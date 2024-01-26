package org.pentales.pentalesrest.dto.dto

import org.pentales.pentalesrest.dto.rating.*
import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*

data class ReportDto(
    var user: UserDto = UserDto(),
    var rating: RatingDto? = null,
    var comment: Comment? = null,
    var type: EContentReportType = EContentReportType.REVIEW,
    var reasons: List<EContentReportReason> = listOf(),
    var description: String = "",
    var status: EReportStatus = EReportStatus.PENDING,
    var approvedBy: User? = null,
) {

    constructor(report: Report) : this(
        user = UserDto(report.user),
        rating = report.rating?.let { RatingDto(it) },
        comment = report.comment,
        type = report.type,
        reasons = report.reasons,
        description = report.description,
        status = report.status,
        approvedBy = report.approvedBy,
    )

    fun toReport(): Report = Report(
        user = user.toUser(),
        rating = rating?.toRating(),
        comment = comment,
        type = type,
        reasons = reasons,
        description = description,
        status = status,
        approvedBy = approvedBy,
    )
}
