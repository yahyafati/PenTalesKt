package org.pentales.pentalesrest.controller

import org.pentales.pentalesrest.config.security.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.services.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/genre")
class GenreController(
    override val service: IGenreServices, override val authenticationFacade: IAuthenticationFacade
) : IBasicControllerSkeleton<Genre, IGenreServices>
