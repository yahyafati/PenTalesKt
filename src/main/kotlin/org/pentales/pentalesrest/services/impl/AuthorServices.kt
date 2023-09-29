package org.pentales.pentalesrest.services.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.*
import org.springframework.data.jpa.repository.*
import org.springframework.stereotype.*

@Service
class AuthorServices(private val authorRepository: AuthorRepository) : IAuthorServices {

    override val repository: JpaRepository<Author, Long>
        get() = authorRepository
}
