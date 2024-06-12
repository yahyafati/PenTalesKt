package org.pentales.pentalesrest.models.entities.user.authority

import jakarta.persistence.*
import jakarta.persistence.Transient
import org.pentales.pentalesrest.models.entities.user.role.*
import org.springframework.security.core.*

@Entity
class Authority(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    var permission: EPermission
) : GrantedAuthority {

    constructor(permissionTitle: String) : this(
        permission = EPermission.valueOf(permissionTitle)
    )

    constructor(permission: EPermission) : this(
        id = permission.id,
        permission = permission
    )

    init {
        id = permission.id
    }

    override fun equals(other: Any?): Boolean {
        if (other !is GrantedAuthority || other !is Authority) return false
        return this.authority == other.authority
    }

    @Transient
    override fun getAuthority(): String {
        return this.permission.name
    }

    override fun hashCode(): Int {
        return permission.hashCode()
    }

    override fun toString(): String {
        return permission.name
    }
}