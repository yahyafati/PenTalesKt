package org.pentales.pentalesrest.services.impl

import org.pentales.pentalesrest.models.Author
import org.pentales.pentalesrest.repo.AuthorRepository
import org.pentales.pentalesrest.repo.base.IRepoSpecification
import org.pentales.pentalesrest.repo.specifications.ISpecification
import org.springframework.stereotype.Service
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

@Service
class AuthorServices(private val authorRepository: AuthorRepository) :
    org.pentales.pentalesrest.services.IAuthorServices {

    override val repository: IRepoSpecification<Author, Long>
        get() = authorRepository
    override val modelProperties: Collection<KProperty1<Author, *>>
        get() = Author::class.memberProperties

    override val specification: ISpecification<Author>
        get() = object : ISpecification<Author> {}
}
