package org.pentales.pentalesrest.services.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.intermediates.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.*
import org.springframework.data.jpa.repository.*
import org.springframework.stereotype.*
import java.util.function.*

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

    private fun saveBookGenres(book: Book): List<BookGenre> {
        val bookGenres: List<BookGenre> = book.genres
        bookGenres.forEach(Consumer { bookGenre: BookGenre ->
            val genre = Genre(bookGenre.id.genreId)
            bookGenre.book = book
            bookGenre.genre = genre
        })
        return bookGenreRepository.saveAll(bookGenres)
    }

    private fun saveBookLanguages(book: Book): List<BookLanguage> {
        val bookLanguages: List<BookLanguage> = book.languages
        bookLanguages.forEach(Consumer { bookLanguage: BookLanguage ->
            val language = Language(bookLanguage.id.languageId)
            bookLanguage.book = book
            bookLanguage.language = language
        })
        return bookLanguageRepository.saveAll(bookLanguages)
    }

    private fun saveBookPublishers(book: Book): List<BookPublisher> {
        val bookPublishers: List<BookPublisher> = book.publishers
        bookPublishers.forEach(Consumer { bookPublisher: BookPublisher ->
            val publisher = Publisher(bookPublisher.id.publisherId)
            bookPublisher.book = book
            bookPublisher.publisher = publisher
        })
        return bookPublisherRepository.saveAll(bookPublishers)
    }

    private fun saveBookAuthors(book: Book): List<BookAuthor> {
        val bookAuthors: List<BookAuthor> = book.authors
        bookAuthors.forEach(Consumer<BookAuthor> { bookAuthor: BookAuthor ->
            val author = Author(bookAuthor.id.authorId)
            bookAuthor.book = book
            bookAuthor.author = author
        })
        return bookAuthorRepository.saveAll<BookAuthor>(bookAuthors)
    }

    override fun save(entity: Book): Book {
        val savedBook: Book = super.save(entity)
        val savedBookGenres: List<BookGenre> = saveBookGenres(savedBook)
        savedBook.genres = savedBookGenres
        val savedBookLanguages: List<BookLanguage> = saveBookLanguages(savedBook)
        savedBook.languages = savedBookLanguages
        val savedBookPublishers: List<BookPublisher> = saveBookPublishers(savedBook)
        savedBook.publishers = savedBookPublishers
        val savedBookAuthors: List<BookAuthor> = saveBookAuthors(savedBook)
        savedBook.authors = savedBookAuthors
        return savedBook
    }
}
