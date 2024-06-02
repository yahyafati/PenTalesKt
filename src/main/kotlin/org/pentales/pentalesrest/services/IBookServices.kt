package org.pentales.pentalesrest.services

import org.pentales.pentalesrest.dto.file.*
import org.pentales.pentalesrest.dto.rating.*
import org.pentales.pentalesrest.models.*
import org.springframework.data.domain.*
import org.springframework.web.multipart.*

interface IBookServices : IGenericService<Book> {

    fun getBookRatingInfo(bookId: Long, fetchBook: Boolean = false): BookRatingInfo
    fun getBookRatings(bookId: Long, pageable: Pageable): Page<Rating>
    fun getRelatedBooks(bookId: Long, pageable: Pageable): Page<Book>
    fun getBookRatingByUser(bookId: Long, userId: Long): Rating?
    fun uploadEbook(file: MultipartFile, id: Long): BookFile
    fun existsUserEbook(user: User, book: Book): Boolean
    fun updateEbookProgress(user: User, bookFile: BookFile, progress: String)
    fun getUserEbooks(user: User, book: Book, pageable: Pageable): Page<BookFile>
    fun getEbook(fileId: Long): BookFile
    fun deleteEbook(fileId: Long, user: User)
    fun uploadBookCover(bookId: Long, uploadDto: ImageUploadDto): Book

}
