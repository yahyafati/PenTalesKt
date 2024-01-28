package org.pentales.pentalesrest.models.enums

import org.pentales.pentalesrest.models.*

enum class ERole(private val permissions: List<EPermission>) { GUEST(emptyList()),
    CUSTOM(emptyList()),
    USER(
        listOf(
            *EPermission.getUserPermissions(),
        )
    ),
    MODERATOR(
        listOf(
            *EPermission.getUserPermissions(),
            *EPermission.getModeratorPermissions(),
        )
    ),
    MANAGER(
        listOf(
            *EPermission.getUserPermissions(),
            *EPermission.getModeratorPermissions(),
            *EPermission.getManagerPermissions(),
        )
    ),
    ADMIN(
        listOf(
            *EPermission.getUserPermissions(),
            *EPermission.getModeratorPermissions(),
            *EPermission.getManagerPermissions(),
            *EPermission.getAdminPermissions(),
        )
    ),
    SUPER_ADMIN(
        listOf(
            *EPermission.getUserPermissions(),
            *EPermission.getModeratorPermissions(),
            *EPermission.getManagerPermissions(),
            *EPermission.getAdminPermissions(),
//            *EPermission.getSuperAdminPermissions(),
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