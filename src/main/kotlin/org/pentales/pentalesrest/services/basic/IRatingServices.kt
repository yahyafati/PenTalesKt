package org.pentales.pentalesrest.services.basic

import org.pentales.pentalesrest.models.*
import org.springframework.data.domain.*
import kotlin.reflect.*

interface IRatingServices {

    val modelProperties: Collection<KProperty1<Rating, *>>
    val entityName: String
        get() {
            val className = javaClass.getSimpleName()
            val SERVICES_LENGTH = "Services".length
            return className.substring(0, className.length - SERVICES_LENGTH)
        }

    fun findByBookId(bookId: Long, pageable: Pageable): Page<Rating>
    fun findByUserId(userId: Long, pageable: Pageable): Page<Rating>
    fun findById(id: Long): Rating

    fun save(entity: Rating): Rating
    fun saveValue(value: Int, book: Book, user: User): Rating
    fun deleteById(id: Long)
    fun deleteByBookId(bookId: Long)
    fun deleteByUserId(userId: Long)

    fun likeRating(rating: Rating, user: User): Boolean
    fun unlikeRating(rating: Rating, user: User): Boolean

}