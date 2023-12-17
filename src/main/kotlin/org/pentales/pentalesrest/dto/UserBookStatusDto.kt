package org.pentales.pentalesrest.dto

import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.models.intermediates.*

data class UserBookStatusDto(
    val bookId: Long,
    val status: EUserBookReadStatus,
) {

    constructor(userBookStatus: UserBookStatus) : this(
        bookId = userBookStatus.book.id,
        status = userBookStatus.status,
    )
}