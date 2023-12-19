package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.keys.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.stereotype.*

@Service
class FollowerServices(
    private val followerRepository: FollowerRepository
) : IFollowerServices {

    fun save(follower: Follower): Follower {
        return followerRepository.save(follower)
    }

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

    override fun follow(followerUser: User, followedUser: User): Follower {
        val key = UserUserKey(followerId = followedUser.id, followedId = followedUser.id)
        val follower = Follower(id = key, followed = followedUser, follower = followerUser)
        return save(follower)
    }

    override fun unfollow(followerUser: User, followedUser: User): Boolean {
        val key = UserUserKey(followerId = followedUser.id, followedId = followedUser.id)
        if (!followerRepository.existsById(key)) {
            return false
        }
        followerRepository.deleteById(key)
        return true
    }

    override fun getFollowings(followerUser: User): List<User> {
        val followings = followerRepository.findAllByFollower(followerUser)
        return followings.map { it.followed }
    }

    override fun getFollowers(followedUser: User): List<User> {
        val followers = followerRepository.findAllByFollowed(followedUser)
        return followers.map { it.follower }
    }
}