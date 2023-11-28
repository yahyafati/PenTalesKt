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

}