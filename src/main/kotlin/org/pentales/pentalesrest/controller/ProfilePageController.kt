package org.pentales.pentalesrest.controller

import org.pentales.pentalesrest.services.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/profile")
class ProfilePageController(
    val profilePageServices: IProfilePageServices
) {

    @GetMapping("/{username}")
    fun getProfilePage(
        @PathVariable
        username: String
    ): Map<String, Any> {
        return profilePageServices.getProfilePage(username)
    }

}