package org.pentales.pentalesrest.services.basic

import org.pentales.pentalesrest.models.*

interface IActivityShareServices {

    fun getShareCountOf(
        rating: Rating
    ): Int

    fun getShareById(
        id: Long
    ): ActivityShare

    fun save(
        activityShare: ActivityShare
    ): ActivityShare

    fun saveNew(
        activityShare: ActivityShare
    ): ActivityShare

    fun deleteById(
        id: Long
    )

}