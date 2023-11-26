package org.pentales.pentalesrest.services.basic.impl

import jakarta.transaction.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.intermediates.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.jpa.repository.*
import org.springframework.stereotype.*
import kotlin.reflect.*
import kotlin.reflect.full.*

@Service
class BookServices(
    private val bookRepository: BookRepository,
    private val bookGenreRepository: BookGenreRepository,
    private val bookLanguageRepository: BookLanguageRepository,
    private val bookPublisherRepository: BookPublisherRepository,
    private val bookAuthorRepository: BookAuthorRepository
) : IBookServices {

    override val repository: JpaRepository<Book, Long>
        get() = bookRepository
    override val modelProperties: Collection<KProperty1<Book, *>>
        get() = Book::class.memberProperties

    private fun saveBookGenres(book: Book): List<BookGenre> {
        val bookGenres: List<BookGenre> = book.genres
        bookGenres.forEach { bookGenre: BookGenre ->
            val genre = Genre(bookGenre.id.genreId)
            bookGenre.book = book
            bookGenre.genre = genre
        }
        return bookGenreRepository.saveAll(bookGenres)
    }

    private fun saveBookLanguages(book: Book): List<BookLanguage> {
        val bookLanguages: List<BookLanguage> = book.languages
        bookLanguages.forEach { bookLanguage: BookLanguage ->
            val language = Language(bookLanguage.id.languageId)
            bookLanguage.book = book
            bookLanguage.language = language
        }
        return bookLanguageRepository.saveAll(bookLanguages)
    }

    private fun saveBookPublishers(book: Book): List<BookPublisher> {
        val bookPublishers: List<BookPublisher> = book.publishers
        bookPublishers.forEach { bookPublisher: BookPublisher ->
            val publisher = Publisher(bookPublisher.id.publisherId)
            bookPublisher.book = book
            bookPublisher.publisher = publisher
        }
        return bookPublisherRepository.saveAll(bookPublishers)
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
        val savedBookLanguages = saveBookLanguages(savedBook)
        savedBook.languages = savedBookLanguages
        val savedBookPublishers = saveBookPublishers(savedBook)
        savedBook.publishers = savedBookPublishers
        val savedBookAuthors = saveBookAuthors(savedBook)
        savedBook.authors = savedBookAuthors
        return savedBook
    }
}
