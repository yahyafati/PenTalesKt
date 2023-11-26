package org.pentales.pentalesrest.services.basic

import org.pentales.pentalesrest.exceptions.*
import org.pentales.pentalesrest.models.*
import org.springframework.data.jpa.repository.*
import java.util.*
import kotlin.reflect.*

interface IGenericService<T : IModel> {

    val repository: JpaRepository<T, Long>
    val modelProperties: Collection<KProperty1<T, *>>
    val entityName: String
        get() {
            val className = javaClass.getSimpleName()
            val SERVICES_LENGTH = "Services".length
            return className.substring(0, className.length - SERVICES_LENGTH)
        }

    fun findById(id: Long): T {
        return repository.findById(id).orElseThrow { NoEntityWithIdException.create(entityName, id) }
    }

    fun findAll(): List<T> {
        return repository.findAll()
    }

    fun save(entity: T): T {
        return repository.save<T>(entity)
    }

    fun saveNew(entity: T): T {
        entity.id = 0L
        return save(entity)
    }

    fun update(id: Long, entity: T, updatedFields: List<String> = emptyList()): T {
        val existingEntity: Optional<T> = repository.findById(id)
        if (existingEntity.isEmpty) throw NoEntityWithIdException.create(entityName, id)
        entity.id = id
        if (updatedFields.isEmpty()) {
            return save(entity)
        }

        modelProperties.forEach { property ->
            if (updatedFields.contains(property.name)) {
                if (property is KMutableProperty<*>) {
                    property.setter.call(existingEntity.get(), property.get(entity))
                }
            }
        }

        return save(existingEntity.get())
    }

    fun deleteById(id: Long) {
        repository.deleteById(id)
    }
}
