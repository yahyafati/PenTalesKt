package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*
import java.io.*

@Entity
class UserProfileGenre(

    @EmbeddedId
    var id: UserProfileGenreKey? = null, var sortOrder: Int = 0,

    @MapsId("userProfileId")
    @ManyToOne
    var profile: UserProfile? = null,

    @MapsId("genreId")
    @ManyToOne
    var genre: Genre? = null

)

@Embeddable
class UserProfileGenreKey(
    var userProfileId: Long?, var genreId: Long?
) : Serializable