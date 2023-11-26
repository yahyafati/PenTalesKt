package org.pentales.pentalesrest.dto

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.intermediates.*
import org.pentales.pentalesrest.models.keys.*

class BookDTO(
    var id: Long = 0L,
    var title: String = "",
    var description: String = "",
    var ISBN: String = "",
    var publicationYear: Int = 0,
    var authors: List<Long> = ArrayList(),
    var genres: List<Long> = ArrayList(),
    var languages: List<Long> = ArrayList(),
    var publishers: List<Long> = ArrayList()
) {

    constructor(book: Book) : this(book.id,
        book.title,
        book.description,
        book.ISBN,
        book.publicationYear,
        book.authors.map { it.author.id },
        book.genres.map { it.genre.id },
        book.languages.map { it.language.id },
        book.publishers.map { it.publisher.id })

    fun toBook(): Book {
        return Book(id = id,
            title = title,
            description = description,
            ISBN = ISBN,
            publicationYear = publicationYear,
            authors = authors.mapIndexed { index, it ->
                BookAuthor(BookAuthorKey(id, it), book = Book(id), author = Author(it), sortOrder = index)
            },
            genres = genres.mapIndexed { index, it ->
                BookGenre(BookGenreKey(id, it), book = Book(id), genre = Genre(it), sortOrder = index)
            },
            languages = languages.mapIndexed { index, it ->
                BookLanguage(BookLanguageKey(id, it), book = Book(id), language = Language(it), sortOrder = index)
            },
            publishers = publishers.mapIndexed { index, it ->
                BookPublisher(BookPublisherKey(id, it), book = Book(id), publisher = Publisher(it), sortOrder = index)
            })
    }

    override fun toString(): String {
        return "BookDTO(id=$id, title='$title', description='$description', ISBN='$ISBN', publicationYear=$publicationYear)"
    }
}