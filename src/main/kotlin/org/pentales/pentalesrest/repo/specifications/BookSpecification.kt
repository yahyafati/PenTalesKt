package org.pentales.pentalesrest.repo.specifications

import jakarta.persistence.criteria.*
import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.intermediates.*
import org.springframework.data.jpa.domain.*

class BookSpecification {

    companion object {

        fun columnEquals(filterDtoList: List<FilterDto>): Specification<Book> {
            return Specification { root, _, criteriaBuilder ->

                val predicates = mutableListOf<Predicate>()
                filterDtoList.forEach { filter ->
                    if (filter.columnName == "authors") {
                        val bookAuthorJoin = root.join<Book, BookAuthor>("authors", JoinType.LEFT)
                        val authorJoin = bookAuthorJoin.join<BookAuthor, Author>("author", JoinType.LEFT)
                        val predicate = criteriaBuilder.equal(authorJoin.get<String>("name"), filter.value)
                        predicates.add(predicate)
                        return@forEach
                    }
                    if (filter.columnName == "genres") {
                        val bookGenreJoin = root.join<Book, BookGenre>("genres", JoinType.LEFT)
                        val predicate =
                            criteriaBuilder.equal(bookGenreJoin.get<String>("genre").get<String>("id"), filter.value)
                        predicates.add(predicate)
                        return@forEach
                    }
                    val predicate = when (filter.filterType) {
                        FilterTypes.EQUALS -> {
                            criteriaBuilder.equal(root.get<String>(filter.columnName), filter.value)
                        }

                        FilterTypes.GREATER_THAN -> {
                            criteriaBuilder.greaterThan(
                                root.get(filter.columnName), filter.value.toString()
                            )
                        }

                        FilterTypes.LESS_THAN -> {
                            criteriaBuilder.lessThan(root.get(filter.columnName), filter.value.toString())
                        }

                        FilterTypes.GREATER_THAN_OR_EQUAL -> {
                            criteriaBuilder.greaterThanOrEqualTo(
                                root.get(filter.columnName), filter.value.toString()
                            )
                        }

                        FilterTypes.LESS_THAN_OR_EQUAL -> {
                            criteriaBuilder.lessThanOrEqualTo(
                                root.get(filter.columnName), filter.value.toString()
                            )
                        }

                        FilterTypes.NOT_EQUALS -> {
                            criteriaBuilder.notEqual(root.get<String>(filter.columnName), filter.value)
                        }

                        FilterTypes.LIKE -> {
                            criteriaBuilder.like(root.get(filter.columnName), "%${filter.value}%")
                        }

                        FilterTypes.NOT_LIKE -> {
                            criteriaBuilder.notLike(root.get(filter.columnName), "%${filter.value}%")
                        }

                        FilterTypes.IN -> {
                            criteriaBuilder.`in`(root.get<String>(filter.columnName)).value(filter.value.toString())
                        }

                        FilterTypes.NOT_IN -> {
                            criteriaBuilder.`in`(root.get<String>(filter.columnName)).value(filter.value.toString())
                                .not()
                        }

                        FilterTypes.IS_NULL -> {
                            criteriaBuilder.isNull(root.get<String>(filter.columnName))
                        }

                        FilterTypes.IS_NOT_NULL -> {
                            criteriaBuilder.isNotNull(root.get<String>(filter.columnName))
                        }

                        FilterTypes.IS_EMPTY -> {
                            criteriaBuilder.isEmpty(root.get(filter.columnName))
                        }

                        FilterTypes.IS_NOT_EMPTY -> {
                            criteriaBuilder.isNotEmpty(root.get(filter.columnName))
                        }

                    }
                    predicates.add(predicate)
                }
                return@Specification criteriaBuilder.and(*predicates.toTypedArray())
            }
        }
    }
}

