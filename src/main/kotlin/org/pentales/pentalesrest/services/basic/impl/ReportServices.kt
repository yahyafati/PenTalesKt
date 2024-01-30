package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*
import org.springframework.transaction.annotation.*

@Service
class ReportServices(
    private val reportRepository: ReportRepository
) : IReportServices {

    override fun saveNew(report: Report): Report {
        report.id = 0
        return reportRepository.save(report)
    }

    override fun save(report: Report): Report {
        return reportRepository.save(report)
    }

    override fun approve(id: Long, approvedBy: User): Report {
        val report = reportRepository.findById(id).get()
        report.status = EReportStatus.APPROVED
        report.approvedBy = approvedBy
        return save(report)
    }

    override fun reject(id: Long, reject: User): Report {
        val report = reportRepository.findById(id).get()
        report.status = EReportStatus.REJECTED
        report.approvedBy = reject
        return save(report)
    }

    override fun delete(id: Long) {
        reportRepository.deleteById(id)
    }

    @Transactional
    override fun getAllReports(pageRequest: PageRequest, search: String?): Page<Report> {
        return if (search.isNullOrEmpty()) {
            reportRepository.findAll(pageRequest)
        } else {
            reportRepository.findAllByDescriptionIgnoreCase(search, pageRequest)
        }
    }
}