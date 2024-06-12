package org.pentales.pentalesrest.models.misc.asset

import org.apache.tika.*
import org.slf4j.*
import org.springframework.context.annotation.*
import org.springframework.stereotype.*
import java.io.*

@Service
@Profile("!prod")
class LocalFileService : IFileService {

    companion object {

        private val LOG = LoggerFactory.getLogger(LocalFileService::class.java)
    }

    private val tika = Tika()

    init {
        LOG.info("Local File Service initialized")
    }

    override fun downloadFile(path: String): FileData {
        LOG.info("Downloading file from local with path: $path")
        val file = File(path)
        if (!file.exists()) {
            return FileData(
                contentType = "application/octet-stream",
                data = ByteArray(0)
            )
        }

        return FileData(
            contentType = tika.detect(file),
            data = file.readBytes()
        )
    }

    override fun uploadFile(path: String, bytes: ByteArray) {
        LOG.info("Uploading file to local with path: $path")
        val file = File(path)
        file.parentFile.mkdirs()
        file.writeBytes(bytes)
    }

    override fun deleteFile(path: String) {
        LOG.info("Deleting file from local with path: $path")
        val file = File(path)
        if (file.exists()) {
            file.delete()
        }
    }
}