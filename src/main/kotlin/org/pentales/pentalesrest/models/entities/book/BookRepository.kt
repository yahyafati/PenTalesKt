package org.pentales.pentalesrest.models.entities.book

import org.pentales.pentalesrest.global.repo.base.*
import org.pentales.pentalesrest.models.entities.activity.book.*
import org.springframework.data.jpa.repository.*

interface BookRepository : IRepoSpecification<Book, Long> {

    @Query("SELECT b FROM ActivityBook b WHERE b.id = :id")
    fun findActivityBookById(id: Long): ActivityBook?

    @Query("SELECT b FROM Book b ORDER BY RANDOM() LIMIT 1")
    fun findRandomBook(): Book?

}
