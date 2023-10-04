package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*
import java.io.*

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

@Embeddable
class UserProfileLanguageKey(
    var userProfileId: Long = 0L, var languageId: Long = 0L
) : Serializable {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserProfileLanguageKey

        if (userProfileId != other.userProfileId) return false
        if (languageId != other.languageId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userProfileId.hashCode()
        result = 31 * result + languageId.hashCode()
        return result
    }

    override fun toString(): String {
        return "UserProfileLanguageKey(userProfileId=$userProfileId, languageId=$languageId)"
    }
}