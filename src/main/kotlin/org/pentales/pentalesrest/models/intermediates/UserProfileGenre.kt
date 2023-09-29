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
    var profile: UserProfile? = null,
    @MapsId("genreId")
    @ManyToOne
    var genre: Genre? = null

)

@Embeddable
class UserProfileGenreKey(
    var userProfileId: Long = 0L, var genreId: Long = 0L
) : Serializable