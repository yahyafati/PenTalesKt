package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.jpa.repository.*
import org.springframework.stereotype.*
import kotlin.reflect.*
import kotlin.reflect.full.*

@Service
class GenreServices(private val genreRepository: GenreRepository) : IGenreServices {

    override val repository: JpaRepository<Genre, Long>
        get() = genreRepository
    override val modelProperties: Collection<KProperty1<Genre, *>>
        get() = Genre::class.memberProperties
}
