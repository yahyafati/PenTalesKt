package org.pentales.pentalesrest.dto.file

import org.springframework.web.multipart.*
import java.awt.*

data class ImageUploadDto(
    val file: MultipartFile? = null,
    var croppedArea: Rectangle? = null,
    var croppedAreaPixels: Rectangle? = null,
)
