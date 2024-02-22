package org.pentales.pentalesrest.config.oauth2

import org.pentales.pentalesrest.exceptions.*
import org.pentales.pentalesrest.models.enums.*

object OAuth2UserInfoFactory {

    fun getOAuth2UserInfo(registrationId: String, attributes: Map<String, Any>): OAuth2UserInfo {
        return if (registrationId.equals(EAuthProvider.GOOGLE.toString(), ignoreCase = true)) {
            GoogleOAuth2UserInfo(attributes)
        } else {
            throw OAuth2AuthenticationProcessingException("Sorry! Login with $registrationId is not supported yet.")
        }
    }
}