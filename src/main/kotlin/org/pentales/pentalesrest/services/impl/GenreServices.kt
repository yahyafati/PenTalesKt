package org.pentales.pentalesrest.services.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.*
import org.springframework.data.jpa.repository.*
import org.springframework.stereotype.*

@Service
class GenreServices(private val genreRepository: GenreRepository) : IGenreServices {

    override val repository: JpaRepository<Genre, Long>
        get() = genreRepository
}
