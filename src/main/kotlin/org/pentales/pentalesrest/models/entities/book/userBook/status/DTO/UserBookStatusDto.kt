package org.pentales.pentalesrest.models.entities.book.userBook.status.DTO

import org.pentales.pentalesrest.models.entities.book.userBook.status.*

data class UserBookStatusDto(
    val bookId: Long,
    val status: EUserBookReadStatus,
) {

    constructor(userBookStatus: UserBookStatus) : this(
        bookId = userBookStatus.book.id,
        status = userBookStatus.status,
    )
}