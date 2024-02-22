package org.pentales.pentalesrest.config.oauth2

import org.pentales.pentalesrest.models.*
import org.springframework.security.core.*
import org.springframework.security.oauth2.core.user.*

class CustomOAuth2User(
    private val oAuth2User: OAuth2User,
) : OAuth2User {

    var user: User? = null
    var newUser: Boolean = false

    constructor(user: User, nameKey: String) : this(
        DefaultOAuth2User(
            user.getAuthorities(),
            mapOf(nameKey to user.id),
            nameKey
        )
    ) {
        this.user = user
    }

    override fun getName(): String {
        return oAuth2User.name
    }

    fun getEmail(): String {
        return oAuth2User.attributes["email"] as String
    }

    override fun getAttributes(): MutableMap<String, Any> {
        return oAuth2User.attributes
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return oAuth2User.authorities
    }

    override fun toString(): String {
        return "CustomOAuth2User(oAuth2User=$oAuth2User)"
    }

}
