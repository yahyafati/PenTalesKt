package org.pentales.pentalesrest.repo.specifications

import jakarta.persistence.criteria.*
import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.*
import org.springframework.data.jpa.domain.*
import org.springframework.stereotype.*

@Component
class UserProfileSpecification : ISpecification<UserProfile> {

    override fun columnEquals(filterDtoList: List<FilterDto>): Specification<UserProfile> {
        return Specification { root, query, criteriaBuilder ->

            val predicates = mutableListOf<Predicate>()
            filterDtoList.forEach { filter ->
                if (filter.name == "name") {
                    val predicate = criteriaBuilder.or(
                        criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("firstName")),
                            "%${filter.value.toString().lowercase()}%"
                        ),
                        criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("lastName")),
                            "%${filter.value.toString().lowercase()}%"
                        )
                    )
                    predicates.add(predicate)
                    return@forEach
                } else if (filter.name == "username") {
                    val predicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get<User>("user").get("username")),
                        "%${filter.value.toString().lowercase()}%"
                    )
                    predicates.add(predicate)
                    return@forEach
                }
                val predicate = super.basicComparePredicate(filter, root, criteriaBuilder)
                predicates.add(predicate)
            }
            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }

}