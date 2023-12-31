package org.pentales.pentalesrest.repo.base

import org.springframework.data.domain.*
import org.springframework.data.repository.*

@NoRepositoryBean
interface IReadOnlyRepository<T, ID> : Repository<T, ID> {

    fun findAll(): List<T>
    fun findAllById(ids: Iterable<ID>, pageable: Pageable): Page<T>
    fun findAll(pageable: Pageable): Page<T>
    fun findById(id: ID): T?
    fun existsById(id: ID): Boolean
    fun count(): Long
}