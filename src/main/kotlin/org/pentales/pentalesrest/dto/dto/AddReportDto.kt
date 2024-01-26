package org.pentales.pentalesrest.dto.dto

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*

data class AddReportDto(
    var ratingId: Long?,
    var commentId: Long?,
    var type: EContentReportType,
    var reasons: List<EContentReportReason> = listOf(),
    var description: String,
) {

    constructor(report: Report) : this(
        ratingId = report.rating?.id,
        commentId = report.comment?.id,
        type = report.type,
        reasons = report.reasons,
        description = report.description,
    )

    fun toReport(user: User, commentId: Long?, ratingId: Long?): Report {
        return Report(
            user = user,
            comment = commentId?.let {
                val comment = Comment()
                comment.id = it
                comment
            },
            rating = ratingId?.let { Rating(id = it) },
            type = type,
            reasons = reasons,
            description = description,
        )
    }
}
