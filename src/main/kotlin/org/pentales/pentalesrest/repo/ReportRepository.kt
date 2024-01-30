package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.base.*
import org.springframework.data.domain.*

interface ReportRepository : IRepoSpecification<Report, Long> {

    fun findAllByDescriptionIgnoreCase(description: String, pageRequest: PageRequest): Page<Report>
}