package org.pentales.pentalesrest.repo.specifications

import jakarta.persistence.criteria.*
import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.intermediates.*
import org.springframework.data.jpa.domain.*
import org.springframework.stereotype.*

@Component
class BookSpecification : ISpecification<Book> {

    override fun columnEquals(filterDtoList: List<FilterDto>): Specification<Book> {
        return Specification { root, query, criteriaBuilder ->

            val predicates = mutableListOf<Predicate>()
            filterDtoList.forEach { filter ->
                if (filter.name == "author") {
                    val bookAuthorJoin = root.join<Book, BookAuthor>("authors", JoinType.LEFT)
                    val authorJoin = bookAuthorJoin.join<BookAuthor, Author>("author", JoinType.LEFT)
                    val predicate = criteriaBuilder.equal(
                        criteriaBuilder.lower(authorJoin.get("name")), filter.value.toString().lowercase()
                    )
                    predicates.add(predicate)
                    return@forEach
                }
                if (filter.name == "authorIds" || filter.name == "genreIds") {
                    val inClause = if (filter.name == "authorIds") {
                        val bookAuthorJoin = root.join<Book, BookAuthor>("authors", JoinType.LEFT)
                        criteriaBuilder.`in`(bookAuthorJoin.get<Author>("author").get<Int>("id"))
                    } else {
                        val bookGenreJoin = root.join<Book, BookGenre>("genres", JoinType.LEFT)
                        criteriaBuilder.`in`(bookGenreJoin.get<Genre>("genre").get<Int>("id"))
                    }
                    val value = filter.value
                    if (value is List<*>) {
                        value.forEach { inClause.value(it.toString().toInt()) }
                    } else {
                        inClause.value(value.toString().toInt())
                    }
                    predicates.add(inClause)
                    return@forEach
                }
                if (filter.name == "averageRating") {
                    val ratingsJoin = root.join<Book, Rating>("ratings", JoinType.LEFT)
                    query.groupBy(root.get<Number>("id"))
                    val predicate = when (filter.filterType) {
                        FilterTypes.GREATER_THAN_OR_EQUAL -> {
                            criteriaBuilder.greaterThanOrEqualTo(
                                criteriaBuilder.avg(ratingsJoin.get("value")), filter.value.toString().toDouble()
                            )
                        }

                        FilterTypes.LESS_THAN_OR_EQUAL -> {
                            criteriaBuilder.lessThanOrEqualTo(
                                criteriaBuilder.avg(ratingsJoin.get("value")), filter.value.toString().toDouble()
                            )
                        }

                        else -> {
                            criteriaBuilder.equal(
                                criteriaBuilder.avg(ratingsJoin.get("value")), filter.value.toString()
                            )
                        }
                    }
                    query.having(predicate)
                    return@forEach
                }
                val predicate = super.basicComparePredicate(filter, root, criteriaBuilder)
                predicates.add(predicate)
            }
            return@Specification criteriaBuilder.and(*predicates.toTypedArray())
        }
    }
}

