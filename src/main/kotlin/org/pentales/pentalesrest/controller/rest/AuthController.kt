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

    @GetMapping("/current")
    fun getCurrentUser(): ResponseEntity<UserDto> {
        val user = authServices.getCurrentUser()
        return ResponseEntity.ok(UserDto(user))
    }
}