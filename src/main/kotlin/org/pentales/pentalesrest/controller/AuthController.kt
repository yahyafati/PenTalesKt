package org.pentales.pentalesrest.controller

import jakarta.validation.*
import org.pentales.pentalesrest.components.configProperties.*
import org.pentales.pentalesrest.config.*
import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.utils.*
import org.springframework.http.*
import org.springframework.validation.annotation.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authServices: org.pentales.pentalesrest.services.IAuthServices,
    private val securityConfigProperties: SecurityConfigProperties,
    private val jwtService: JwtService,
) {

    @PostMapping("/register")
    fun register(
        @Validated
        @RequestBody
        registerUser: RegisterUser,
    ): ResponseEntity<BasicResponseDto<UserDto>> {
        val user = authServices.register(registerUser)
        val token = jwtService.generateToken(user)
        val headerToken = securityConfigProperties.jwt.header to securityConfigProperties.jwt.prefix + " " + token

        return ResponseEntity.ok().header(headerToken.first, headerToken.second)
            .body(
                BasicResponseDto.ok(
                    UserDto(user, ServletUtil.getBaseURLFromCurrentRequest()),
                    "User registered successfully"
                )
            )
    }

    @PostMapping("/username-available")
    fun isUsernameAvailable(
        @Valid
        @RequestBody
        username: String
    ): ResponseEntity<BasicResponseDto<Boolean>> {
        val isAvailable = authServices.isUsernameAvailable(username)
        val message = "$username is ${if (isAvailable) "available" else "not available"}"
        return ResponseEntity.ok(BasicResponseDto.ok(isAvailable, message))
    }

    @GetMapping("/current")
    fun getCurrentUser(): ResponseEntity<UserDto> {
        val user = authServices.getCurrentUser()
        return ResponseEntity.ok(UserDto(user, ServletUtil.getBaseURLFromCurrentRequest()))
    }
}