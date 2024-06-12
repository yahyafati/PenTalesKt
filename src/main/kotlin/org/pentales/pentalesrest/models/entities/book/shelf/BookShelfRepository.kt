package org.pentales.pentalesrest.models.entities.book.shelf

import org.pentales.pentalesrest.global.repo.base.*
import org.pentales.pentalesrest.models.entities.user.*
import org.springframework.data.domain.*

interface BookShelfRepository : IRepoSpecification<BookShelf, Long> {

    fun findAllByOwner(owner: User, pageable: Pageable): Page<BookShelf>
    fun findAllByOwnerUsername(username: String, pageable: Pageable): Page<BookShelf>
    fun findByOwnerAndReadLaterIsTrue(owner: User): BookShelf
    fun findByOwnerUsernameAndReadLaterIsTrue(username: String): BookShelf
    fun existsByOwnerAndId(owner: User, id: Long): Boolean
}