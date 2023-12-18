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
    private val userBookStatusRepository: UserBookStatusRepository,
    private val userBookActivityServices: IUserBookActivityServices,
) : IUserBookStatusServices {

    override fun updateBookStatus(userId: Long, bookId: Long, status: EUserBookReadStatus): UserBookStatus {
        val id = UserBookKey(userId = userId, bookId = bookId)
        val userBookStatus = UserBookStatus(id = id)
        userBookStatus.status = status
        userBookStatus.book = Book(id = bookId)
        userBookStatus.user = User(id = userId)
        val savedStatus = userBookStatusRepository.save(userBookStatus)
        val userBookActivity = UserBookActivity(User(id = userId), Book(id = bookId), status = status)
        userBookActivityServices.addBookActivity(userBookActivity)
        return savedStatus
    }

    override fun getBookStatus(userId: Long, bookId: Long): EUserBookReadStatus {
        val key = UserBookKey(
            userId = userId, bookId = bookId
        )
        val status = userBookStatusRepository.findById(key).orElse(null) ?: return EUserBookReadStatus.NONE
        return status.status
    }

    override fun getNowReadingBook(userId: Long): UserBookStatus? {
        return userBookStatusRepository.findTopByUserAndStatusOrderByCreatedAtDesc(
            User(id = userId), EUserBookReadStatus.READING
        )
    }

    override fun getBooksByStatus(userId: Long, status: EUserBookReadStatus, pageable: Pageable): List<UserBookStatus> {
        return userBookStatusRepository.findAllByUserAndStatus(
            User(id = userId), status, pageable
        )
    }
}