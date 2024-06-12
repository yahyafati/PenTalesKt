package org.pentales.pentalesrest.models.entities.user.auth

import jakarta.validation.*
import org.pentales.pentalesrest.config.properties.*
import org.pentales.pentalesrest.global.dto.*
import org.pentales.pentalesrest.models.entities.user.dto.*
import org.pentales.pentalesrest.models.misc.jwt.*
import org.pentales.pentalesrest.utils.*
import org.springframework.http.*
import org.springframework.validation.annotation.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authServices: IAuthServices,
    private val securityProperties: SecurityProperties,
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
        val headerToken = securityProperties.jwt.header to securityProperties.jwt.prefix + " " + token

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