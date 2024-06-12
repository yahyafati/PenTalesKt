package org.pentales.pentalesrest.models.entities.activity

import jakarta.persistence.*
import jakarta.persistence.Id
import jakarta.persistence.Transient
import org.pentales.pentalesrest.models.entities.activity.book.*
import org.pentales.pentalesrest.models.entities.rating.*
import org.pentales.pentalesrest.models.entities.rating.comment.*
import org.pentales.pentalesrest.models.entities.rating.share.*
import org.pentales.pentalesrest.models.entities.user.*
import org.springframework.data.annotation.*
import java.sql.*
import java.util.*

@Entity
@Table(name = "activity")
@Immutable
data class ActivityView(
    @Id
    var id: UUID = UUID.randomUUID(),

    @Column(name = "rating_id")
    var ratingId: Long = 0,

    var activityId: Long = 0,

    @ManyToOne
    var user: User = User(),

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    var type: EActivityType = EActivityType.RATING,

    @Column(name = "updated_at")
    var updatedAt: Timestamp = Timestamp(System.currentTimeMillis()),

    @Column(name = "created_at")
    var createdAt: Timestamp = Timestamp(System.currentTimeMillis()),
) {

    @Transient
    var rating: Rating? = null

    @Transient
    var comment: Comment? = null

    @Transient
    var share: Share? = null

    @Transient
    var activityBook: ActivityBook? = null

    fun getEffectiveRating(): Rating? {
        return when (type) {
            EActivityType.RATING -> rating
            EActivityType.COMMENT -> comment?.rating
            EActivityType.SHARE -> share?.rating
        }
    }

    init {
        when (type) {
            EActivityType.RATING -> {
                rating = Rating()
                rating?.id = activityId
            }

            EActivityType.COMMENT -> {
                comment = Comment()
                comment?.id = activityId
            }

            EActivityType.SHARE -> {
                share = Share()
                share?.id = activityId
            }
        }

        activityBook = ActivityBook()
    }
}