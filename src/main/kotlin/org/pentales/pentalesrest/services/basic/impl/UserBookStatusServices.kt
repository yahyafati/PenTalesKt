package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.models.intermediates.*
import org.pentales.pentalesrest.models.keys.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*

@Service
class UserBookStatusServices(
    private val userBookStatusRepository: UserBookStatusRepository
) : IUserBookStatusServices {

    override fun getBookStatus(userId: Long, bookId: Long): UserBookStatus? {
        val key = UserBookKey(
            userId = userId, bookId = bookId
        )
        return userBookStatusRepository.findById(key).orElse(null)
    }

    override fun getNowReadingBook(userId: Long): UserBookStatus? {
        return userBookStatusRepository.findLastByUserAndStatusOrderByCreatedAt(
            User(id = userId), EUserBookReadStatus.READING
        )
    }

    override fun getBooksByStatus(userId: Long, status: EUserBookReadStatus, pageable: Pageable): List<UserBookStatus> {
        return userBookStatusRepository.findAllByUserAndStatus(
            User(id = userId), status, pageable
        )
    }
}