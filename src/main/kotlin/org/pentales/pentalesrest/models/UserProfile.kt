package org.pentales.pentalesrest.models

import jakarta.persistence.*
import net.minidev.json.annotate.*
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.models.interfaces.*
import java.time.*

@Entity
class UserProfile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long = 0L,
    var firstName: String = "",
    var lastName: String = "",
    var displayName: String = "",
    var dateOfBirth: LocalDate = LocalDate.now(),
    var bio: String = "",
    @Enumerated(value = EnumType.STRING)
    var gender: EGender = EGender.FEMALE,
    var profilePicture: String? = null,
    var coverPicture: String? = null,
    var goodreadsProfile: String? = null,
    var twitterProfile: String? = null,
    var facebookProfile: String? = null,
    var instagramProfile: String? = null,
    var linkedinProfile: String? = null,
    var youtubeProfile: String? = null,
    @OneToOne
    var user: User = User(),
) : IModel() {

    @JsonIgnore
    fun getFullName(): String {
        if (this.displayName.isEmpty()) {
            return displayName
        }
        return "${this.firstName} ${this.lastName}"
    }

    override fun toString(): String {
        return "UserProfile(id=$id, firstName='$firstName', lastName='$lastName', displayName='$displayName', dateOfBirth=$dateOfBirth, bio='$bio', gender=$gender)"
    }
}