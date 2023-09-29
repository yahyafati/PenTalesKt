package org.pentales.pentalesrest.controller

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.services.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/book")
class BookController(private val bookServices: IBookServices) : IBasicControllerSkeleton<Book, IBookServices> {

    override val service: IBookServices
        get() = bookServices
}
