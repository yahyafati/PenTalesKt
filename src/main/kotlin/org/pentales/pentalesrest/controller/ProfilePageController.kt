package org.pentales.pentalesrest.controller

import jakarta.servlet.http.*
import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/profile")
class ProfilePageController(
    val profilePageServices: IProfilePageServices,
    val authenticationFacade: IAuthenticationFacade,
) {

    @GetMapping
    fun getProfilePage(
        @RequestParam(required = false, name = "username")
        usernameParam: String?,
        request: HttpServletRequest,
    ): BasicResponseDto<Map<String, Any>> {
        val username = usernameParam ?: authenticationFacade.forcedCurrentUser.username
        val response = profilePageServices.getProfilePage(username, request)
        return BasicResponseDto.ok(response)
    }

}