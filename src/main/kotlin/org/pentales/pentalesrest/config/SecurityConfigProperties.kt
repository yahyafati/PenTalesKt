package org.pentales.pentalesrest.config

import org.springframework.boot.context.properties.*

@ConfigurationProperties(prefix = "org.pen-tales.security")
data class SecurityConfigProperties(
    var jwt: JwtProperties = JwtProperties(),
    var loginUrl: String = "/login",
    var logoutUrl: String = "/logout",
    var refreshUrl: String = "/refresh",
    var registerUrl: String = "/register",
) {

    class JwtProperties(
        var secret: String = "",
//        var issuer: String = "",
//        var audience: String = "",
        var expiration: Long = 0L,
        var header: String = "",
        var prefix: String = "",
        var issuer: String = "self",
    )
}