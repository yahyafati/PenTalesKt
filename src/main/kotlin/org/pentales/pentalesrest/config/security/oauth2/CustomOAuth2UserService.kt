package org.pentales.pentalesrest.config.security.oauth2

import org.pentales.pentalesrest.exceptions.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.embeddables.*
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.services.*
import org.springframework.security.authentication.*
import org.springframework.security.core.*
import org.springframework.security.oauth2.client.userinfo.*
import org.springframework.security.oauth2.core.user.*
import org.springframework.stereotype.*
import kotlin.random.*

@Service
class CustomOAuth2UserService(
    private val userServices: IUserServices,
) : DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)
        return try {
            processOAuth2User(userRequest!!, oAuth2User)
        } catch (ex: AuthenticationException) {
            throw ex
        } catch (ex: Exception) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw InternalAuthenticationServiceException(ex.message, ex.cause)
        }
    }

    private fun processOAuth2User(oAuth2UserRequest: OAuth2UserRequest, oAuth2User: OAuth2User): OAuth2User {
        val oAuth2UserInfo: OAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
            oAuth2UserRequest.clientRegistration.registrationId,
            oAuth2User.attributes
        )
        if (oAuth2UserInfo.email.isEmpty()) {
            throw OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider")
        }

        var user = userServices.findByEmail(oAuth2UserInfo.email)
        var isNewUser: Boolean = false
        if (user != null) {
            val existingProvider = user.provider?.provider
            val provider = EAuthProvider.from(oAuth2UserRequest.clientRegistration.registrationId)
            if (existingProvider != null) {
                if (existingProvider != EAuthProvider.LOCAL && existingProvider != provider
                ) {
                    throw OAuth2AuthenticationProcessingException(
                        "Looks like you're signed up with $existingProvider account. " +
                                "Please use your $existingProvider account to login."
                    )
                }
            }
            user = updateExistingUser(user, oAuth2UserInfo, provider)
        } else {
            isNewUser = true
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo)
        }

        return oAuth2UserInfo.toCustomOAuth2User(user).apply { newUser = isNewUser }
    }

    private fun extractUniqueUsernameFromEmail(email: String): String {
        val username = email.takeWhile { it != '@' }
        var prefix = ""
        var uniqueUsername = username
        while (userServices.existsByUsername(uniqueUsername)) {
            prefix += Random.nextInt(100, 999).toString()
            uniqueUsername = "${username}_${prefix}"
        }
        return uniqueUsername
    }

    private fun registerNewUser(oAuth2UserRequest: OAuth2UserRequest, oAuth2UserInfo: OAuth2UserInfo): User {
        val provider = EAuthProvider.from(oAuth2UserRequest.clientRegistration.registrationId)
        val user = User()
        user.email = oAuth2UserInfo.email
        user.username = extractUniqueUsernameFromEmail(oAuth2UserInfo.email)
        user.password = ""
        user.profile = UserProfile(
            firstName = oAuth2UserInfo.firstName,
            lastName = oAuth2UserInfo.lastName,
            profilePicture = oAuth2UserInfo.imageUrl,
            user = user
        )
        user.provider = UserProvider(
            provider = provider,
            providerId = oAuth2UserInfo.id
        )
        return userServices.save(user)
    }

    private fun updateExistingUser(existingUser: User, oAuth2UserInfo: OAuth2UserInfo, provider: EAuthProvider): User {
        existingUser.profile?.let {
            it.firstName = oAuth2UserInfo.name
            it.lastName = oAuth2UserInfo.name
            it.profilePicture = oAuth2UserInfo.imageUrl
        }
        val providerEntity = existingUser.provider ?: UserProvider()
        existingUser.provider = providerEntity.apply {
            this.provider = providerEntity.provider
            this.providerId = oAuth2UserInfo.id
        }
        return userServices.save(existingUser)
    }
}