package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.repo.base.IRepoSpecification
import org.pentales.pentalesrest.repo.specifications.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.stereotype.*
import kotlin.reflect.*
import kotlin.reflect.full.*

@Service
class AuthorServices(private val authorRepository: AuthorRepository) : IAuthorServices {

    override val repository: IRepoSpecification<Author, Long>
        get() = authorRepository
    override val modelProperties: Collection<KProperty1<Author, *>>
        get() = Author::class.memberProperties

    override val specification: ISpecification<Author>
        get() = object : ISpecification<Author> {}
}
