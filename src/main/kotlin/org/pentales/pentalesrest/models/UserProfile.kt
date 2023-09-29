package org.pentales.pentalesrest.models

import jakarta.persistence.*
import org.pentales.pentalesrest.models.enums.*
import java.time.*

@Entity
class UserProfile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long?,
    var firstName: String?,
    var lastName: String?,
    var displayName: String?,
    var dateOfBirth: LocalDate?,
    var bio: String?,

    @Enumerated(value = EnumType.STRING)
    var gender: Gender?,

    @OneToOne
    var user: User?

) : IModel