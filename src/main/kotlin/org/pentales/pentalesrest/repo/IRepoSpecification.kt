package org.pentales.pentalesrest.repo

import org.springframework.data.jpa.repository.*
import org.springframework.data.repository.*

@NoRepositoryBean
interface IRepoSpecification<T, ID> : JpaRepository<T, ID>, JpaSpecificationExecutor<T> {}