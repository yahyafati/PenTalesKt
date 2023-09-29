package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*
import java.io.*

@Entity
class UserProfileLanguage(

    @EmbeddedId
    var id: UserProfileLanguageKey? = null, var sortOrder: Int = 0,

    @MapsId("userProfileId")
    @ManyToOne
    var profile: UserProfile? = null,

    @MapsId("languageId")
    @ManyToOne
    var language: Language? = null

)

@Embeddable
class UserProfileLanguageKey(
    var userProfileId: Long?, var languageId: Long?
) : Serializable