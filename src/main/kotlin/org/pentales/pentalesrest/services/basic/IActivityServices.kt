package org.pentales.pentalesrest.services.basic

import org.pentales.pentalesrest.models.*

interface IActivityServices {

    fun saveRating(rating: Rating): Activity
    fun saveComment(comment: RatingComment): Activity
    fun saveShare(share: ActivityShare): Activity
    fun deleteActivity(id: Long)
}