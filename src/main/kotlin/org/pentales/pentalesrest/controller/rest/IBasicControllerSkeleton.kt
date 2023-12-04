package org.pentales.pentalesrest.controller.rest

import org.pentales.pentalesrest.controller.rest.book.*
import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.interfaces.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

interface IBasicControllerSkeleton<Entity : IModel, Service : IGenericService<Entity>> {

    companion object {

        fun getPageRequest(
            page: Int?,
            size: Int?,
            sort: String?,
            direction: Sort.Direction?,
        ): PageRequest {
            val pageNumber = (page ?: 0).coerceAtLeast(0)
            val pageSize = (size ?: 10).coerceAtMost(RatingController.MAX_PAGE_SIZE).coerceAtLeast(1)
            val sortDirection = direction ?: Sort.Direction.ASC

            val pageRequest = if (sort.isNullOrEmpty()) {
                PageRequest.of(pageNumber, pageSize)
            } else {
                PageRequest.of(
                    pageNumber, pageSize, Sort.by(sortDirection, sort)
                )
            }
            return pageRequest
        }

        const val MAX_PAGE_SIZE = 50
    }

    val service: Service
    val authenticationFacade: IAuthenticationFacade

    @PostMapping("/search")
    fun searchAll(
        @RequestParam(defaultValue = "0")
        page: Int?,
        @RequestParam(defaultValue = "10")
        size: Int?,
        @RequestParam(defaultValue = "")
        sort: String?,
        @RequestParam(defaultValue = "ASC")
        direction: Sort.Direction?,
        @RequestBody(required = false)
        filters: List<FilterDto>? = listOf()
    ): ResponseEntity<Page<Entity>> {
        val pageRequest = getPageRequest(page, size, sort, direction)
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
