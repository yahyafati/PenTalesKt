package org.pentales.pentalesrest.models.entities.report.dto

import org.pentales.pentalesrest.models.entities.rating.comment.dtos.*
import org.pentales.pentalesrest.models.entities.rating.dto.*
import org.pentales.pentalesrest.models.entities.user.dto.*

data class ReportDto(
    var id: Long = 0,
    var user: UserDto = UserDto(),
    var rating: RatingDto? = null,
    var comment: CommentDto? = null,
    var type: org.pentales.pentalesrest.models.entities.report.EContentReportType = org.pentales.pentalesrest.models.entities.report.EContentReportType.REVIEW,
    var reasons: List<org.pentales.pentalesrest.models.entities.report.EContentReportReason> = listOf(),
    var description: String = "",
    var status: org.pentales.pentalesrest.models.entities.report.EReportStatus = org.pentales.pentalesrest.models.entities.report.EReportStatus.PENDING,
    var approvedBy: UserDto? = null,
) {

    constructor(report: org.pentales.pentalesrest.models.entities.report.Report, baseURL: String) : this(
        id = report.id,
        user = UserDto(report.user, baseURL),
        rating = report.rating?.let { RatingDto(it, baseURL) },
        comment = report.comment?.let { CommentDto(it, baseURL) },
        type = report.type,
        reasons = report.eReasons,
        description = report.description,
        status = report.status,
        approvedBy = report.approvedBy?.let { UserDto(it, baseURL) },
    )

    fun toReport(): org.pentales.pentalesrest.models.entities.report.Report =
        org.pentales.pentalesrest.models.entities.report.Report(
            id = id,
            user = user.toUser(),
            rating = rating?.toRating(),
            comment = comment?.toComment(),
            type = type,
            reasons = org.pentales.pentalesrest.models.entities.report.Report.joinReasons(reasons),
            description = description,
            status = status,
            approvedBy = approvedBy?.toUser(),
        )
}
