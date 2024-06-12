package org.pentales.pentalesrest.models.entities.book.genre

import org.pentales.pentalesrest.global.repo.base.*
import org.pentales.pentalesrest.global.repo.specifications.*
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
