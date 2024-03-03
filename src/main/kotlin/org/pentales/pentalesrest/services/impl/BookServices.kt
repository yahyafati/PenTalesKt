package org.pentales.pentalesrest.services.impl

import jakarta.transaction.*
import org.pentales.pentalesrest.components.*
import org.pentales.pentalesrest.components.configProperties.*
import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.dto.rating.*
import org.pentales.pentalesrest.exceptions.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.intermediates.*
import org.pentales.pentalesrest.models.keys.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.repo.base.*
import org.pentales.pentalesrest.repo.specifications.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.utils.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*
import org.springframework.web.multipart.*
import java.nio.file.*
import kotlin.jvm.optionals.*
import kotlin.reflect.*
import kotlin.reflect.full.*

@Service
class BookServices(
    private val bookRepository: BookRepository,
    private val bookGenreRepository: BookGenreRepository,
    private val bookAuthorRepository: BookAuthorRepository,
    private val bookSpecification: ISpecification<Book>,
    private val ratingRepository: RatingRepository,
    private val fileService: IFileService,
    private val bookFileRepository: BookFileRepository,
    private val authenticationFacade: IAuthenticationFacade,
    private val fileConfigProperties: FileConfigProperties,
    private val userBookProgressRepository: UserBookProgressRepository,
) : org.pentales.pentalesrest.services.IBookServices {

    override val repository: IRepoSpecification<Book, Long>
        get() = bookRepository
    override val modelProperties: Collection<KProperty1<Book, *>>
        get() = Book::class.memberProperties

    override val specification: ISpecification<Book>
        get() = bookSpecification

    val bookFileSpecification: ISpecification<BookFile>
        get() = object : ISpecification<BookFile> {}

    override fun getBookRatingInfo(bookId: Long, fetchBook: Boolean): BookRatingInfo {
        val bookRatingInfo = BookRatingInfo()
        val book = if (fetchBook) {
            bookRepository.findById(bookId).orElseThrow { NoEntityWithIdException.create("entityName", bookId) }
        } else Book(bookId)
        bookRatingInfo.book = book
        bookRatingInfo.rating = ratingRepository.findAverageRatingByBook(book) ?: 0.0
        bookRatingInfo.count = ratingRepository.countAllByBook(book)
        bookRatingInfo.reviewsCount = ratingRepository.countBookReviews(book)
        bookRatingInfo.distribution = ratingRepository.findRatingDistributionByBook(book)
        return bookRatingInfo
    }

    override fun getBookRatings(bookId: Long, pageable: Pageable): Page<Rating> {
        val book = Book(id = bookId)
        return ratingRepository.findAllByBook(book, pageable)
    }

    override fun getRelatedBooks(bookId: Long, pageable: Pageable): Page<Book> {
        // TODO: Implement this
        return bookRepository.findAll(pageable)
    }

    override fun getBookRatingByUser(bookId: Long, userId: Long): Rating? {
        val book = Book(id = bookId)
        val user = User(id = userId)
        return ratingRepository.findByBookAndUser(book, user)
    }

    override fun uploadEbook(file: MultipartFile, id: Long): BookFile {
        val user = authenticationFacade.forcedCurrentUser
        val book = bookRepository.findById(id).orElseThrow { NoEntityWithIdException.create("Book", id) }
        val existingBookFile = bookFileRepository.findByOwnerAndBook(user, book)
        if (existingBookFile != null) {
            val path = existingBookFile.path
            fileService.deleteFile(path)
        }

        val fileName = file.originalFilename ?: ""
        val uniqueFileName = FileUtil.getUniqueFilename(fileName)
        val byteArray = file.bytes

        val path = Paths.get(
            fileConfigProperties.upload.path,
            "books",
            book.id.toString(),
            "ebooks",
            uniqueFileName
        ).toString()
        fileService.uploadFile(path, byteArray)
        val bookFile = existingBookFile.apply {
            this?.path = path
        } ?: BookFile(
            path = path,
            owner = user,
            book = book
        )
        return bookFileRepository.save(bookFile)
    }

    override fun existsUserEbook(user: User, book: Book): Boolean {
        return bookFileRepository.existsByOwnerAndBook(user, book)
    }

    override fun updateEbookProgress(user: User, bookFile: BookFile, progress: String) {
        val userBookProgress = UserBookProgress(
            id = UserBookFileKey(
                userId = user.id,
                bookFileId = bookFile.id
            ),
            user = user,
            bookFile = bookFile,
            progress = progress
        )
        userBookProgressRepository.save(userBookProgress)
    }

    override fun getUserEbooks(user: User, book: Book, pageable: Pageable): Page<BookFile> {
        val specification = bookFileSpecification.columnEqualsOr(
            listOf(
                listOf(
                    FilterDto(
                        name = "owner.id",
                        value = user.id,
                        filterType = FilterTypes.EQUALS
                    ),
                    FilterDto(
                        name = "book.id",
                        value = book.id,
                        filterType = FilterTypes.EQUALS
                    )
                ),
                // OR
                listOf(
                    FilterDto(
                        name = "isPublic",
                        value = true,
                        filterType = FilterTypes.EQUALS
                    ),
                    FilterDto(
                        name = "book.id",
                        value = book.id,
                        filterType = FilterTypes.EQUALS
                    )
                )
            )
        )
        val bookFiles = bookFileRepository.findAll(
            specification,
            pageable
        )
        bookFiles.forEach {
            val userBookProgress = userBookProgressRepository.findById(
                UserBookFileKey(
                    userId = user.id,
                    bookFileId = it.id
                )
            ).getOrNull()
            it.__lastRead = userBookProgress?.updatedAt
            it.__progress = userBookProgress?.progress ?: "0"

        }
        return bookFiles
    }

    override fun getEbook(fileId: Long): BookFile {
        val bookFile =
            bookFileRepository.findById(fileId).orElseThrow { NoEntityWithIdException.create("BookFile", fileId) }
        val userBookProgress = userBookProgressRepository.findById(
            UserBookFileKey(
                userId = authenticationFacade.forcedCurrentUser.id,
                bookFileId = fileId
            )
        ).getOrNull()
        bookFile.__progress = userBookProgress?.progress ?: "0"
        bookFile.__lastRead = userBookProgress?.updatedAt
        return bookFile
    }

    private fun saveBookGenres(book: Book): List<BookGenre> {
        val bookGenres: List<BookGenre> = book.genres
        bookGenres.forEach { bookGenre: BookGenre ->
            val genre = Genre(bookGenre.id.genreId)
            bookGenre.book = book
            bookGenre.genre = genre
        }
        return bookGenreRepository.saveAll(bookGenres)
    }

    private fun saveBookAuthors(book: Book): List<BookAuthor> {
        val bookAuthors: List<BookAuthor> = book.authors
        bookAuthors.forEach { bookAuthor: BookAuthor ->
            val author = Author(bookAuthor.id.authorId)
            bookAuthor.book = book
            bookAuthor.author = author
        }
        return bookAuthorRepository.saveAll(bookAuthors)
    }

    @Transactional
    override fun save(entity: Book): Book {
        if (entity.id == 0L) {
            throw NoEntityWithIdException("No entity with id 0 found, use saveNew instead")
        }
        val savedBookGenres = saveBookGenres(entity)
        entity.genres = savedBookGenres
        val savedBookAuthors = saveBookAuthors(entity)
        entity.authors = savedBookAuthors
        val savedBook: Book = super.save(entity)
        return savedBook
    }

    @Transactional
    override fun saveNew(entity: Book): Book {
        val savedBook: Book = super.saveNew(entity)
        val savedBookGenres = saveBookGenres(savedBook)
        savedBook.genres = savedBookGenres
        val savedBookAuthors = saveBookAuthors(savedBook)
        savedBook.authors = savedBookAuthors
        return savedBook
    }
}
