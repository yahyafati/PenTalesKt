package org.pentales.pentalesrest.controller.rest

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

interface IBasicControllerSkeleton<Entity : IModel, Service : IGenericService<Entity>> {

    companion object {

        const val MAX_PAGE_SIZE = 50
    }

    val service: Service
    val authenticationFacade: IAuthenticationFacade

    @GetMapping("")
    fun getAll(
        @RequestParam(defaultValue = "0")
        page: Int?,
        @RequestParam(defaultValue = "10")
        size: Int?,
        @RequestParam(defaultValue = "")
        sort: String?,
        @RequestParam(defaultValue = "ASC")
        direction: Sort.Direction?
    ): ResponseEntity<Page<Entity>> {
        val pageNumber = page ?: 0
        val pageSize = size ?: 10
        val sortDirection = direction ?: Sort.Direction.ASC

        val pageRequest = if (sort.isNullOrEmpty()) {
            PageRequest.of(pageNumber, pageSize.coerceAtMost(MAX_PAGE_SIZE))
        } else {
            PageRequest.of(pageNumber, pageSize.coerceAtMost(MAX_PAGE_SIZE), Sort.by(sortDirection, sort))
        }
        return ResponseEntity.ok(service.findAll(pageRequest))
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
