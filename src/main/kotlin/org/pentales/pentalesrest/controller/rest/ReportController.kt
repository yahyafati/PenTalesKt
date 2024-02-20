package org.pentales.pentalesrest.controller.rest

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.dto.report.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.basic.*
import org.pentales.pentalesrest.utils.*
import org.springframework.data.domain.*
import org.springframework.http.*
import org.springframework.security.access.prepost.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/report")
@PreAuthorize("hasPermission('MODERATOR', 'ACCESS')")
class ReportController(
    private val reportService: IReportServices,
    private val authenticationFacade: IAuthenticationFacade,
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
        val pageRequest = ServletUtil.getPageRequest(page, size, sort, sortDirection)
        val reports = reportService.getAllReports(pageRequest, search)
        val dto = reports.map { ReportDto(it, ServletUtil.getBaseURLFromCurrentRequest()) }
        return ResponseEntity.ok(BasicResponseDto.ok(data = dto))
    }

    @PatchMapping("/{id}/approve")
    fun approveReport(
        @PathVariable
        id: Long
    ): ResponseEntity<BasicResponseDto<ReportDto>> {
        val user = authenticationFacade.forcedCurrentUser
        val report = reportService.approve(id, user)
        return ResponseEntity.ok(
            BasicResponseDto.ok(
                data = ReportDto(
                    report,
                    ServletUtil.getBaseURLFromCurrentRequest()
                )
            )
        )
    }

    @PatchMapping("/{id}/reject")
    fun rejectReport(
        @PathVariable
        id: Long
    ): ResponseEntity<BasicResponseDto<ReportDto>> {
        val user = authenticationFacade.forcedCurrentUser
        val report = reportService.reject(id, user)
        return ResponseEntity.ok(
            BasicResponseDto.ok(
                data = ReportDto(
                    report,
                    ServletUtil.getBaseURLFromCurrentRequest()
                )
            )
        )
    }
}