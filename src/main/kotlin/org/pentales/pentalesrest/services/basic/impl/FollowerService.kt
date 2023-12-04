package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.keys.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.stereotype.*

@Service
class FollowerService(
    private val followerRepository: FollowerRepository
) : IFollowerService {

    override fun countFollowersOf(user: User): Int {
        // We have to look for the cases where the user is followed
        return followerRepository.countAllByFollowed(user)
    }

    override fun countFollowersOf(username: String): Int {
        return followerRepository.countAllByFollowedUsername(username)
    }

    override fun countFollowingsOf(user: User): Int {
        // We have to look for the cases where the user is a follower
        return followerRepository.countAllByFollower(user)
    }

    override fun countFollowingsOf(username: String): Int {
        return followerRepository.countAllByFollowerUsername(username)
    }

    override fun isFollowing(follower: User, followed: User): Boolean {
        val key = UserUserKey(
            followerId = follower.id, followedId = followed.id
        )
        return followerRepository.existsById(key)
    }
}