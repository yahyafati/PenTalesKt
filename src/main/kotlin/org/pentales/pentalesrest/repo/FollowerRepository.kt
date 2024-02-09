package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.keys.*
import org.pentales.pentalesrest.repo.base.*
import org.springframework.data.domain.*

interface FollowerRepository : IRepoSpecification<Follower, UserUserKey> {

    fun countAllByFollower(follower: User): Int
    fun countAllByFollowerUsername(username: String): Int

    fun countAllByFollowed(following: User): Int
    fun countAllByFollowedUsername(username: String): Int

    fun findAllByFollowed(followed: User): List<Follower>
    fun findAllByFollower(follower: User): List<Follower>

    fun findAllByFollower(follower: User, pageable: Pageable): Page<Follower>

    fun findAllByFollowed(followed: User, pageable: Pageable): Page<Follower>

}