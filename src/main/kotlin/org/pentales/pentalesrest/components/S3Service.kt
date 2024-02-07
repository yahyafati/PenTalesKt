package org.pentales.pentalesrest.components

import org.pentales.pentalesrest.components.configProperties.*
import org.slf4j.*
import org.springframework.stereotype.*
import software.amazon.awssdk.core.*
import software.amazon.awssdk.core.sync.*
import software.amazon.awssdk.regions.*
import software.amazon.awssdk.services.s3.*
import software.amazon.awssdk.services.s3.model.*

@Service
class S3Service(
    private val awsConfigProperties: AWSConfigProperties
) {

    companion object {

        private val LOG = LoggerFactory.getLogger(S3Service::class.java)
    }

    private val client = S3Client.builder()
        .region(Region.of(awsConfigProperties.s3.region))
        .build()

    init {
        LOG.info("S3 Service initialized")
    }

    fun uploadFile(key: String, file: ByteArray) {
        try {
            LOG.info("Uploading file to S3 with key: $key")
            val putObjectRequest = PutObjectRequest.builder()
                .bucket(awsConfigProperties.s3.bucket)
                .key(key)
                .build()

            client.putObject(putObjectRequest, RequestBody.fromBytes(file))
            LOG.info("File uploaded to S3 with key $key")
        } catch (e: Exception) {
            LOG.error("Error uploading file to S3 with key: $key", e)
            throw e
        }
    }

    fun downloadFile(key: String): ResponseInputStream<GetObjectResponse> {
        val response = try {
            LOG.info("Downloading file from S3 with key: $key")
            val getObjectRequest = GetObjectRequest.builder()
                .bucket(awsConfigProperties.s3.bucket)
                .key(key)
                .build()

            val res = client.getObject(getObjectRequest)
            LOG.info("File downloaded from S3 with key $key")
            res
        } catch (e: Exception) {
            LOG.error("Error downloading file from S3 with key: $key", e)
            throw e
        }

        return response
    }

    fun deleteFile(key: String) {
        try {
            LOG.info("Deleting file from S3 with key: $key")
            val deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(awsConfigProperties.s3.bucket)
                .key(key)
                .build()

            client.deleteObject(deleteObjectRequest)
            LOG.info("File deleted from S3 with key $key")
        } catch (e: Exception) {
            LOG.error("Error deleting file from S3 with key: $key", e)
            throw e
        }
    }

    fun close() {
        LOG.info("Closing S3 client")
        client.close()
        LOG.info("S3 client closed")
    }
}