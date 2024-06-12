package org.pentales.pentalesrest.models.entities.report

import org.pentales.pentalesrest.global.repo.base.*
import org.springframework.data.domain.*

interface ReportRepository : IRepoSpecification<Report, Long> {

    fun findAllByDescriptionIgnoreCase(
        description: String,
        pageRequest: PageRequest
    ): Page<Report>
}