package org.pentales.pentalesrest.controller

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.services.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/publisher")
class PublisherController(private val publisherServices: IPublisherServices) :
    IBasicControllerSkeleton<Publisher, IPublisherServices> {

    override val service: IPublisherServices
        get() = publisherServices
}
