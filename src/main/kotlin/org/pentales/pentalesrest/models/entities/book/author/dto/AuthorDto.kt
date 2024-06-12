package org.pentales.pentalesrest.models.entities.book.author.dto

import org.pentales.pentalesrest.models.entities.book.author.*

class AuthorDto(
    var id: Long = 0,
    var name: String = "",
) {

    constructor(author: Author) : this(
        id = author.id,
        name = author.name,
    )

    fun toAuthor(): Author {
        return Author(
            id = id,
            name = name,
        )
    }

}