package org.pentales.pentalesrest.models

import org.pentales.pentalesrest.models.enums.*
import org.springframework.security.core.*

class Authority(private val permission: EPermission) : GrantedAuthority { constructor(permissionTitle: String) : this(
    EPermission.fromTitle(permissionTitle)
)

    override fun getAuthority(): String {
        return this.permission.getAuthorityName()
    }
}