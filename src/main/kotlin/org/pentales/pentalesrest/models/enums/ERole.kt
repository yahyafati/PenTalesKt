package org.pentales.pentalesrest.models.enums

enum class ERole(val id: Long, private val permissions: List<EPermission>) {

    ROLE_CUSTOM(1, emptyList()),
    ROLE_USER(
        2,
        listOf(
            *EPermission.getUserPermissions(),
        )
    ),
    ROLE_MODERATOR(
        3,
        listOf(
            *EPermission.getUserPermissions(),
            *EPermission.getModeratorPermissions(),
        )
    ),
    ROLE_MANAGER(
        4,
        listOf(
            *EPermission.getUserPermissions(),
            *EPermission.getModeratorPermissions(),
            *EPermission.getManagerPermissions(),
        )
    ),
    ROLE_ADMIN(
        5,
        listOf(
            *EPermission.getUserPermissions(),
            *EPermission.getModeratorPermissions(),
            *EPermission.getManagerPermissions(),
            *EPermission.getAdminPermissions(),
        )
    ),
    ROLE_SUPER_ADMIN(
        6,
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

}