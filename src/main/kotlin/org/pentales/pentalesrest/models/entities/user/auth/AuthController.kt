package org.pentales.pentalesrest.models.entities.user.auth

import jakarta.validation.*
import org.pentales.pentalesrest.config.properties.*
import org.pentales.pentalesrest.global.dto.*
import org.pentales.pentalesrest.models.entities.user.auth.dto.*
import org.pentales.pentalesrest.models.entities.user.dto.*
import org.pentales.pentalesrest.models.misc.jwt.*
import org.pentales.pentalesrest.utils.*
import org.springdoc.core.annotations.*
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

    @PostMapping("/resend-verification-email")
    fun resendVerificationEmail(): ResponseEntity<BasicResponseDto<Unit>> {
        authServices.resendVerificationEmail()
        return ResponseEntity.ok(BasicResponseDto.ok(Unit, "Verification email sent successfully"))
    }

    @PostMapping("/verify-email")
    fun verifyEmail(
        @Valid
        @RequestBody
        verifyUser: VerifyUserDto,
    ): ResponseEntity<BasicResponseDto<UserDto>> {
        val verification = authServices.verifyEmail(verifyUser)
        val isEmailVerified = verification.status
        val message = if (isEmailVerified) "Email verified successfully" else "Email verification failed"

        if (!isEmailVerified) {
            throw IllegalStateException("Email verification failed")
        }

        val userDto = UserDto(
            verification.user ?: throw IllegalStateException("User not found"),
            ServletUtil.getBaseURLFromCurrentRequest()
        )

        return ResponseEntity.ok(
            BasicResponseDto.ok(
                userDto, message
            )
        )
    }

    @PostMapping("/forgot-password")
    fun forgotPassword(
        @Valid
        @RequestBody
        forgotPasswordDto: ForgotPasswordDto
    ): ResponseEntity<BasicResponseDto<Unit>> {
        authServices.forgotPassword(forgotPasswordDto)
        return ResponseEntity.ok(BasicResponseDto.ok(Unit, "Password reset link sent successfully"))
    }

    @GetMapping("/reset-password")
    fun resetPassword(
        @ParameterObject
        verifyUser: ResetUserDto,
    ): ResponseEntity<BasicResponseDto<Unit>> {
        authServices.resetPassword(verifyUser)
        if (verifyUser.callback.isNullOrBlank()) {
            return ResponseEntity.ok(BasicResponseDto.ok(Unit, "Password reset successfully"))
        }
        
        return ResponseEntity
            .status(HttpStatus.FOUND)
            .header("Location", verifyUser.callback)
            .body(null)
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