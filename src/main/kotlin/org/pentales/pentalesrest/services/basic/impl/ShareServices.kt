package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.exceptions.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.stereotype.*

@Service
class ShareServices(
    private val shareRepository: ShareRepository,
    private val activityRepository: ActivityRepository,
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
        val savedActivityShare = save(share)
        val activity = Activity(share = savedActivityShare)
        activityRepository.save(activity)
        return savedActivityShare
    }

    override fun deleteById(id: Long) {
        val share = Share()
        share.id = id
        val affected = activityRepository.deleteByShare(share)
        if (affected == 0L) {
            shareRepository.deleteById(id)
        }
    }
}