package org.pentales.pentalesrest.global.controller.test

import org.pentales.pentalesrest.exceptions.*
import org.springframework.security.access.prepost.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/test")
class TestController {

    @GetMapping("/unsecured")
    fun unsecured(): String {
        return "Unsecured"
    }

    @GetMapping("/secured")
    fun secured(): String {
        return "Secured"
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    fun admin(): String {
        return "Admin"
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    fun user(): String {
        return "User"
    }

    @GetMapping("/all")
    fun all(): String {
        return "All"
    }

    @GetMapping("/error")
    fun error(): String {
        throw GenericException("Generic error")
    }
}
