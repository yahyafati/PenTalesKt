package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.keys.*

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

