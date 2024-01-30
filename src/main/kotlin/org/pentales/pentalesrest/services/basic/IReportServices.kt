package org.pentales.pentalesrest.services.basic

import org.pentales.pentalesrest.models.*
import org.springframework.data.domain.*

interface IReportServices {

    fun saveNew(report: Report): Report
    fun save(report: Report): Report
    fun approve(id: Long, approvedBy: User): Report
    fun reject(id: Long, reject: User): Report
    fun delete(id: Long)

    fun getAllReports(pageRequest: PageRequest, search: String?): Page<Report>
}