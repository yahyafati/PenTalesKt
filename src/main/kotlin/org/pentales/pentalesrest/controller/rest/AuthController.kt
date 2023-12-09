package org.pentales.pentalesrest.controller.rest

import jakarta.validation.*
import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authServices: IAuthServices) {

    @PostMapping("/register")
    fun register(
        @Valid
        @RequestBody
        registerUser: RegisterUser
    ): ResponseEntity<UserDto> {
        val user = authServices.register(registerUser)
        return ResponseEntity.ok(UserDto(user))
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
        return ResponseEntity.ok(UserDto(user))
    }
}