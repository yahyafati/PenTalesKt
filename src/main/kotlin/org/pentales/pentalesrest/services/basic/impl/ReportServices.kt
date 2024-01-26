package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.stereotype.*

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
}