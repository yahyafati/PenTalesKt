package org.pentales.pentalesrest.components

import org.pentales.pentalesrest.components.configProperties.*
import org.slf4j.*
import org.springframework.context.annotation.*
import org.springframework.stereotype.*
import software.amazon.awssdk.core.*
import software.amazon.awssdk.core.sync.*
import software.amazon.awssdk.regions.*
import software.amazon.awssdk.services.s3.*
import software.amazon.awssdk.services.s3.model.*

@Service
@Profile("prod")
class S3FileService(
    private val awsConfigProperties: AWSConfigProperties
) : IFileService {

    companion object {

        private val LOG = LoggerFactory.getLogger(S3FileService::class.java)
    }

    private val client = S3Client.builder()
        .region(Region.of(awsConfigProperties.s3.region))
        .build()

    init {
        LOG.info("S3 Service initialized")
    }

    private fun uploadFileInner(key: String, file: ByteArray) {
        LOG.info("Uploading file to S3 with key: $key")
        val putObjectRequest = PutObjectRequest.builder()
            .bucket(awsConfigProperties.s3.bucket)
            .key(key)
            .build()

        client.putObject(putObjectRequest, RequestBody.fromBytes(file))
        LOG.info("File uploaded to S3 with key $key")
    }

    override fun uploadFile(path: String, bytes: ByteArray) {
        try {
            uploadFileInner(path, bytes)
        } catch (e: Exception) {
            LOG.error("Error uploading file to S3 with key: $path", e)
            throw e
        }
    }

    private fun downloadFileInner(key: String): ResponseInputStream<GetObjectResponse> {
        LOG.info("Downloading file from S3 with key: $key")
        val getObjectRequest = GetObjectRequest.builder()
            .bucket(awsConfigProperties.s3.bucket)
            .key(key)
            .build()

        val response = client.getObject(getObjectRequest)
        LOG.info("File downloaded from S3 with key $key")
        return response
    }

    override fun downloadFile(path: String): FileData {
        return try {
            val response = downloadFileInner(path)
            FileData(response.response().contentType(), response.readAllBytes())
        } catch (e: Exception) {
            LOG.error("Error downloading file from S3 with key: $path", e)
            throw e
        }
    }

    private fun deleteFileInner(key: String) {
        LOG.info("Deleting file from S3 with key: $key")
        val deleteObjectRequest = DeleteObjectRequest.builder()
            .bucket(awsConfigProperties.s3.bucket)
            .key(key)
            .build()

        client.deleteObject(deleteObjectRequest)
        LOG.info("File deleted from S3 with key $key")
    }

    override fun deleteFile(path: String) {
        try {
            deleteFileInner(path)
        } catch (e: Exception) {
            LOG.error("Error deleting file from S3 with key: $path", e)
            throw e
        }
    }

    fun close() {
        LOG.info("Closing S3 client")
        client.close()
        LOG.info("S3 client closed")
    }
}