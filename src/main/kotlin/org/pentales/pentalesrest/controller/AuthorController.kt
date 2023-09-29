package org.pentales.pentalesrest.controller

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.services.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/author")
class AuthorController(private val authorServices: IAuthorServices) :
    IBasicControllerSkeleton<Author, IAuthorServices> {

    override val service: IAuthorServices
        get() = authorServices
}
