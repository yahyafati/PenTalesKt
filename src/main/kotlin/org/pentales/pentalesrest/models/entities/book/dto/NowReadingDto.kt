package org.pentales.pentalesrest.models.entities.book.dto

import java.sql.*

data class NowReadingDto(
    var book: BookDTO?,
    var startedAt: Timestamp?,
)
