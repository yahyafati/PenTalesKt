package org.pentales.pentalesrest.components

import com.fasterxml.jackson.databind.*
import org.slf4j.*
import org.springframework.http.*
import org.springframework.stereotype.*
import org.springframework.web.reactive.function.client.*
import java.util.concurrent.*

@Service
class RequestService(
    private val webClientBuilder: WebClient.Builder,
    private val objectMapper: ObjectMapper,
) {

    companion object {

        private val LOG = LoggerFactory.getLogger(RequestService::class.java)
    }

    init {
        LOG.info("RequestService initialized")
    }

    fun sendRequest(url: String, method: HttpMethod, body: String): CompletableFuture<Any> {
        return CompletableFuture.supplyAsync {
            sendRequestSync(url, method, body)
        }
    }

    fun <T> sendRequest(url: String, method: HttpMethod, body: String, clazz: Class<T>): CompletableFuture<T> {
        return CompletableFuture.supplyAsync {
            val response = sendRequestSync(url, method, body).toString()
            objectMapper.readValue(response, clazz)
        }
    }

    fun sendRequestSync(url: String, method: HttpMethod, body: String): Any {
        val client = sendRequestSync(url, method, MediaType.APPLICATION_JSON, body)
        return client
    }

    fun sendRequestSync(url: String, method: HttpMethod, contentType: MediaType, body: String): String {
        val client = webClientBuilder
            .baseUrl(url)
            .build()
            .method(method)
            .contentType(contentType)
            .bodyValue(body)
            .retrieve()
            .bodyToMono(String::class.java)
            .block()

        return client!!
    }

    fun <T> sendRequestSync(url: String, method: HttpMethod, body: String, clazz: Class<T>): T {
        val client = sendRequestSync(url, method, body)
        return objectMapper.readValue(client.toString(), clazz)
    }

    fun downloadFile(url: String): ByteArray {
        val client = webClientBuilder
            .baseUrl(url)
            .build()
            .get()
            .retrieve()
            .bodyToMono(ByteArray::class.java)
            .block()

        return client!!
    }
}
