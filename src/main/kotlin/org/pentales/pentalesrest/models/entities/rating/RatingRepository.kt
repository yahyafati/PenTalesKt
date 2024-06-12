package org.pentales.pentalesrest.models.entities.rating

import org.pentales.pentalesrest.global.repo.base.*
import org.pentales.pentalesrest.models.entities.book.*
import org.pentales.pentalesrest.models.entities.rating.dto.*
import org.pentales.pentalesrest.models.entities.user.*
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.*
import org.springframework.data.repository.query.*

interface RatingRepository : IRepoSpecification<Rating, Long> {

    fun findAllByBook(book: Book, pageable: Pageable): Page<Rating>
    fun countAllByBook(book: Book): Long
    fun findByBookAndUser(book: Book, user: User): Rating?

    @Query("SELECT COUNT(r) FROM Rating r WHERE r.book = :book AND r.review IS NOT NULL AND r.review != ''")
    fun countBookReviews(
        @Param("book")
        book: Book
    ): Long

    @Query("SELECT new org.pentales.pentalesrest.models.entities.rating.dto.RatingDistribution(r.value, COUNT(r)) FROM Rating r WHERE r.book = :book GROUP BY r.value")
    fun findRatingDistributionByBook(book: Book): List<RatingDistribution>

    fun findAllByUser(user: User, pageable: Pageable): Page<Rating>
    fun countAllByUser(user: User): Long

    fun findTopByUserAndBookOrderByUpdatedAtDesc(
        user: User,
        book: Book
    ): Rating?

    @Query("SELECT COUNT(r) FROM Rating r WHERE r.user = :user AND r.review IS NOT NULL AND r.review != ''")
    fun countUserReviews(
        @Param("user")
        user: User
    ): Long

    @Query("SELECT AVG(r.value) FROM Rating r WHERE r.book = :book")
    fun findAverageRatingByBook(
        @Param("book")
        book: Book
    ): Double?

    @Query("SELECT AVG(r.value) FROM Rating r WHERE r.user = :user")
    fun findAverageRatingByUser(
        @Param("user")
        user: User
    ): Double?

    fun deleteAllByBook(book: Book)
    fun deleteAllByUser(user: User)
    fun deleteByIdAndUser(id: Long, user: User): Long

}