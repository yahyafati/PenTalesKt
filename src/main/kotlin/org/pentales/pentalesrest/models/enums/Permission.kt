package org.pentales.pentalesrest.models.enums

enum class Permission(private val title: String) { ADMIN_READ("admin:read"),
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
    USER_DELETE("user:delete");

    fun getAuthorityName(): String {
        return this.title
    }

    companion object {

        fun fromTitle(title: String): Permission {
            return Permission.values().first { it.title == title }
        }
    }
}