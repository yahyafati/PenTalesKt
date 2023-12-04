package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*

interface FollowerRepository : IRepoSpecification<Follower, Long> {

    fun countAllByFollower(follower: User): Long

    fun countAllByFollowing(following: User): Long

}