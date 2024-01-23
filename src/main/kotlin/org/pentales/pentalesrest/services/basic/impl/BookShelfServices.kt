package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.repo.base.*
import org.pentales.pentalesrest.repo.specifications.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*
import kotlin.reflect.*
import kotlin.reflect.full.*

@Service
class BookShelfServices(
    private val bookShelfRepository: BookShelfRepository
) : IBookShelfServices {

    override fun findAllByOwner(owner: User, pageable: Pageable): Page<BookShelf> {
        return bookShelfRepository.findAllByOwner(owner, pageable)
    }

    override fun findAllByOwnerUsername(username: String, pageable: Pageable): Page<BookShelf> {
        return bookShelfRepository.findAllByOwnerUsername(username, pageable)
    }

    override fun findReadLater(owner: User): BookShelf {
        return bookShelfRepository.findByOwnerAndReadLaterIsTrue(owner)
    }

    override fun findReadLater(username: String): BookShelf {
        return bookShelfRepository.findByOwnerUsernameAndReadLaterIsTrue(username)
    }

    override val repository: IRepoSpecification<BookShelf, Long>
        get() = bookShelfRepository
    override val modelProperties: Collection<KProperty1<BookShelf, *>>
        get() = BookShelf::class.memberProperties
    override val specification: ISpecification<BookShelf>
        get() = object : ISpecification<BookShelf> {}
}