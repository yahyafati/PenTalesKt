package org.pentales.pentalesrest.services.basic.impl

import jakarta.transaction.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.intermediates.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.repo.specifications.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.stereotype.*
import kotlin.reflect.*
import kotlin.reflect.full.*

@Service
class BookServices(
    private val bookRepository: BookRepository,
    private val bookGenreRepository: BookGenreRepository,
    private val bookAuthorRepository: BookAuthorRepository,
    private val bookSpecification: ISpecification<Book>
) : IBookServices {

    override val repository: IRepoSpecification<Book, Long>
        get() = bookRepository
    override val modelProperties: Collection<KProperty1<Book, *>>
        get() = Book::class.memberProperties

    override val specification: ISpecification<Book>
        get() = bookSpecification

    private fun saveBookGenres(book: Book): List<BookGenre> {
        val bookGenres: List<BookGenre> = book.genres
        bookGenres.forEach { bookGenre: BookGenre ->
            val genre = Genre(bookGenre.id.genreId)
            bookGenre.book = book
            bookGenre.genre = genre
        }
        return bookGenreRepository.saveAll(bookGenres)
    }

    private fun saveBookAuthors(book: Book): List<BookAuthor> {
        val bookAuthors: List<BookAuthor> = book.authors
        bookAuthors.forEach { bookAuthor: BookAuthor ->
            val author = Author(bookAuthor.id.authorId)
            bookAuthor.book = book
            bookAuthor.author = author
        }
        return bookAuthorRepository.saveAll(bookAuthors)
    }

    @Transactional
    override fun saveNew(entity: Book): Book {
        val savedBook: Book = super.saveNew(entity)
        val savedBookGenres = saveBookGenres(savedBook)
        savedBook.genres = savedBookGenres
        val savedBookAuthors = saveBookAuthors(savedBook)
        savedBook.authors = savedBookAuthors
        return savedBook
    }
}
