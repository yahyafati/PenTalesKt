package org.pentales.pentalesrest.controller.rest

import jakarta.servlet.http.*
import org.pentales.pentalesrest.components.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.*
import software.amazon.awssdk.services.s3.model.*
import java.net.*

@RequestMapping("/api/assets")
@RestController
class AssetController(
    private val s3Service: S3Service,
) {

    @GetMapping("/**", produces = [MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE])
    fun getResource(request: HttpServletRequest): ResponseEntity<ByteArray> {
        val url = request.requestURL.toString()
        val encoded = url.substringAfter("/api/assets/")
        val imagePath = URLDecoder.decode(encoded, "UTF-8")
        val response = try {
            s3Service.downloadFile(imagePath)
        } catch (e: NoSuchKeyException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found")
        }
        val image = response.readAllBytes()
        val contentType = response.response().contentType()
        return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(contentType))
            .body(image)
    }
}