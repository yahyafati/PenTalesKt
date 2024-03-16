package org.pentales.pentalesrest.config.security.oauth2

import jakarta.servlet.http.*
import org.pentales.pentalesrest.utils.*
import org.springframework.security.oauth2.client.web.*
import org.springframework.security.oauth2.core.endpoint.*
import org.springframework.stereotype.*

@Component
class HttpCookieOAuth2AuthorizationRequestRepository : AuthorizationRequestRepository<OAuth2AuthorizationRequest?> {

    override fun loadAuthorizationRequest(request: HttpServletRequest?): OAuth2AuthorizationRequest? {
        val cookie = CookieUtils.getCookie(request!!, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME) ?: return null

        return CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest::class.java)
    }

    override fun removeAuthorizationRequest(
        request: HttpServletRequest?,
        response: HttpServletResponse?
    ): OAuth2AuthorizationRequest? {
        return this.removeAuthorizationRequest(request)
    }

    override fun saveAuthorizationRequest(
        authorizationRequest: OAuth2AuthorizationRequest?,
        request: HttpServletRequest,
        response: HttpServletResponse?
    ) {
        if (authorizationRequest == null) {
            CookieUtils.deleteCookie(request, response!!, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
            CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME)
            return
        }

        CookieUtils.addCookie(
            response!!,
            OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
            CookieUtils.serialize(authorizationRequest),
            cookieExpireSeconds
        )

        val redirectUriAfterLogin: String = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME) ?: ""
        if (redirectUriAfterLogin.isNotBlank()) {
            CookieUtils.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin, cookieExpireSeconds)
        }
    }

    fun removeAuthorizationRequest(request: HttpServletRequest?): OAuth2AuthorizationRequest? {
        return this.loadAuthorizationRequest(request)
    }

    fun removeAuthorizationRequestCookies(request: HttpServletRequest?, response: HttpServletResponse?) {
        CookieUtils.deleteCookie(request!!, response!!, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
        CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME)
    }

    companion object {

        const val OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME: String = "oauth2_auth_request"
        const val REDIRECT_URI_PARAM_COOKIE_NAME: String = "redirect_uri"
        private const val cookieExpireSeconds = 180
    }
}
