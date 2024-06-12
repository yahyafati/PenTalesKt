package org.pentales.pentalesrest.models.entities.book.userBook.status

import org.pentales.pentalesrest.global.repo.base.*
import org.pentales.pentalesrest.models.entities.entityKeys.*
import org.pentales.pentalesrest.models.entities.user.*
import org.springframework.data.domain.*

interface UserBookStatusRepository : IRepoSpecification<UserBookStatus, UserBookKey> {

    fun findTopByUserAndStatusOrderByCreatedAtDesc(user: User, status: EUserBookReadStatus): UserBookStatus?

    fun findTopByUserUsernameAndStatusOrderByCreatedAtDesc(
        username: String,
        status: EUserBookReadStatus
    ): UserBookStatus?

    fun findAllByUserAndStatus(user: User, status: EUserBookReadStatus, pageable: Pageable): List<UserBookStatus>
}