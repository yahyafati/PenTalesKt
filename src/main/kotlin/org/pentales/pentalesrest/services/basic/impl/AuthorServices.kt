package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.jpa.repository.*
import org.springframework.stereotype.*
import kotlin.reflect.*
import kotlin.reflect.full.*

@Service
class AuthorServices(private val authorRepository: AuthorRepository) : IAuthorServices {

    override val repository: JpaRepository<Author, Long>
        get() = authorRepository
    override val modelProperties: Collection<KProperty1<Author, *>>
        get() = Author::class.memberProperties
}
