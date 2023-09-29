package org.pentales.pentalesrest.controller

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.services.*
import org.springframework.web.bind.annotation.*

interface IBasicControllerSkeleton<Entity : IModel, Service : IGenericService<Entity>> {

    val service: Service

    // Why do we need the @get:GetMapping("") annotation?
    @get:GetMapping("")
    val all: List<Entity>?
        get() = service.findAll()

    @GetMapping("/{id}")
    fun getEntityById(
        @PathVariable
        id: String
    ): Entity {
        val idLong = id.toLong()
        return service.findById(idLong)
    }

    @PostMapping("")
    fun saveEntity(
        @RequestBody
        entity: Entity
    ): Entity {
        return service.saveNew(entity)
    }

    @PutMapping("/{id}")
    fun updateEntity(
        @PathVariable
        id: String,
        @RequestBody
        entity: Entity
    ): Entity {
        val idLong = id.toLong()
        entity.id = idLong
        return service.update(entity)
    }

    @DeleteMapping("/{id}")
    fun deleteEntity(
        @PathVariable
        id: String
    ) {
        val idLong = id.toLong()
        service.deleteById(idLong)
    }
}
