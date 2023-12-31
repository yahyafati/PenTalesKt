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
class GenreServices(private val genreRepository: GenreRepository) : IGenreServices {

    override val repository: IRepoSpecification<Genre, Long>
        get() = genreRepository
    override val modelProperties: Collection<KProperty1<Genre, *>>
        get() = Genre::class.memberProperties
    override val specification: ISpecification<Genre>
        get() = object : ISpecification<Genre> {}
}
