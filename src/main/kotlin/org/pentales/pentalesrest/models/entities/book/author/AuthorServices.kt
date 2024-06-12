package org.pentales.pentalesrest.models.entities.book.author

import org.pentales.pentalesrest.global.repo.base.*
import org.pentales.pentalesrest.global.repo.specifications.*
import org.springframework.stereotype.*
import kotlin.reflect.*
import kotlin.reflect.full.*

@Service
class AuthorServices(private val authorRepository: AuthorRepository) :
    IAuthorServices {

    override val repository: IRepoSpecification<Author, Long>
        get() = authorRepository
    override val modelProperties: Collection<KProperty1<Author, *>>
        get() = Author::class.memberProperties

    override val specification: ISpecification<Author>
        get() = object : ISpecification<Author> {}
}
