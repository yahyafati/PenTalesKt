package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*
import java.io.*

@Entity
class UserProfileGenre(
    @EmbeddedId
    var id: UserProfileGenreKey = UserProfileGenreKey(),
    var sortOrder: Int = 0,
    @MapsId("userProfileId")
    @ManyToOne
    var profile: UserProfile = UserProfile(),
    @MapsId("genreId")
    @ManyToOne
    var genre: Genre = Genre()
)

@Embeddable
class UserProfileGenreKey(
    var userProfileId: Long = 0L, var genreId: Long = 0L
) : Serializable {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserProfileGenreKey

        if (userProfileId != other.userProfileId) return false
        if (genreId != other.genreId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userProfileId.hashCode()
        result = 31 * result + genreId.hashCode()
        return result
    }

    override fun toString(): String {
        return "UserProfileGenreKey(userProfileId=$userProfileId, genreId=$genreId)"
    }
}