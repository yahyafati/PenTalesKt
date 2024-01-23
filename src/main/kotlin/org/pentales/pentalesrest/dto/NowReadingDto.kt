package org.pentales.pentalesrest.dto

import org.pentales.pentalesrest.dto.book.*
import java.sql.*

data class NowReadingDto(
    var book: BookDTO?,
    var startedAt: Timestamp?,
)
