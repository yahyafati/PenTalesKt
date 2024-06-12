package org.pentales.pentalesrest.models.entities.rating

import org.pentales.pentalesrest.config.properties.*
import org.pentales.pentalesrest.exceptions.*
import org.pentales.pentalesrest.models.entities.book.*
import org.pentales.pentalesrest.models.entities.entityKeys.*
import org.pentales.pentalesrest.models.entities.rating.like.*
import org.pentales.pentalesrest.models.entities.user.*
import org.pentales.pentalesrest.models.entities.user.dto.*
import org.pentales.pentalesrest.models.misc.pushNotification.*
import org.pentales.pentalesrest.models.misc.sentimentAnalysis.*
import org.pentales.pentalesrest.utils.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*
import org.springframework.transaction.annotation.*
import kotlin.reflect.*
import kotlin.reflect.full.*

@Service
class RatingServices(
    private val repository: RatingRepository,
    private val ratingLikeRepository: RatingLikeRepository,
    private val pushNotificationService: IPushNotificationService,
    private val sentimentAnalysisService: SentimentAnalysisService,
) : IRatingServices {

    override val modelProperties: Collection<KProperty1<Rating, *>>
        get() = Rating::class.memberProperties

    override fun findByBookId(bookId: Long, pageable: Pageable): Page<Rating> {
        return repository.findAllByBook(Book(id = bookId), pageable)
    }

    override fun findByUserId(userId: Long, pageable: Pageable): Page<Rating> {
        return repository.findAllByUser(User(id = userId), pageable)
    }

    override fun findById(id: Long): Rating {
        return repository.findById(id).orElseThrow { NoEntityWithIdException.create(entityName, id.toString()) }
    }

    @Transactional
    override fun save(entity: Rating): Rating {
        val saved = repository.save(entity)
        sentimentAnalysisService.addRequest(
            SentimentAnalysisRequest(
                id = saved.id,
                text = saved.review,
                type = ESentimentAnalysisRequestType.REVIEW
            )
        )
        return saved
    }

    @Transactional
    override fun saveNew(entity: Rating): Rating {
        val existing = repository.findByBookAndUser(entity.book, entity.user)
        if (existing != null) {
            existing.value = entity.value
            existing.review = entity.review
            if (existing.hidden) {
                existing.hidden = false
                existing.review = ""
            }
            return save(existing)
        }
        return save(entity)
    }

    @Transactional
    override fun saveValue(value: Int, book: Book, user: User): Rating {
        val existing = repository.findByBookAndUser(book, user)
        if (existing != null) {
            existing.value = value
            if (existing.hidden) {
                existing.hidden = false
                existing.review = ""
            }
            return save(existing)
        }
        val rating = Rating(book = book, user = user, value = value)
        return save(rating)
    }

    @Transactional
    override fun deleteById(id: Long, user: User): Long {
        return repository.deleteByIdAndUser(id, user)
    }

    override fun deleteByBookId(bookId: Long) {
        repository.deleteAllByBook(Book(id = bookId))
    }

    override fun deleteByUserId(userId: Long) {
        repository.deleteAllByUser(User(id = userId))
    }

    override fun likeRating(rating: Rating, user: User): Boolean {
        val key = UserRatingKey(userId = user.id, ratingId = rating.id)
        if (ratingLikeRepository.existsById(key)) {
            return true
        }
        val ratingLike = RatingLike(id = key, rating = rating, user = user)
        ratingLikeRepository.save(ratingLike)
        if (rating.user.id != user.id) {
            pushNotificationService.sendPushNotificationToUser(
                IPushNotificationService.ActionType.OPEN_REVIEW,
                rating.user.id,
                mapOf(
                    "type" to "like",
                    "reviewId" to rating.id.toString(),
                    "bookTitle" to rating.book.title,
                    "bookId" to rating.book.id.toString(),
                    "username" to user.username,
                    "icon" to UserDto.getURLWithBaseURL(
                        user.profile?.profilePicture,
                        ServletUtil.getBaseURLFromCurrentRequest()
                    ).let { it ?: "" }
                )
            )
        }
        return true
    }

    override fun unlikeRating(rating: Rating, user: User): Boolean {
        val key = UserRatingKey(userId = user.id, ratingId = rating.id)
        if (!ratingLikeRepository.existsById(key)) {
            return false
        }
        ratingLikeRepository.deleteById(key)
        return false
    }

}