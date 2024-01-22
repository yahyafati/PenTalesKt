package org.pentales.pentalesrest.components

import org.springframework.boot.context.properties.*
import org.springframework.stereotype.*

@Component
data class SecurityConfigProperties(
    var jwt: JwtProperties = JwtProperties(),
    var loginUrl: String = "/api/auth/login",
    var logoutUrl: String = "/api/auth/logout",
    var refreshUrl: String = "/api/auth/refresh",
    var registerUrl: String = "/api/auth/register",
    var usernameAvailableUrl: String = "/api/auth/username-available",
) {

    @ConfigurationProperties(prefix = "org.pen-tales.security.jwt")
    data class JwtProperties(
        var secret: String = "",
//        var issuer: String = "",
//        var audience: String = "",
        var expiration: Long = 0L,
        var header: String = "",
        var prefix: String = "",
        var issuer: String = "self",
    )
}