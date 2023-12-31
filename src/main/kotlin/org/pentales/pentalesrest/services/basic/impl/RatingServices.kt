package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.exceptions.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*
import org.springframework.transaction.annotation.*
import kotlin.reflect.*
import kotlin.reflect.full.*

@Service
class RatingServices(
    private val repository: RatingRepository,
) : IRatingServices {

    override val modelProperties: Collection<KProperty1<Rating, *>>
        get() = Rating::class.memberProperties

    override fun findByBookId(bookId: Long, pageable: Pageable): Page<Rating> {
        return repository.findAllByBook(Book(id = bookId), pageable)
    }

    override fun findByUserId(userId: Long, pageable: Pageable): Page<Rating> {
        return repository.findAllByUser(User(id = userId), pageable)
    }

    override fun findById(id: Long): Rating {
        return repository.findById(id).orElseThrow { NoEntityWithIdException.create(entityName, id.toString()) }
    }

    @Transactional
    override fun save(entity: Rating): Rating {
        return repository.save(entity)
    }

    @Transactional
    override fun saveNew(entity: Rating): Rating {
        return save(entity)
    }

    @Transactional
    override fun update(id: Long, entity: Rating, updatedFields: List<String>): Rating {
        val existingEntity: Rating =
            repository.findById(id).orElseThrow { NoEntityWithIdException.create(entityName, id.toString()) }
        entity.id = id
        if (updatedFields.isEmpty()) {
            return save(entity)
        }

        modelProperties.forEach { property ->
            if (updatedFields.contains(property.name)) {
                if (property is KMutableProperty<*>) {
                    property.setter.call(existingEntity, property.get(entity))
                }
            }
        }

        return save(existingEntity)
    }

    override fun deleteById(id: Long) {
        repository.deleteById(id)
    }

    override fun deleteByBookId(bookId: Long) {
        repository.deleteAllByBook(Book(id = bookId))
    }

    override fun deleteByUserId(userId: Long) {
        repository.deleteAllByUser(User(id = userId))
    }

}