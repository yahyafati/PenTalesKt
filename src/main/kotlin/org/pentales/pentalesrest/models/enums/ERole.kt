package org.pentales.pentalesrest.models.enums

import org.pentales.pentalesrest.models.*

enum class ERole(private val permissions: List<EPermission>) { GUEST(emptyList()),
    CUSTOM(emptyList()),
    USER(
        listOf(
            EPermission.USER_READ,
            EPermission.USER_UPDATE,
            EPermission.USER_CREATE,
            EPermission.USER_DELETE,
        )
    ),
    MANAGER(
        listOf(
            EPermission.USER_READ,
            EPermission.USER_UPDATE,
            EPermission.USER_CREATE,
            EPermission.USER_DELETE,
            EPermission.MANAGER_READ,
            EPermission.MANAGER_UPDATE,
            EPermission.MANAGER_CREATE,
            EPermission.MANAGER_DELETE,
        )
    ),
    ADMIN(
        listOf(
            EPermission.USER_READ,
            EPermission.USER_UPDATE,
            EPermission.USER_CREATE,
            EPermission.USER_DELETE,
            EPermission.MANAGER_READ,
            EPermission.MANAGER_UPDATE,
            EPermission.MANAGER_CREATE,
            EPermission.MANAGER_DELETE,
            EPermission.ADMIN_READ,
            EPermission.ADMIN_UPDATE,
            EPermission.ADMIN_CREATE,
            EPermission.ADMIN_DELETE,
        )
    );

    fun getRole(): String {
        return this.name
    }

    fun getPermissions(): Collection<EPermission> {
        return this.permissions
    }

    fun getPermittedAuthorities(): Collection<Authority> {
        return this.permissions.map { Authority(it) }
    }
}