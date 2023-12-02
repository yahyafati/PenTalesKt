package org.pentales.pentalesrest.dto

import org.pentales.pentalesrest.models.*

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