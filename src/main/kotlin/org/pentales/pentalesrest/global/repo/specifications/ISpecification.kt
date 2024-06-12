package org.pentales.pentalesrest.global.repo.specifications

import jakarta.persistence.criteria.*
import org.pentales.pentalesrest.global.dto.*
import org.springframework.data.jpa.domain.*

interface ISpecification<T> {

    fun getSubProperty(root: Root<T>, properties: List<String>): Path<String> {
        var subProperty = root.get<String>(properties.first())
        for (i in 1 until properties.size) {
            subProperty = subProperty.get(properties[i])
        }
        return subProperty

    }

    fun basicComparePredicate(filter: FilterDto, root: Root<T>, criteriaBuilder: CriteriaBuilder): Predicate {
        if (filter.filterType == FilterTypes.IS_EMPTY || filter.filterType == FilterTypes.IS_NOT_EMPTY) {
            if (filter.name.contains(".")) {
                throw IllegalArgumentException("Can't use IS_EMPTY or IS_NOT_EMPTY with nested properties, not yet implemented")
            }
        }
        val properties = filter.name.split(".")
        val subProperty: Path<String> = getSubProperty(root, properties)

        return when (filter.filterType) {
            FilterTypes.EQUALS -> {
                criteriaBuilder.equal(subProperty, filter.value)
            }

            FilterTypes.GREATER_THAN -> {
                criteriaBuilder.greaterThan(
                    subProperty, filter.value.toString()
                )
            }

            FilterTypes.LESS_THAN -> {
                criteriaBuilder.lessThan(subProperty, filter.value.toString())
            }

            FilterTypes.GREATER_THAN_OR_EQUAL -> {
                criteriaBuilder.greaterThanOrEqualTo(
                    subProperty, filter.value.toString()
                )
            }

            FilterTypes.LESS_THAN_OR_EQUAL -> {
                criteriaBuilder.lessThanOrEqualTo(
                    subProperty, filter.value.toString()
                )
            }

            FilterTypes.NOT_EQUALS -> {
                criteriaBuilder.notEqual(subProperty, filter.value)
            }

            FilterTypes.LIKE -> {
                criteriaBuilder.like(
                    criteriaBuilder.lower(subProperty),
                    "%${filter.value}%".lowercase()
                )
            }

            FilterTypes.NOT_LIKE -> {
                criteriaBuilder.notLike(criteriaBuilder.lower(subProperty), "%${filter.value}%".lowercase())
            }

            FilterTypes.IN -> {
                criteriaBuilder.`in`(subProperty).value(filter.value.toString())
            }

            FilterTypes.NOT_IN -> {
                criteriaBuilder.`in`(subProperty).value(filter.value.toString()).not()
            }

            FilterTypes.IS_NULL -> {
                criteriaBuilder.isNull(subProperty)
            }

            FilterTypes.IS_NOT_NULL -> {
                criteriaBuilder.isNotNull(subProperty)
            }

            FilterTypes.IS_EMPTY -> {
                criteriaBuilder.isEmpty(root.get(filter.name))
            }

            FilterTypes.IS_NOT_EMPTY -> {
                criteriaBuilder.isNotEmpty(root.get(filter.name))
            }
        }
    }

    fun basicComparePredicate(
        filterDtoList: List<FilterDto>,
        root: Root<T>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        val predicates = mutableListOf<Predicate>()
        filterDtoList.forEach { filter ->
            val predicate = basicComparePredicate(filter, root, criteriaBuilder)
            predicates.add(predicate)
        }
        return criteriaBuilder.and(*predicates.toTypedArray())
    }

    fun columnEquals(filterDtoList: List<FilterDto>): Specification<T> {
        return Specification { root, query, criteriaBuilder ->
            return@Specification basicComparePredicate(filterDtoList, root, criteriaBuilder)
        }
    }

    fun columnEqualsOr(filterDtoList: List<List<FilterDto>>): Specification<T> {
        return Specification { root, query, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()
            filterDtoList.forEach { filterDtoList ->
                val predicate = basicComparePredicate(filterDtoList, root, criteriaBuilder)
                predicates.add(predicate)
            }
            return@Specification criteriaBuilder.or(*predicates.toTypedArray())
        }
    }
}