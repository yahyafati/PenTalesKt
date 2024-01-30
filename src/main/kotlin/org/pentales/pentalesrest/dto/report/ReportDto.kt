package org.pentales.pentalesrest.dto.report

import org.pentales.pentalesrest.dto.rating.*
import org.pentales.pentalesrest.dto.ratingComment.*
import org.pentales.pentalesrest.dto.user.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*

data class ReportDto(
    var id: Long = 0,
    var user: UserDto = UserDto(),
    var rating: RatingDto? = null,
    var comment: CommentDto? = null,
    var type: EContentReportType = EContentReportType.REVIEW,
    var reasons: List<EContentReportReason> = listOf(),
    var description: String = "",
    var status: EReportStatus = EReportStatus.PENDING,
    var approvedBy: User? = null,
) {

    constructor(report: Report) : this(
        id = report.id,
        user = UserDto(report.user),
        rating = report.rating?.let { RatingDto(it) },
        comment = report.comment?.let { CommentDto(it) },
        type = report.type,
        reasons = report.eReasons,
        description = report.description,
        status = report.status,
        approvedBy = report.approvedBy,
    )

    fun toReport(): Report = Report(
        id = id,
        user = user.toUser(),
        rating = rating?.toRating(),
        comment = comment?.toComment(),
        type = type,
        reasons = Report.joinReasons(reasons),
        description = description,
        status = status,
        approvedBy = approvedBy,
    )
}