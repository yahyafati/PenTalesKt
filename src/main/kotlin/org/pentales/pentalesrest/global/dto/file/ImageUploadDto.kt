package org.pentales.pentalesrest.global.dto.file

import org.springframework.web.multipart.*
import java.awt.*

data class ImageUploadDto(
    val file: MultipartFile? = null,
    var croppedArea: Rectangle? = null,
    var croppedAreaPixels: Rectangle? = null,
)
