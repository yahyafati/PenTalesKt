package org.pentales.pentalesrest.controller.rest

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/author")
class AuthorController(
    override val service: IAuthorServices, override val authenticationFacade: IAuthenticationFacade
) : IBasicControllerSkeleton<Author, IAuthorServices>
