package org.pentales.pentalesrest.config

import com.fasterxml.jackson.databind.*
import org.springframework.context.annotation.*
import org.springframework.http.*
import org.springframework.http.converter.*
import org.springframework.stereotype.*
import org.springframework.util.*
import java.io.*
import java.nio.charset.*

@Component
@Primary
class MappingJackson2String2HttpMessageConverter(
    private val objectMapper: ObjectMapper
) : AbstractHttpMessageConverter<Any>(

) {

    //...
    override fun supports(clazz: Class<*>): Boolean {
//        return supportedClasses.contains(clazz)
        return true
    }

    override fun canWrite(mediaType: MediaType?): Boolean {
        return mediaType == null || mediaType.toString().contains(MediaType.APPLICATION_JSON_VALUE)
    }

    @Throws(IOException::class, HttpMessageNotReadableException::class)
    override fun readInternal(clazz: Class<*>, inputMessage: HttpInputMessage): String {
//        val charset: Charset = getContentTypeCharset(inputMessage.headers.contentType)
        val charset: Charset = inputMessage.headers.contentType?.charset ?: Charsets.UTF_8
        return StreamUtils.copyToString(inputMessage.body, charset)
    }

    @Throws(IOException::class, HttpMessageNotWritableException::class)
    override fun writeInternal(linkedHashMap: Any, outputMessage: HttpOutputMessage) {
        outputMessage.headers.acceptCharset = listOf(Charsets.UTF_8)
//        val charset: Charset = getContentTypeCharset(outputMessage.headers.contentType)
        val charset: Charset = outputMessage.headers.contentType?.charset ?: Charsets.UTF_8
        val result = if (linkedHashMap is String) linkedHashMap else objectMapper.writeValueAsString(linkedHashMap)
        StreamUtils.copy(result, charset, outputMessage.body)
    }
}