package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.models.intermediates.*
import org.pentales.pentalesrest.models.keys.*
import org.pentales.pentalesrest.repo.base.IRepoSpecification
import org.springframework.data.domain.*

interface UserBookStatusRepository : IRepoSpecification<UserBookStatus, UserBookKey> {

    fun findTopByUserAndStatusOrderByCreatedAtDesc(user: User, status: EUserBookReadStatus): UserBookStatus?

    fun findAllByUserAndStatus(user: User, status: EUserBookReadStatus, pageable: Pageable): List<UserBookStatus>
}