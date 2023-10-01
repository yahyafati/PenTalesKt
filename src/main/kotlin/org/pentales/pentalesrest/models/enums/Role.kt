package org.pentales.pentalesrest.models.enums

import org.pentales.pentalesrest.models.*

enum class Role(private val permissions: List<Permission>) {

    GUEST(emptyList()),
    CUSTOM(emptyList()),
    USER(
        listOf(
            Permission.USER_READ,
            Permission.USER_UPDATE,
            Permission.USER_CREATE,
            Permission.USER_DELETE,
        )
    ),
    MANAGER(
        listOf(
            Permission.USER_READ,
            Permission.USER_UPDATE,
            Permission.USER_CREATE,
            Permission.USER_DELETE,
            Permission.MANAGER_READ,
            Permission.MANAGER_UPDATE,
            Permission.MANAGER_CREATE,
            Permission.MANAGER_DELETE,
        )
    ),
    ADMIN(
        listOf(
            Permission.USER_READ,
            Permission.USER_UPDATE,
            Permission.USER_CREATE,
            Permission.USER_DELETE,
            Permission.MANAGER_READ,
            Permission.MANAGER_UPDATE,
            Permission.MANAGER_CREATE,
            Permission.MANAGER_DELETE,
            Permission.ADMIN_READ,
            Permission.ADMIN_UPDATE,
            Permission.ADMIN_CREATE,
            Permission.ADMIN_DELETE,
        )
    );

    fun getRole(): String {
        return this.name
    }

    fun getPermissions(): Collection<Permission> {
        return this.permissions
    }

    fun getPermittedAuthorities(): Collection<Authority> {
        return this.permissions.map { Authority(it) }
    }
}