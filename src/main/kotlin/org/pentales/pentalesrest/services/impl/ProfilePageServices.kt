package org.pentales.pentalesrest.services.impl

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.enums.*
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
    private val userGoalService: IUserGoalService,
    private val userBookServices: IUserBookServices,
) : IProfilePageServices {

    override fun getProfilePage(username: String): Map<String, Any> {
        val profile = profileRepository.findByUserUsername(username)
        val profileDto = ProfileDto(profile)
        val followerCount = followerService.countFollowersOf(profile.user)
        val followingCount = followerService.countFollowingsOf(profile.user)
        val ratingCount = ratingRepository.countAllByUser(profile.user)
        val reviewCount = ratingRepository.countUserReviews(profile.user)
        val currentGoal = userGoalService.findByUserAndGoalYear(profile.user, Year.now().value)
        val nowReading = userBookServices.getNowReadingBook(profile.user.id)
        val startOfYear = Year.now().atDay(1).atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
        val readSoFar =
            userBookServices.getBooksCountByStatusSince(profile.user.id, UserBookReadStatus.READ, startOfYear)

        val map = mutableMapOf(

            "profile" to profileDto,

            "followerCount" to followerCount,

            "followingCount" to followingCount,

            "ratingCount" to ratingCount,

            "reviewCount" to reviewCount,
        )

        if (currentGoal != null) {
            map["currentGoal"] = mapOf(
                "target" to currentGoal.target,
                "readSoFar" to readSoFar,
            )
        }

        if (nowReading != null) {
            map["nowReading"] = mapOf(
                "book" to BookDTO(nowReading.book),
                "startedAt" to nowReading.updatedAt,
            )
        }

        return map
    }
}