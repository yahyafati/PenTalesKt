package org.pentales.pentalesrest.models

import jakarta.persistence.*
import org.pentales.pentalesrest.models.enums.*

@Entity
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    role: ERole = ERole.ROLE_USER,
) {

    @Transient
    var authorities = role.getPermissions()

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    var role: ERole = role
        set(value) {
            field = value
            this.id = value.id
            this.authorities = value.getPermissions()
        }

    init {
        this.id = role.id
        this.authorities = role.getPermissions()
    }
}