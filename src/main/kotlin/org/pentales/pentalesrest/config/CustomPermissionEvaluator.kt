package org.pentales.pentalesrest.config

import org.pentales.pentalesrest.models.enums.*
import org.springframework.security.access.*
import org.springframework.security.core.*
import org.springframework.stereotype.*
import java.io.*

@Component
class CustomPermissionEvaluator : PermissionEvaluator {

    override fun hasPermission(
        auth: Authentication?, targetDomainObject: Any?, permission: Any
    ): Boolean {
        if ((auth == null) || (targetDomainObject == null) || permission !is String) {
            return false
        }
        return hasPrivilege(
            auth,
            EPermission.valueOf(permission)
        )
    }

    override fun hasPermission(
        auth: Authentication?, targetId: Serializable?, targetType: String?, permission: Any
    ): Boolean {
        if ((auth == null) || (targetType == null) || permission !is String) {
            return false
        }
        return hasPrivilege(
            auth,
            EPermission.valueOf(permission)
        )
    }

    private fun hasPrivilege(auth: Authentication, permission: EPermission): Boolean {
        for (grantedAuth in auth.authorities) {
            if (grantedAuth.authority == permission.name) {
                return true
            }
        }
        return false
    }
}
