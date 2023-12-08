package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.exceptions.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.stereotype.*

@Service
class ActivityShareServices(
    private val activityShareRepository: ActivityShareRepository,
    private val activityRepository: ActivityRepository,
) : IActivityShareServices {

    val entityName = "ActivityShare"

    override fun getShareCountOf(rating: Rating): Int {
        return activityShareRepository.countAllByRating(rating)
    }

    override fun save(activityShare: ActivityShare): ActivityShare {
        return activityShareRepository.save(activityShare)
    }

    override fun getShareById(id: Long): ActivityShare {
        return activityShareRepository.findById(id)
            .orElseThrow { NoEntityWithIdException.create(entityName, id.toString()) }
    }

    override fun saveNew(activityShare: ActivityShare): ActivityShare {
        val savedActivityShare = save(activityShare)
        val activity = Activity(share = savedActivityShare)
        activityRepository.save(activity)
        return savedActivityShare
    }

    override fun deleteById(id: Long) {
        val activityShare = ActivityShare()
        activityShare.id = id
        val affected = activityRepository.deleteByShare(activityShare)
        if (affected == 0L) {
            activityShareRepository.deleteById(id)
        }
    }
}