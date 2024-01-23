package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.base.*
import org.springframework.data.domain.*

interface BookShelfRepository : IRepoSpecification<BookShelf, Long> {

    fun findAllByOwner(owner: User, pageable: Pageable): Page<BookShelf>
    fun findAllByOwnerUsername(username: String, pageable: Pageable): Page<BookShelf>
    fun findByOwnerAndReadLaterIsTrue(owner: User): BookShelf
    fun findByOwnerUsernameAndReadLaterIsTrue(username: String): BookShelf
    fun existsByOwnerAndId(owner: User, id: Long): Boolean
}