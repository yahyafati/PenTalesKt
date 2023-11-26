package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.keys.*

@Entity
class UserProfileLanguage(
    @EmbeddedId
    var id: UserProfileLanguageKey = UserProfileLanguageKey(),
    var sortOrder: Int = 0,
    @MapsId("userProfileId")
    @ManyToOne
    var profile: UserProfile = UserProfile(),
    @MapsId("languageId")
    @ManyToOne
    var language: Language = Language()
)

