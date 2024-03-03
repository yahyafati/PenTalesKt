package org.pentales.pentalesrest.controller

import org.pentales.pentalesrest.config.security.*
import org.pentales.pentalesrest.models.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/author")
class AuthorController(
    override val service: org.pentales.pentalesrest.services.IAuthorServices,
    override val authenticationFacade: IAuthenticationFacade
) : IBasicControllerSkeleton<Author, org.pentales.pentalesrest.services.IAuthorServices>
