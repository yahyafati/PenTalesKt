package org.pentales.pentalesrest.models.enums

enum class EPermission(private val title: String) { ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    MANAGER_READ("management:read"),
    MANAGER_UPDATE("management:update"),
    MANAGER_CREATE("management:create"),
    MANAGER_DELETE("management:delete"),
    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_CREATE("user:create"),
    USER_DELETE("user:delete"),
    MODERATOR_READ("moderator:read"),
    MODERATOR_UPDATE("moderator:update"),
    MODERATOR_CREATE("moderator:create"),
    MODERATOR_DELETE("moderator:delete");

    fun getAuthorityName(): String {
        return this.title
    }

    companion object {

        fun getAdminPermissions(): Array<out EPermission> {
            return arrayOf(
                ADMIN_READ,
                ADMIN_UPDATE,
                ADMIN_CREATE,
                ADMIN_DELETE,
            )
        }

        fun getManagerPermissions(): Array<out EPermission> {
            return arrayOf(
                MANAGER_READ,
                MANAGER_UPDATE,
                MANAGER_CREATE,
                MANAGER_DELETE,
            )
        }

        fun getModeratorPermissions(): Array<out EPermission> {
            return arrayOf(
                MODERATOR_READ,
                MODERATOR_UPDATE,
                MODERATOR_CREATE,
                MODERATOR_DELETE,
            )
        }

        fun getUserPermissions(): Array<out EPermission> {
            return arrayOf(
                USER_READ,
                USER_UPDATE,
                USER_CREATE,
                USER_DELETE,
            )
        }

        fun fromTitle(title: String): EPermission {
            return EPermission.values().first { it.title == title }
        }
    }
}