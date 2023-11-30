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
    fun findById(bookId: Long, userId: Long): Rating

    fun save(entity: Rating): Rating
    fun update(bookId: Long, userId: Long, entity: Rating, updatedFields: List<String> = emptyList()): Rating

    fun deleteById(bookId: Long, userId: Long)
    fun deleteByBookId(bookId: Long)
    fun deleteByUserId(userId: Long)

}