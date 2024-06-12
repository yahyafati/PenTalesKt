package org.pentales.pentalesrest.models.misc.asset

interface IFileService {

    fun downloadFile(path: String): FileData

    fun uploadFile(path: String, bytes: ByteArray)

    fun deleteFile(path: String)
}