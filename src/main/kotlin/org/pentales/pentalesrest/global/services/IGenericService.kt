package org.pentales.pentalesrest.global.services

import org.pentales.pentalesrest.exceptions.*
import org.pentales.pentalesrest.global.dto.*
import org.pentales.pentalesrest.global.repo.base.*
import org.pentales.pentalesrest.global.repo.specifications.*
import org.pentales.pentalesrest.models.entities.interfaces.*
import org.springframework.data.domain.*
import java.util.*
import kotlin.reflect.*

interface IGenericService<T : IModel> {

    companion object {

        fun <T : IModel> create(
            repository: IRepoSpecification<T, Long>,
            modelProperties: Collection<KProperty1<T, *>>,
            specification: ISpecification<T>
        ): IGenericService<T> {
            return object : IGenericService<T> {
                override val repository: IRepoSpecification<T, Long> = repository
                override val modelProperties: Collection<KProperty1<T, *>> = modelProperties
                override val specification: ISpecification<T> = specification
            }
        }

    }

    val repository: IRepoSpecification<T, Long>
    val modelProperties: Collection<KProperty1<T, *>>
    val specification: ISpecification<T>

    val entityName: String
        get() {
            val className = javaClass.getSimpleName()
            val SERVICES_LENGTH = "Services".length
            return className.substring(0, className.length - SERVICES_LENGTH)
        }

    fun findById(id: Long): T {
        return repository.findById(id).orElseThrow { NoEntityWithIdException.create(entityName, id) }
    }

    fun findAll(pageable: Pageable, filters: List<FilterDto>): Page<T> {
        return repository.findAll(specification.columnEquals(filters), pageable)
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
