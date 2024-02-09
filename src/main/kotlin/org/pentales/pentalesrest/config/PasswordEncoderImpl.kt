package org.pentales.pentalesrest.config

import org.springframework.context.annotation.*
import org.springframework.security.crypto.bcrypt.*
import org.springframework.security.crypto.password.*
import org.springframework.stereotype.*

@Component
class PasswordEncoderImpl {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}