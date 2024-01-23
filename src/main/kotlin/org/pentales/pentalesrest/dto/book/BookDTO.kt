package org.pentales.pentalesrest.dto.book

import org.pentales.pentalesrest.dto.author.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.intermediates.*
import org.pentales.pentalesrest.models.keys.*

data class BookDTO(
    var id: Long = 0L,
    var title: String = "",
    var description: String = "",
    var ISBN: String = "",
    var publicationYear: Int = 0,
    var authors: List<AuthorDto> = ArrayList(),
    var genres: List<Long> = ArrayList(),
    var languageCode: String = "",
    var publisher: String = "",
    var coverImage: String = "",
    var averageRating: Double = 0.0,
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
        coverImage = book.coverImage,
        averageRating = book.__averageRating
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

}