package org.pentales.pentalesrest.services.impl

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.dto.book.*
import org.pentales.pentalesrest.dto.rating.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.repo.specifications.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.*
import org.pentales.pentalesrest.services.basic.*
import org.pentales.pentalesrest.utils.*
import org.springframework.data.domain.*
import org.springframework.stereotype.*
import org.springframework.transaction.annotation.*

@Service
class BookPageServices(
    private val bookRepository: BookRepository,
    private val ratingRepository: RatingRepository,
    private val authenticationFacade: IAuthenticationFacade,
    private val bookSpecification: ISpecification<Book>,
    private val userBookStatusServices: IUserBookStatusServices,
) : IBookPageServices {

    override fun getBooks(page: Int, size: Int, filters: List<FilterDto>): Page<BookDTO> {
        val pageable = PageRequest.of(page, size, Sort.by("id").descending())
        val books = bookRepository.findAll(bookSpecification.columnEquals(filters), pageable).map { BookDTO(it) }
//        val bookCount = bookRepository.count(BookSpecification.columnEquals(filters))
//        val bookList = books.map { BookDTO(it) }.toList()

        return books
    }

    @Transactional
    override fun getBookPageData(bookId: Long): Map<String, Any> {
        val book = bookRepository.findById(bookId).orElseThrow()
        val ratings = ratingRepository.findAllByBook(book, Pageable.ofSize(10))
        val ratingCount = ratingRepository.countAllByBook(book)
        val reviewCount = ratingRepository.countBookReviews(book)
        val relatedBooks = bookRepository.findAll(Pageable.ofSize(6)).map { BookDTO(it) }.toList()
        val currentUser = authenticationFacade.forcedCurrentUser
        val currentUserRating = ratingRepository.findTopByUserAndBookOrderByUpdatedAtDesc(currentUser, book)
        val bookDto = BookDTO(book)

        var averageRating: Double = String.format("%.2f", ratings.map { it.value }.average()).toDouble()

        if (averageRating.isNaN()) {
            averageRating = 0.0
        }

        val userBookStatus = userBookStatusServices.getBookStatus(bookId = bookId, userId = currentUser.id)

        val map = mapOf(

            "book" to bookDto,

            "bookStatus" to userBookStatus,

            "currentUserRating" to mapOf(
                "value" to currentUserRating?.value, "review" to currentUserRating?.review
            ),

            "rating" to mapOf(

                "average" to averageRating,

                "count" to ratingCount,

                "reviewCount" to reviewCount,

                "allRatings" to ratings.map { RatingDto(it, ServletUtil.getBaseURLFromCurrentRequest()) },

                "distribution" to ratingRepository.findRatingDistributionByBook(book).map { ratingDistribution ->
                    mapOf(
                        "value" to ratingDistribution.value, "count" to ratingDistribution.count
                    )
                }

            ),

            "relatedBooks" to relatedBooks.map { relatedBook ->
                mapOf(
                    "id" to relatedBook.id,
                    "title" to relatedBook.title,
                    "coverImage" to relatedBook.coverImage,
                    "authors" to relatedBook.authors,
                )
            })

        return map
    }
}