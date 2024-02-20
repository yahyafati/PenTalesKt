package org.pentales.pentalesrest.config

import org.pentales.pentalesrest.models.enums.*
import org.slf4j.*
import org.springframework.security.access.*
import org.springframework.security.core.*
import org.springframework.stereotype.*
import java.io.*

@Component
class CustomPermissionEvaluator : PermissionEvaluator {

    companion object {

        val LOG = LoggerFactory.getLogger(CustomPermissionEvaluator::class.java)
    }

    override fun hasPermission(
        auth: Authentication?, targetDomainObject: Any?, permission: Any
    ): Boolean {
        if ((auth == null) || (targetDomainObject == null) || permission !is String) {
            return false
        }

        val target = targetDomainObject.toString().uppercase()
        val permissions = if (target == "*") {
            EPermission.values().toList()
        } else {
            if (permission.toString() == "*") {
                EPermission.values().filter { it.name.startsWith(target) }
            } else {
                val permissionName =
                    "${target}_${permission.toString().uppercase()}"
                listOf(EPermission.valueOf(permissionName))
            }
        }

        LOG.debug("Checking permissions: {}", permissions)


        return hasPrivileges(
            auth,
            permissions
        )
    }

    override fun hasPermission(
        auth: Authentication?, targetId: Serializable?, targetType: String?, permission: Any
    ): Boolean {
        if ((auth == null) || (targetType == null) || permission !is String) {
            return false
        }
        val permissionName = "${targetType.uppercase()}_${permission.toString().uppercase()}"
        return hasPrivilege(
            auth,
            EPermission.valueOf(permissionName)
        )
    }

    private fun hasPrivilege(auth: Authentication, permission: EPermission): Boolean {
        return auth.authorities.any { it.authority == permission.name }
    }

    private fun hasPrivileges(auth: Authentication, permissions: List<EPermission>): Boolean {
        LOG.debug("Checking permissions: {}", permissions)
        for (permission in permissions) {
            if (hasPrivilege(auth, permission)) {
                return true
            }
        }
        return false
    }

}
