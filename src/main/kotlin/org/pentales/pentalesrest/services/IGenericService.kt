package org.pentales.pentalesrest.services

import org.pentales.pentalesrest.exceptions.*
import org.pentales.pentalesrest.models.*
import org.springframework.data.jpa.repository.*
import java.util.*

interface IGenericService<T : IModel> {

    val repository: JpaRepository<T, Long>
    val entityName: String
        get() {
            val className = javaClass.getSimpleName()
            val SERVICES_LENGTH = "Services".length
            return className.substring(0, className.length - SERVICES_LENGTH)
        }

    fun findById(id: Long): T {
        return repository.findById(id).orElseThrow { NoEntityWithIdException.create(entityName, id) }
    }

    fun findAll(): List<T>? {
        return repository.findAll()
    }

    fun save(entity: T): T {
        println("Saving entity: $entity")
        return repository.save<T>(entity)
    }

    fun saveNew(entity: T): T {
        entity.id = 0L
        return save(entity)
    }

    fun update(entity: T): T {
        val existingEntity: Optional<T> = repository.findById(entity.id)
        if (existingEntity.isEmpty) {
            throw NoEntityWithIdException.create(entityName, entity.id)
        }
        return save(entity)
    }

    fun deleteById(id: Long) {
        repository.deleteById(id)
    }
}
