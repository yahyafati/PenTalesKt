package org.pentales.pentalesrest.controller.rest

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.dto.report.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.http.*
import org.springframework.security.access.prepost.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/report")
@PreAuthorize("hasPermission('MODERATOR', 'MODERATOR_ACCESS')")
class ReportController(
    private val reportService: IReportServices
) {

    @GetMapping("")
    fun getAllReports(
        @RequestParam(required = false, defaultValue = "0")
        page: Int?,
        @RequestParam(required = false, defaultValue = "10")
        size: Int?,
        @RequestParam(required = false, defaultValue = "id")
        sort: String?,
        @RequestParam(required = false, defaultValue = "ASC")
        sortDirection: Sort.Direction?,
        @RequestParam(required = false, defaultValue = "")
        search: String?,
    ): ResponseEntity<BasicResponseDto<Page<ReportDto>>> {
        val pageRequest = IBasicControllerSkeleton.getPageRequest(page, size, sort, sortDirection)
        val reports = reportService.getAllReports(pageRequest, search)
        val dto = reports.map { ReportDto(it) }
        return ResponseEntity.ok(BasicResponseDto.ok(data = dto))
    }
}