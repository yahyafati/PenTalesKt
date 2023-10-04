package org.pentales.pentalesrest.dto

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.intermediates.*

class BookDTO(
    var id: Long = 0L,
    var title: String = "",
    var description: String = "",
    var ISBN: String = "",
    var publicationYear: Int = 0,
    var authors: List<Author> = ArrayList(),
    var genres: List<Genre> = ArrayList(),
    var languages: List<Language> = ArrayList(),
    var publishers: List<Publisher> = ArrayList()
) {

    constructor(book: Book) : this(book.id,
        book.title,
        book.description,
        book.ISBN,
        book.publicationYear,
        book.authors.map { it.author },
        book.genres.map { it.genre },
        book.languages.map { it.language },
        book.publishers.map { it.publisher })

    fun toBook(): Book {
        return Book(id, title, description, ISBN, publicationYear, authors.mapIndexed { index, it ->
            BookAuthor(BookAuthorKey(id, it.id), book = Book(id), author = it, sortOrder = index)
        }, genres.mapIndexed { index, it ->
            BookGenre(BookGenreKey(id, it.id), book = Book(id), genre = it, sortOrder = index)
        }, languages.mapIndexed { index, it ->
            BookLanguage(BookLanguageKey(id, it.id), book = Book(id), language = it, sortOrder = index)
        }, publishers.mapIndexed { index, it ->
            BookPublisher(BookPublisherKey(id, it.id), book = Book(id), publisher = it, sortOrder = index)
        })
    }

    override fun toString(): String {
        return "BookDTO(id=$id, title='$title', description='$description', ISBN='$ISBN', publicationYear=$publicationYear)"
    }
}