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
    var authors: List<AuthorDto> = ArrayList(),
    var genres: List<Long> = ArrayList(),
    var languageCode: String = "",
    var publisher: String = "",
    var coverImage: String = ""
) {

    constructor(book: Book) : this(
        id = book.id,
        title = book.title,
        description = book.description,
        ISBN = book.ISBN,
        publicationYear = book.publicationYear,
        authors = book.authors.map { AuthorDto(it.author) },
        genres = book.genres.map { it.genre.id },
        languageCode = book.languageCode,
        publisher = book.publisher,
        coverImage = book.coverImage
    )

    fun toBook(): Book {
        return Book(
            id = id,
            title = title,
            description = description,
            ISBN = ISBN,
            publicationYear = publicationYear,
            authors = authors.mapIndexed { index, it ->
                BookAuthor(BookAuthorKey(id, it.id), book = Book(id), author = Author(it.id), sortOrder = index)
            },
            genres = genres.mapIndexed { index, it ->
                BookGenre(BookGenreKey(id, it), book = Book(id), genre = Genre(it), sortOrder = index)
            },
            languageCode = languageCode,
            publisher = publisher,
            coverImage = coverImage
        )
    }

    override fun toString(): String {
        return "BookDTO(id=$id, title='$title', description='$description', ISBN='$ISBN', publicationYear=$publicationYear)"
    }
}