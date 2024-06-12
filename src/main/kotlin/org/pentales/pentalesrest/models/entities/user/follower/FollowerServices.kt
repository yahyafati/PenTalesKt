package org.pentales.pentalesrest.models.entities.user.follower

import org.pentales.pentalesrest.models.entities.entityKeys.*
import org.pentales.pentalesrest.models.entities.user.*
import org.pentales.pentalesrest.models.entities.user.dto.*
import org.pentales.pentalesrest.models.misc.pushNotification.*
import org.pentales.pentalesrest.utils.*
import org.slf4j.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*

@Service
class FollowerServices(
    private val followerRepository: FollowerRepository,
    private val pushNotificationService: IPushNotificationService,
) : IFollowerServices {

    companion object {

        val LOG: Logger = LoggerFactory.getLogger(FollowerServices::class.java)
    }

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

    override fun isFollowing(followerUsername: String, followedUsername: String): Boolean {
        return followerRepository.existsByFollowerUsernameAndFollowedUsername(
            followerUsername = followerUsername,
            followedUsername = followedUsername
        )
    }

    override fun toggleFollow(followerUser: User, followedUser: User): Boolean {
        val key = UserUserKey(followerId = followerUser.id, followedId = followedUser.id)
        val follower = Follower(id = key, followed = followedUser, follower = followerUser)
        val exists = followerRepository.existsById(key)
        LOG.debug("Follower exists: $exists")
        if (exists) {
            followerRepository.deleteById(key)
            return false
        }
        val isFollowingBack = isFollowing(followedUser, followerUser)
        pushNotificationService.sendPushNotificationToUser(
            action = IPushNotificationService.ActionType.OPEN_USER_PROFILE,
            userId = followedUser.id,
            data = mapOf(
                "type" to "follow",
                "username" to followerUser.username,
                "userId" to followerUser.id.toString(),
                "icon" to UserDto.getURLWithBaseURL(
                    followerUser.profile?.profilePicture,
                    ServletUtil.getBaseURLFromCurrentRequest()
                ).let { it ?: "" },
                "isFollowingBack" to isFollowingBack.toString()
            )
        )
        save(follower)
        return true
    }

    override fun getFollowings(followerUser: User): List<User> {
        val followings = followerRepository.findAllByFollower(followerUser)
        return followings.map { it.followed }
    }

    override fun getFollowings(followerUser: User, pageable: Pageable): Page<User> {
        return followerRepository.findAllByFollower(followerUser, pageable).map { it.followed }
    }

    override fun getFollowers(followedUser: User): List<User> {
        val followers = followerRepository.findAllByFollowed(followedUser)
        return followers.map { it.follower }
    }

    override fun getFollowers(followedUser: User, pageable: Pageable): Page<User> {
        return followerRepository.findAllByFollowed(followedUser, pageable).map { it.follower }
    }

}