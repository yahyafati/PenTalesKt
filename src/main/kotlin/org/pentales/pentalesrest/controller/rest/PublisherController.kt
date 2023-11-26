package org.pentales.pentalesrest.controller.rest

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/publisher")
class PublisherController(
    override val service: IPublisherServices, override val authenticationFacade: IAuthenticationFacade
) : IBasicControllerSkeleton<Publisher, IPublisherServices>
