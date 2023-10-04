package org.pentales.pentalesrest.controller

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.services.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

interface IBasicControllerSkeleton<Entity : IModel, Service : IGenericService<Entity>> {


    val service: Service

    // Why do we need the @get:GetMapping("") annotation?
    @get:GetMapping("")
    val all: ResponseEntity<List<Entity>>
        get() = ResponseEntity.ok(service.findAll())

    @GetMapping("/{id}")
    fun getEntityById(
        @PathVariable
        id: String
    ): ResponseEntity<Entity> {
        val idLong = id.toLong()
        return ResponseEntity.ok(service.findById(idLong))
    }

    @PostMapping("")
    fun saveEntity(
        @RequestBody
        entity: Entity
    ): ResponseEntity<Entity> {
        return ResponseEntity.ok(service.saveNew(entity))
    }

    @PutMapping("/{id}")
    fun updateEntity(
        @PathVariable
        id: Long,
        @RequestBody
        entity: Entity
    ): ResponseEntity<Entity> {
        entity.id = id
        return ResponseEntity.ok(service.update(id, entity))
    }

    @DeleteMapping("/{id}")
    fun deleteEntity(
        @PathVariable
        id: String
    ): ResponseEntity<Unit> {
        val idLong = id.toLong()
        service.deleteById(idLong)
        return ResponseEntity.ok().build()
    }
}
