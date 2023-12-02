package org.pentales.pentalesrest.repo

import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.keys.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*
import org.springframework.data.repository.query.*

interface RatingRepository : IRepoSpecification<Rating, UserBookKey> {

    fun findAllByBook(book: Book, pageable: Pageable): Page<Rating>
    fun countAllByBook(book: Book): Long

    @Query("SELECT COUNT(r) FROM Rating r WHERE r.book = :book AND r.review IS NOT NULL AND r.review != ''")
    fun countBookReviews(
        @Param("book")
        book: Book
    ): Long

    @Query("SELECT new org.pentales.pentalesrest.dto.RatingDistribution(r.value, COUNT(r)) FROM Rating r WHERE r.book = :book GROUP BY r.value")
    fun findRatingDistributionByBook(book: Book): List<RatingDistribution>

    fun findAllByUser(user: User, pageable: Pageable): Page<Rating>
    fun countAllByUser(user: User): Long

    @Query("SELECT COUNT(r) FROM Rating r WHERE r.user = :user AND r.review IS NOT NULL AND r.review != ''")
    fun countUserReviews(
        @Param("user")
        user: User
    ): Long

    fun deleteAllByBook(book: Book)
    fun deleteAllByUser(user: User)

}