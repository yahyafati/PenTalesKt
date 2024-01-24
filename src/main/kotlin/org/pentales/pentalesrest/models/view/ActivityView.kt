package org.pentales.pentalesrest.models.view

import jakarta.persistence.*
import jakarta.persistence.Id
import jakarta.persistence.Transient
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*
import org.springframework.data.annotation.*
import java.sql.*

@Entity
@Table(name = "activity")
@Immutable
data class ActivityView(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0,

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
                rating?.id = id
            }

            EActivityType.COMMENT -> {
                comment = Comment()
                comment?.id = id
            }

            EActivityType.SHARE -> {
                share = Share()
                share?.id = id
            }
        }

        activityBook = ActivityBook()
    }
}