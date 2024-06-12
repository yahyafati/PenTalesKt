package org.pentales.pentalesrest.models.entities.book.genre

import org.pentales.pentalesrest.config.security.*
import org.pentales.pentalesrest.global.controller.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/genre")
class GenreController(
    override val service: IGenreServices, override val authenticationFacade: IAuthenticationFacade
) : IBasicControllerSkeleton<Genre, IGenreServices>
