package org.pentales.pentalesrest.controller

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.services.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/genre")
class GenreController(private val genreServices: IGenreServices) : IBasicControllerSkeleton<Genre, IGenreServices> {


    override val service: IGenreServices
        get() = genreServices
}
