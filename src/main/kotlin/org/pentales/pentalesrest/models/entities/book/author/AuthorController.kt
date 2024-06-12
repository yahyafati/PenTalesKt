package org.pentales.pentalesrest.models.entities.book.author

import org.pentales.pentalesrest.config.security.*
import org.pentales.pentalesrest.global.controller.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/author")
class AuthorController(
    override val service: IAuthorServices,
    override val authenticationFacade: IAuthenticationFacade
) : IBasicControllerSkeleton<Author, IAuthorServices>
