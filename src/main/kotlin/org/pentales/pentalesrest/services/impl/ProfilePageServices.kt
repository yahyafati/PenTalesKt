package org.pentales.pentalesrest.services.impl

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.stereotype.*
import java.time.*

@Service
class ProfilePageServices(
    private val userRepository: UserRepository,
    private val profileRepository: UserProfileRepository,
    private val followerService: IFollowerService,
    private val ratingRepository: RatingRepository,
    private val userGoalService: IUserGoalService
) : IProfilePageServices {

    override fun getProfilePage(username: String): Map<String, Any> {
        val profile = profileRepository.findByUserUsername(username)
        val profileDto = ProfileDto(profile)
        val followerCount = followerService.countFollowersOf(profile.user)
        val followingCount = followerService.countFollowingsOf(profile.user)
        val ratingCount = ratingRepository.countAllByUser(profile.user)
        val reviewCount = ratingRepository.countUserReviews(profile.user)
        val currentGoal = userGoalService.findByUserAndGoalYear(profile.user, Year.now().value)

        return mapOf(

            "profile" to profileDto,

            "followerCount" to followerCount,

            "followingCount" to followingCount,

            "ratingCount" to ratingCount,

            "reviewCount" to reviewCount,

            "currentGoal" to mapOf(
                "target" to currentGoal?.target,
            )
        )
    }
}