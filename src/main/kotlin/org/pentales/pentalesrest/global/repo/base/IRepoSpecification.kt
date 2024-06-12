package org.pentales.pentalesrest.global.repo.base

import org.springframework.data.jpa.repository.*
import org.springframework.data.repository.*

@NoRepositoryBean
interface IRepoSpecification<T, ID> : JpaRepository<T, ID>, JpaSpecificationExecutor<T> {}