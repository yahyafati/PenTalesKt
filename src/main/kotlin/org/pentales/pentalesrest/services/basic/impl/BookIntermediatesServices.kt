package org.pentales.pentalesrest.services.basic.impl

import jakarta.transaction.*
import org.pentales.pentalesrest.exceptions.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.intermediates.*
import org.pentales.pentalesrest.models.keys.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.stereotype.*

@Service
class BookIntermediatesServices(
    private val bookRepository: BookRepository,
    private val bookGenreRepository: BookGenreRepository,
    private val bookLanguageRepository: BookLanguageRepository,
    private val bookPublisherRepository: BookPublisherRepository,
    private val bookAuthorRepository: BookAuthorRepository
) : IBookIntermediatesServices {

    @Transactional
    override fun updateAuthors(bookId: Long, authors: List<Author>, delete: Boolean): Book {
        val book = bookRepository.findById(bookId).orElseThrow { NoEntityWithIdException.create("Book", bookId) }
        if (delete) {
            bookAuthorRepository.deleteAllByBookId(bookId)
        }
        val bookAuthors = authors.mapIndexed { index, author ->
            BookAuthor(BookAuthorKey(book.id, author.id), index, book, author)
        }
        bookAuthorRepository.saveAll(bookAuthors)
        return book
    }

    @Transactional
    override fun removeAuthors(bookId: Long): Book {
        bookAuthorRepository.deleteAllByBookId(bookId)
        return bookRepository.findById(bookId).orElseThrow { NoEntityWithIdException.create("Book", bookId) }
    }

    @Transactional
    override fun updatePublishers(bookId: Long, publishers: List<Publisher>, delete: Boolean): Book {
        val book = bookRepository.findById(bookId).orElseThrow { NoEntityWithIdException.create("Book", bookId) }
        if (delete) {
            bookPublisherRepository.deleteAllByBookId(bookId)
        }
        val bookPublishers = publishers.mapIndexed { index, publisher ->
            BookPublisher(BookPublisherKey(book.id, publisher.id), index, book, publisher)
        }
        bookPublisherRepository.saveAll(bookPublishers)
        return book
    }

    @Transactional
    override fun removePublishers(bookId: Long): Book {
        bookPublisherRepository.deleteAllByBookId(bookId)
        return bookRepository.findById(bookId).orElseThrow { NoEntityWithIdException.create("Book", bookId) }
    }

    @Transactional
    override fun updateGenres(bookId: Long, genres: List<Genre>, delete: Boolean): Book {
        val book = bookRepository.findById(bookId).orElseThrow { NoEntityWithIdException.create("Book", bookId) }
        if (delete) {
            bookGenreRepository.deleteAllByBookId(bookId)
        }
        val bookGenres = genres.mapIndexed { index, genre ->
            BookGenre(BookGenreKey(book.id, genre.id), index, book, genre)
        }
        bookGenreRepository.saveAll(bookGenres)
        return book
    }

    @Transactional
    override fun removeGenres(bookId: Long): Book {
        bookGenreRepository.deleteAllByBookId(bookId)
        return bookRepository.findById(bookId).orElseThrow { NoEntityWithIdException.create("Book", bookId) }
    }

    @Transactional
    override fun updateLanguages(bookId: Long, languages: List<Language>, delete: Boolean): Book {
        val book = bookRepository.findById(bookId).orElseThrow { NoEntityWithIdException.create("Book", bookId) }
        if (delete) {
            bookLanguageRepository.deleteAllByBookId(bookId)
        }
        val bookLanguages = languages.mapIndexed { index, language ->
            BookLanguage(BookLanguageKey(book.id, language.id), index, book, language)
        }
        bookLanguageRepository.saveAll(bookLanguages)
        return book
    }

    @Transactional
    override fun removeLanguages(bookId: Long): Book {
        bookLanguageRepository.deleteAllByBookId(bookId)
        return bookRepository.findById(bookId).orElseThrow { NoEntityWithIdException.create("Book", bookId) }
    }
}