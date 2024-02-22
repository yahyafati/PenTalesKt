package org.pentales.pentalesrest.config.oauth2

import org.pentales.pentalesrest.models.*

class GoogleOAuth2UserInfo(attributes: Map<String, Any>) : OAuth2UserInfo(attributes) {

    override fun toCustomOAuth2User(user: User): CustomOAuth2User {
        return CustomOAuth2User(user, nameKey)
    }

    override val nameKey: String
        get() = "sub"

    override val id: String
        get() = attributes[nameKey].toString()

    override val name: String
        get() = attributes["name"].toString()

    val familyName: String
        get() = attributes["family_name"].toString()

    override val email: String
        get() = attributes["email"].toString()

    val emailVerified: Boolean
        get() = attributes["email_verified"] as Boolean

    override val imageUrl: String
        get() = attributes["picture"].toString()
    override val firstName: String
        get() = attributes["given_name"].toString()
    override val lastName: String
        get() = attributes["family_name"].toString()
}