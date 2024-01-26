package org.pentales.pentalesrest.models

import jakarta.persistence.*
import org.pentales.pentalesrest.models.converters.*
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.models.interfaces.*

@Entity
class Report(
    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User,

    @ManyToOne
    var rating: Rating?,

    @ManyToOne
    var comment: Comment?,

    @Enumerated(EnumType.STRING)
    var type: EContentReportType,

    @Convert(converter = ReasonsConverter::class)
    @Enumerated(EnumType.STRING)
    var reasons: List<EContentReportReason> = listOf(),

    var description: String,

    @Enumerated(EnumType.STRING)
    var status: EReportStatus = EReportStatus.PENDING,

    @ManyToOne
    var approvedBy: User? = null,
) : IModel()