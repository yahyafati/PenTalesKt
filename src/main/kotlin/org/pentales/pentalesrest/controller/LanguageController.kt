package org.pentales.pentalesrest.controller

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.services.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/language")
class LanguageController(private val languageServices: ILanguageServices) :
    IBasicControllerSkeleton<Language, ILanguageServices> {

    override val service: ILanguageServices
        get() = languageServices
}
