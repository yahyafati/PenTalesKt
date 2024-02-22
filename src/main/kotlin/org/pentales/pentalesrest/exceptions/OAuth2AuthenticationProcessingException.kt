package org.pentales.pentalesrest.exceptions

import org.springframework.security.core.*

class OAuth2AuthenticationProcessingException : AuthenticationException {
    constructor(msg: String?, t: Throwable?) : super(msg, t)

    constructor(msg: String?) : super(msg)
}