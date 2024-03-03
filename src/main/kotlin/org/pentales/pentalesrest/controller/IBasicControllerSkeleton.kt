package org.pentales.pentalesrest.controller

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.interfaces.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.basic.*
import org.pentales.pentalesrest.utils.*
import org.springframework.data.domain.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

interface IBasicControllerSkeleton<Entity : IModel, Service : IGenericService<Entity>> {

    companion object

    val service: Service
    val authenticationFacade: IAuthenticationFacade

    @PostMapping("/search")
    fun searchAll(
        @RequestBody(required = false)
        filters: List<FilterDto>? = listOf()
    ): ResponseEntity<Page<Entity>> {
        val pageRequest = ServletUtil.getPageRequest()
        val response = service.findAll(pageRequest, filters ?: listOf())
        return ResponseEntity.ok(response)
    }

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
