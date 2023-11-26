package org.pentales.pentalesrest.services

interface IBookPageServices {

    fun getBookPageData(bookId: Long): Map<String, Any>
}