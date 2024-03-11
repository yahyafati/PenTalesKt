package org.pentales.pentalesrest.services.impl

import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.exceptions.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.*
import org.pentales.pentalesrest.utils.*
import org.springframework.stereotype.*

@Service
class ShareServices(
    private val shareRepository: ShareRepository,
    private val pushNotificationService: PushNotificationService
) : IShareServices {

    val entityName = "ActivityShare"

    override fun getShareCountOf(rating: Rating): Int {
        return shareRepository.countAllByRating(rating)
    }

    override fun save(share: Share): Share {
        return shareRepository.save(share)
    }

    override fun getShareById(id: Long): Share {
        return shareRepository.findById(id).orElseThrow { NoEntityWithIdException.create(entityName, id.toString()) }
    }

    override fun saveNew(share: Share): Share {
        share.id = 0
        val saved = save(share)
        if (saved.user.id != saved.rating.user.id) {
            pushNotificationService.sendPushNotificationToUser(
                IPushNotificationService.ActionType.OPEN_REVIEW,
                saved.rating.user.id,
                mapOf(
                    "type" to "share",
                    "shareId" to saved.id.toString(),
                    "reviewId" to saved.rating.id.toString(),
                    "bookId" to saved.rating.book.id.toString(),
                    "bookTitle" to saved.rating.book.title,
                    "username" to saved.user.username,
                    "icon" to UserDto.getURLWithBaseURL(
                        saved.user.profile?.profilePicture,
                        ServletUtil.getBaseURLFromCurrentRequest()
                    ),
                )
            )
        }
        return saved
    }

    override fun deleteById(id: Long) {
        shareRepository.deleteById(id)
    }
}