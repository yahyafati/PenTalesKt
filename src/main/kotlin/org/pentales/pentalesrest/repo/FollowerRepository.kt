package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.keys.*

interface FollowerRepository : IRepoSpecification<Follower, UserUserKey> {

    fun countAllByFollower(follower: User): Int
    fun countAllByFollowerUsername(username: String): Int

    fun countAllByFollowed(following: User): Int
    fun countAllByFollowedUsername(username: String): Int

    fun findAllByFollowed(followed: User): List<Follower>
    fun findAllByFollower(follower: User): List<Follower>

}