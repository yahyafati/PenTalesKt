package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.base.*
import org.springframework.data.jpa.repository.*

interface BookRepository : IRepoSpecification<Book, Long> {

    @Query("SELECT b FROM ActivityBook b WHERE b.id = :id")
    fun findActivityBookById(id: Long): ActivityBook?

}
