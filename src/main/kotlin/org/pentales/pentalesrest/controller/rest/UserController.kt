package org.pentales.pentalesrest.controller.rest

import org.pentales.pentalesrest.services.basic.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: IUserServices, private val userProfileService: IUserProfileServices
) {}