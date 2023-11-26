package org.pentales.pentalesrest.models

import jakarta.persistence.*
import org.pentales.pentalesrest.models.enums.*
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
    var gender: Gender = Gender.FEMALE,
    @OneToOne
    var wantToReadList: ReadList = ReadList(),
    @OneToOne
    var user: User = User(),
) : IModel() {

    override fun toString(): String {
        return "UserProfile(id=$id, firstName='$firstName', lastName='$lastName', displayName='$displayName', dateOfBirth=$dateOfBirth, bio='$bio', gender=$gender)"
    }
}