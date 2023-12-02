package org.pentales.pentalesrest.repo.specifications

import jakarta.persistence.criteria.*
import org.pentales.pentalesrest.dto.*
import org.springframework.data.jpa.domain.*

interface ISpecification<T> {

    fun basicComparePredicate(filter: FilterDto, root: Root<T>, criteriaBuilder: CriteriaBuilder): Predicate {
        return when (filter.filterType) {
            FilterTypes.EQUALS -> {
                criteriaBuilder.equal(root.get<String>(filter.name), filter.value)
            }

            FilterTypes.GREATER_THAN -> {
                criteriaBuilder.greaterThan(
                    root.get(filter.name), filter.value.toString()
                )
            }

            FilterTypes.LESS_THAN -> {
                criteriaBuilder.lessThan(root.get(filter.name), filter.value.toString())
            }

            FilterTypes.GREATER_THAN_OR_EQUAL -> {
                criteriaBuilder.greaterThanOrEqualTo(
                    root.get(filter.name), filter.value.toString()
                )
            }

            FilterTypes.LESS_THAN_OR_EQUAL -> {
                criteriaBuilder.lessThanOrEqualTo(
                    root.get(filter.name), filter.value.toString()
                )
            }

            FilterTypes.NOT_EQUALS -> {
                criteriaBuilder.notEqual(root.get<String>(filter.name), filter.value)
            }

            FilterTypes.LIKE -> {
                criteriaBuilder.like(root.get(filter.name), "%${filter.value}%")
            }

            FilterTypes.NOT_LIKE -> {
                criteriaBuilder.notLike(root.get(filter.name), "%${filter.value}%")
            }

            FilterTypes.IN -> {
                criteriaBuilder.`in`(root.get<String>(filter.name)).value(filter.value.toString())
            }

            FilterTypes.NOT_IN -> {
                criteriaBuilder.`in`(root.get<String>(filter.name)).value(filter.value.toString()).not()
            }

            FilterTypes.IS_NULL -> {
                criteriaBuilder.isNull(root.get<String>(filter.name))
            }

            FilterTypes.IS_NOT_NULL -> {
                criteriaBuilder.isNotNull(root.get<String>(filter.name))
            }

            FilterTypes.IS_EMPTY -> {
                criteriaBuilder.isEmpty(root.get(filter.name))
            }

            FilterTypes.IS_NOT_EMPTY -> {
                criteriaBuilder.isNotEmpty(root.get(filter.name))
            }
        }
    }

    fun columnEquals(filterDtoList: List<FilterDto>): Specification<T> {
        return Specification { root, query, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()
            filterDtoList.forEach { filter ->
                val predicate = basicComparePredicate(filter, root, criteriaBuilder)
                predicates.add(predicate)
            }
            return@Specification criteriaBuilder.and(*predicates.toTypedArray())
        }
    }
}