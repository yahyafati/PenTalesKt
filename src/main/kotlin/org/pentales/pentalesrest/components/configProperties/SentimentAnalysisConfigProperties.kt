package org.pentales.pentalesrest.components.configProperties

import org.springframework.boot.context.properties.*
import org.springframework.stereotype.*
import kotlin.time.*
import kotlin.time.Duration.Companion.seconds

@Component
@ConfigurationProperties(prefix = "org.pen-tales.sentiment-analysis")
data class SentimentAnalysisConfigProperties(
    var endpoint: String = "http://localhost:5000/evaluate",
    var minPoolSize: Int = 5,
    var threshold: Double = 0.3,
    var intervalRate: Int = 10.seconds.toDouble(DurationUnit.MILLISECONDS).toInt(),
)

enum class ESentimentAnalysisRequestType {
    REVIEW,
    COMMENT
}

data class SentimentAnalysisResponseData(
    var id: String,
    var type: ESentimentAnalysisRequestType,
    var sentiment: Double,
)

data class SentimentAnalysisResponse(
    var data: List<SentimentAnalysisResponseData>,
)

data class SentimentAnalysisRequest(
    val type: ESentimentAnalysisRequestType,
    val id: Long,
    val text: String,
) {

    override fun hashCode(): Int {
        return id.hashCode() + type.hashCode() + text.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SentimentAnalysisRequest) return false
        return id == other.id && type == other.type && text == other.text
    }
}


