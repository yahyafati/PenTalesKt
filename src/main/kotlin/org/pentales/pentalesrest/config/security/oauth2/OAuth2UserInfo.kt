package org.pentales.pentalesrest.config.security.oauth2

import org.pentales.pentalesrest.models.entities.user.*

abstract class OAuth2UserInfo(var attributes: Map<String, Any>) {

    abstract fun toCustomOAuth2User(user: User): CustomOAuth2User

    abstract val nameKey: String

    abstract val id: String

    abstract val name: String

    abstract val email: String

    abstract val imageUrl: String
    abstract val firstName: String
    abstract val lastName: String
}