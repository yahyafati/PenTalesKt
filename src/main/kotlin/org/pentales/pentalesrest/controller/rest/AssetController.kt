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
    private val fileService: IFileService,
) {

    @GetMapping(
        "/**",
        produces = [
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE,
            MediaType.ALL_VALUE
        ]
    )
    fun getResource(request: HttpServletRequest): ResponseEntity<ByteArray> {
        val url = request.requestURL.toString()
        val encoded = url.substringAfter("/api/assets/")
        val imagePath = URLDecoder.decode(encoded, "UTF-8")
        val fileData = try {
            fileService.downloadFile(imagePath)
        } catch (e: NoSuchKeyException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found")
        }
        val image = fileData.data
        val contentType = fileData.contentType
        return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(contentType))
            .body(image)
    }
}