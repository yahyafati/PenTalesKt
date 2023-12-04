package org.pentales.pentalesrest.dto

import org.pentales.pentalesrest.models.*

class ShareDto(
    val id: Long? = 0,
    val quote: String? = "",
    var user: UserDto? = null,
) {

    constructor(activityShare: ActivityShare) : this(
        id = activityShare.id,
        quote = activityShare.shareQuote,
        user = UserDto(activityShare.user),
    )

    fun toActivityShare(
        rating: Rating,
        user: User,
    ): ActivityShare {

        return ActivityShare(
            shareQuote = quote,
            rating = rating,
            user = user,
        )
    }
}