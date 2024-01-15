package org.pentales.pentalesrest.dto

import java.sql.*

data class NowReadingDto(
    var book: BookDTO?,
    var startedAt: Timestamp?,
)
