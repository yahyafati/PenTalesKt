package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.intermediates.*
import org.pentales.pentalesrest.models.keys.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.repo.base.*
import org.pentales.pentalesrest.repo.specifications.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*
import org.springframework.transaction.annotation.*
import kotlin.reflect.*
import kotlin.reflect.full.*

@Service
class BookShelfServices(
    private val bookShelfRepository: BookShelfRepository,
    private val bookShelfBookRepository: IBookshelfBookRepository,
    private val ratingRepository: RatingRepository,
) : IBookShelfServices {

    fun filterShelvesByOwner(shelves: List<BookShelf>, user: User): List<BookShelf> {
        return shelves.filter { bookShelfRepository.existsByOwnerAndId(user, it.id) }
    }

    override fun findAllByOwner(owner: User, pageable: Pageable): Page<BookShelf> {
        return bookShelfRepository.findAllByOwner(owner, pageable)
    }

    override fun findAllByOwnerUsername(username: String, pageable: Pageable): Page<BookShelf> {
        return bookShelfRepository.findAllByOwnerUsername(username, pageable)
    }

    override fun findReadLater(owner: User): BookShelf {
        return bookShelfRepository.findByOwnerAndReadLaterIsTrue(owner)
    }

    override fun findReadLater(username: String): BookShelf {
        val readLater = bookShelfRepository.findByOwnerUsernameAndReadLaterIsTrue(username)
        readLater.books.forEach { shelfBook ->
            shelfBook.book.__averageRating =
                ratingRepository.findAverageRatingByBook(Book(id = shelfBook.book.id)) ?: 0.0
            shelfBook.book.__ratingCount = ratingRepository.countAllByBook(Book(id = shelfBook.book.id))
        }
        return readLater
    }

    @Transactional
    override fun removeBookFromAllShelves(user: User, book: Book): Int {
        return bookShelfBookRepository.deleteAllByBookShelfOwnerAndBook(user, ActivityBook(book))
    }

    @Transactional
    override fun addBookToShelves(user: User, book: Book, shelves: List<BookShelf>, removeExisting: Boolean): Int {
        if (removeExisting) {
            removeBookFromAllShelves(user, book)
        }
        val activityBook = ActivityBook(book)
        val filteredShelves = filterShelvesByOwner(shelves, user)
        val bookShelfBooks = filteredShelves.map {
            val key = BookShelfBookKey(it.id, book.id)
            BookShelfBook(key, activityBook, it)
        }
        val saved = bookShelfBookRepository.saveAll(bookShelfBooks)
        return saved.size
    }

    override val repository: IRepoSpecification<BookShelf, Long>
        get() = bookShelfRepository
    override val modelProperties: Collection<KProperty1<BookShelf, *>>
        get() = BookShelf::class.memberProperties
    override val specification: ISpecification<BookShelf>
        get() = object : ISpecification<BookShelf> {}
}