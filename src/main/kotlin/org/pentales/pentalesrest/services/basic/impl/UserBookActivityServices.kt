package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*
import java.sql.*
import java.time.*

@Service
class UserBookActivityServices(
    private val userBookActivityRepository: UserBookActivityRepository
) : IUserBookActivityServices {

    fun save(userBookActivity: UserBookActivity): UserBookActivity {
        return userBookActivityRepository.save(userBookActivity)
    }

    override fun addBookActivity(userBookActivity: UserBookActivity): UserBookActivity {
        userBookActivity.id = 0L
        return save(userBookActivity)
    }

    override fun getBooksByStatus(
        userId: Long, status: EUserBookReadStatus, pageable: Pageable
    ): List<UserBookActivity> {
        return userBookActivityRepository.findAllByUserIdAndStatus(userId, status, pageable)
    }

    override fun getBooksCountByStatus(userId: Long, status: EUserBookReadStatus): Int {
        return userBookActivityRepository.countAllByUserIdAndStatus(userId, status)
    }

    override fun getBooksByStatusSince(
        userId: Long, status: EUserBookReadStatus, since: Long, pageable: Pageable
    ): List<UserBookActivity> {
        return userBookActivityRepository.findAllByUserIdAndStatusAndCreatedAtGreaterThanEqual(
            userId, status, Timestamp(since), pageable
        )
    }

    override fun getBooksCountByStatusSince(userId: Long, status: EUserBookReadStatus, since: Long): Int {
        return userBookActivityRepository.countAllByUserIdAndStatusAndCreatedAtGreaterThanEqual(
            userId, status, Timestamp(since)
        )
    }

    override fun getBooksCountByStatusInYear(userId: Long, status: EUserBookReadStatus, year: Int): Int {
        val yearStart = Year.of(year).atDay(1).atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
        val yearEnd = Year.of(year + 1).atDay(1).atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
        return userBookActivityRepository.countAllByUserIdAndStatusAndCreatedAtBetween(
            userId = userId,
            status = status,
            startTime = Timestamp(yearStart),
            endTime = Timestamp(yearEnd),
        )
    }

}