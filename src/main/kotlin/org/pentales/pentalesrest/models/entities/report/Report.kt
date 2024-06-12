package org.pentales.pentalesrest.models.entities.report

import jakarta.persistence.*
import org.pentales.pentalesrest.models.entities.interfaces.*
import org.pentales.pentalesrest.models.entities.rating.Rating
import org.pentales.pentalesrest.models.entities.rating.comment.*
import org.pentales.pentalesrest.models.entities.user.*

@Entity
class Report(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User = User(),

    @ManyToOne
    var rating: Rating? = null,

    @ManyToOne
    var comment: Comment? = null,

    @Enumerated(EnumType.STRING)
    var type: EContentReportType = EContentReportType.REVIEW,

    var reasons: String = "",

    var description: String = "",

    @Enumerated(EnumType.STRING)
    var status: EReportStatus = EReportStatus.PENDING,

    @ManyToOne
    var approvedBy: User? = null,
) : IModel() {

    companion object {

        fun splitReasons(reasons: String): List<EContentReportReason> {
            return reasons
                .trim()
                .split(",")
                .filter { it.isNotEmpty() }
                .map { EContentReportReason.valueOf(it) }
        }

        fun joinReasons(reasons: List<EContentReportReason>): String {
            return reasons.joinToString(",") { it.name }.trim()
        }
    }

    @Transient
    var eReasons: List<EContentReportReason> = listOf()
        get() {
            return splitReasons(reasons).toMutableList()
        }
        set(value) {
            field = value
            val reasons = joinReasons(value)
            if (this.reasons != reasons) {
                this.reasons = reasons
            }
        }

}