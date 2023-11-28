package org.pentales.pentalesrest.services.impl

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.keys.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.security.*
import org.pentales.pentalesrest.services.*
import org.springframework.stereotype.*
import org.springframework.transaction.annotation.*

@Service
class BookPageServices(
    private val bookRepository: BookRepository,
    private val ratingRepository: RatingRepository,
    private val authenticationFacade: IAuthenticationFacade
) : IBookPageServices {

    @Transactional
    override fun getBookPageData(bookId: Long): Map<String, Any> {
        val book = bookRepository.findById(bookId).orElseThrow()
        val ratings = ratingRepository.findAllByBook(book)
        val ratingCount = ratingRepository.countAllByBook(book)
        val reviewCount = ratingRepository.countBookReviews(book)
        val relatedBooks = bookRepository.findAll().map { BookDTO(it) }
        val currentUser = authenticationFacade.currentUser
        val currentUserRating = if (currentUser != null) {
            ratingRepository.findById(UserBookKey(userId = currentUser.id, bookId = book.id)).orElse(null)
        } else {
            null
        }
        val bookDto = BookDTO(book)

        var averageRating: Double = String.format("%.2f", ratings.map { it.value }.average()).toDouble()

        if (averageRating.isNaN()) {
            averageRating = 0.0
        }

        val map = mapOf(

            "book" to bookDto,

            "currentUserRating" to mapOf(
                "value" to currentUserRating?.value, "review" to currentUserRating?.review
            ),

            "rating" to mapOf(

                "average" to averageRating,

                "count" to ratingCount,

                "reviewCount" to reviewCount,

                "allRatings" to ratings.map { rating ->
                    mapOf(
                        "value" to rating.value,

                        "review" to rating.review,

                        "user" to mapOf(
                            "username" to rating.user?.username,
                            "name" to rating.user?.profile?.displayName,
                            "avatar" to rating.user?.profile?.avatar
                        )
                    )
                }),

            "relatedBooks" to relatedBooks.map { currentBook ->
                mapOf("id" to currentBook.id,
                    "title" to currentBook.title,
                    "coverImage" to book.coverImage,
                    "authors" to book.authors.map { author -> author.id.authorId })
            })

        return map
    }
}