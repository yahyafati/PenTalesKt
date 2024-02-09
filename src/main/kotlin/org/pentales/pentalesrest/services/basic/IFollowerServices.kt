package org.pentales.pentalesrest.services.basic

import org.pentales.pentalesrest.models.*
import org.springframework.data.domain.*

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

    fun toggleFollow(
        followerUser: User, followedUser: User
    ): Boolean

    fun getFollowings(
        followerUser: User
    ): List<User>

    fun getFollowers(
        followedUser: User,
    ): List<User>

    fun getFollowings(
        followerUser: User,
        pageable: Pageable
    ): Page<User>

    fun getFollowers(
        followedUser: User,
        pageable: Pageable
    ): Page<User>

}