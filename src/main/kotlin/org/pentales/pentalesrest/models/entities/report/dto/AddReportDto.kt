package org.pentales.pentalesrest.models.entities.report.dto

import org.pentales.pentalesrest.models.entities.rating.*
import org.pentales.pentalesrest.models.entities.rating.comment.*
import org.pentales.pentalesrest.models.entities.user.*

data class AddReportDto(
    var ratingId: Long?,
    var commentId: Long?,
    var type: org.pentales.pentalesrest.models.entities.report.EContentReportType,
    var reasons: List<org.pentales.pentalesrest.models.entities.report.EContentReportReason> = listOf(),
    var description: String,
) {

    constructor(report: org.pentales.pentalesrest.models.entities.report.Report) : this(
        ratingId = report.rating?.id,
        commentId = report.comment?.id,
        type = report.type,
        reasons = report.eReasons,
        description = report.description,
    )

    fun toReport(
        user: User,
        commentId: Long?,
        ratingId: Long?
    ): org.pentales.pentalesrest.models.entities.report.Report {
        return org.pentales.pentalesrest.models.entities.report.Report(
            user = user,
            comment = commentId?.let {
                val comment = Comment()
                comment.id = it
                comment
            },
            rating = ratingId?.let { Rating(id = it) },
            type = type,
            reasons = org.pentales.pentalesrest.models.entities.report.Report.joinReasons(reasons),
            description = description,
        )
    }
}
