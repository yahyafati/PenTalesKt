package org.pentales.pentalesrest.services.impl

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.security.core.userdetails.*
import org.springframework.stereotype.*
import java.time.*

@Service
class ProfilePageServices(
    private val userRepository: UserRepository,
    private val profileRepository: UserProfileRepository,
    private val followerService: IFollowerServices,
    private val ratingRepository: RatingRepository,
    private val userGoalService: IUserGoalServices,
    private val userBookActivityServices: IUserBookActivityServices,
    private val userBookStatusServices: IUserBookStatusServices,
    private val authenticationFacade: IAuthenticationFacade,
) : IProfilePageServices {

    override fun getProfilePage(username: String): Map<String, Any> {
        val currentUser = authenticationFacade.forcedCurrentUser
        val profile =
            profileRepository.findByUserUsername(username) ?: throw UsernameNotFoundException("No profile found")
        val profileDto = ProfileDto(profile)
        val followerCount = followerService.countFollowersOf(profile.user)
        val followingCount = followerService.countFollowingsOf(profile.user)
        val isFollowed = followerService.isFollowing(follower = currentUser, followed = profile.user)
        val ratingCount = ratingRepository.countAllByUser(profile.user)
        val reviewCount = ratingRepository.countUserReviews(profile.user)
        val currentGoal = userGoalService.findByUserAndGoalYear(profile.user, Year.now().value)
        val nowReading = userBookStatusServices.getNowReadingBook(profile.user.id)
        val readSoFar = userBookActivityServices.getBooksCountByStatusInYear(
            profile.user.id, EUserBookReadStatus.READ, Year.now(ZoneId.of("UTC")).value
        )

        val map = mutableMapOf(

            "profile" to profileDto,

            "followerCount" to followerCount,

            "followingCount" to followingCount,

            "ratingCount" to ratingCount,

            "reviewCount" to reviewCount,

            "isFollowed" to isFollowed,
        )

        if (currentGoal != null) {
            map["currentGoal"] = mapOf(
                "target" to currentGoal.target,
                "current" to readSoFar,
                "year" to Year.now().value,
            )
        }

        if (nowReading != null) {
            map["nowReading"] = mapOf(
                "book" to BookDTO(nowReading.book),
                "startedAt" to nowReading.createdAt,
            )
        }

        return map
    }
}