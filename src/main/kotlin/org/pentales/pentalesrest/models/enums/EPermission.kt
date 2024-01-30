package org.pentales.pentalesrest.models.enums

enum class EPermission(val id: Long) {

    ADMIN_READ(1),
    ADMIN_WRITE(2),
    ADMIN_DELETE(3),
    MANAGER_READ(4),
    MANAGER_CREATE(5),
    MANAGER_DELETE(6),
    USER_READ(7),
    USER_CREATE(8),
    USER_DELETE(9),
    MODERATOR_ACCESS(10);

    companion object {

        fun getAdminPermissions(): Array<out EPermission> {
            return arrayOf(
                ADMIN_READ,
                ADMIN_WRITE,
                ADMIN_DELETE,
            )
        }

        fun getManagerPermissions(): Array<out EPermission> {
            return arrayOf(
                MANAGER_READ,
                MANAGER_CREATE,
                MANAGER_DELETE,
            )
        }

        fun getModeratorPermissions(): Array<out EPermission> {
            return arrayOf(
                MODERATOR_ACCESS,
            )
        }

        fun getUserPermissions(): Array<out EPermission> {
            return arrayOf(
                USER_READ,
                USER_CREATE,
                USER_DELETE,
            )
        }

    }
}