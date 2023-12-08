package org.pentales.pentalesrest.services.basic

import org.pentales.pentalesrest.models.*

interface IFollowerServices {

    fun countFollowersOf(
        user: User
    ): Int

    fun countFollowersOf(
        username: String
    ): Int

    fun countFollowingsOf(
        user: User
    ): Int

    fun countFollowingsOf(
        username: String
    ): Int

    fun isFollowing(
        follower: User, followed: User
    ): Boolean

}