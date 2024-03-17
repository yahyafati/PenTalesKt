package org.pentales.pentalesrest.components

import com.fasterxml.jackson.databind.*
import org.pentales.pentalesrest.components.configProperties.*
import org.pentales.pentalesrest.repo.*
import org.slf4j.*
import org.springframework.http.*
import org.springframework.scheduling.annotation.*
import org.springframework.stereotype.*
import org.springframework.transaction.annotation.*

@Service
class SentimentAnalysisService(
    private val sentimentAnalysisConfigProps: SentimentAnalysisConfigProperties,
    private val requestService: RequestService,
    private val objectMapper: ObjectMapper,
    private val commentRepository: CommentRepository,
    private val ratingRepository: RatingRepository,
) {

    companion object {

        private val LOG = LoggerFactory.getLogger(SentimentAnalysisService::class.java)
    }

    val pool: MutableSet<SentimentAnalysisRequest> = mutableSetOf()

    init {
        LOG.info("SentimentAnalysisService initialized")
    }

    fun addRequest(request: SentimentAnalysisRequest) {
        LOG.info("Adding request to pool: $request")
        pool.add(request)
    }

    private fun isSentimentOkay(sentiment: Double): Boolean {
        return sentiment > sentimentAnalysisConfigProps.threshold
    }

    @Scheduled(fixedRateString = "\${org.pen-tales.sentiment-analysis.interval-rate}")
    @Synchronized
    @Transactional
    fun processRequests() {
        try {
            if (pool.isEmpty()) {
                return
            }
            LOG.info("Processing sentiment analysis requests, pool size: ${pool.size}")

            val string = objectMapper.writeValueAsString(
                pool.map {
                    mapOf("id" to it.id, "text" to it.text, "type" to it.type)
                }
            )

            pool.clear()
            val response = requestService.sendRequestSync(
                sentimentAnalysisConfigProps.endpoint,
                HttpMethod.POST,
                string,
                SentimentAnalysisResponse::class.java
            )
            applySentimentAnalysis(response)
        } catch (e: Exception) {
            LOG.error("Error processing sentiment analysis requests", e)
        }
    }

    @Transactional
    fun applySentimentAnalysis(request: SentimentAnalysisResponse) {
        val commentIDs: MutableSet<Long> = mutableSetOf()
        val reviewIDs: MutableSet<Long> = mutableSetOf()

        request.data.forEach { response ->
            if (!isSentimentOkay(response.sentiment)) {
                when (response.type) {
                    ESentimentAnalysisRequestType.COMMENT -> commentIDs.add(response.id.toLong())
                    ESentimentAnalysisRequestType.REVIEW -> reviewIDs.add(response.id.toLong())
                }
            }
        }

        if (commentIDs.isNotEmpty()) {
            commentRepository.deleteAllById(commentIDs)
        }
        if (reviewIDs.isNotEmpty()) {
            ratingRepository.deleteAllById(reviewIDs)
        }

    }

}

