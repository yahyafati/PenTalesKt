package org.pentales.pentalesrest.services.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.*
import org.springframework.data.jpa.repository.*
import org.springframework.stereotype.*
import kotlin.reflect.*
import kotlin.reflect.full.*

@Service
class LanguageServices(private val languageRepository: LanguageRepository) : ILanguageServices {


    override val repository: JpaRepository<Language, Long>
        get() = languageRepository
    override val modelProperties: Collection<KProperty1<Language, *>>
        get() = Language::class.memberProperties
}
