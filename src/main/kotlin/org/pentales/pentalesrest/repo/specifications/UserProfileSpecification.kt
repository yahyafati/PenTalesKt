package org.pentales.pentalesrest.repo.specifications

import jakarta.persistence.criteria.*
import org.pentales.pentalesrest.dto.*
import org.pentales.pentalesrest.models.*
import org.springframework.stereotype.*

@Component
class UserProfileSpecification : ISpecification<UserProfile> {

    companion object {

        const val FULL_NAME_FILTER = "fullName"
        const val USERNAME_FILTER = "username"
    }

    override fun basicComparePredicate(
        filter: FilterDto,
        root: Root<UserProfile>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return when (filter.name) {
            FULL_NAME_FILTER -> {
                return when (filter.filterType) {
                    FilterTypes.LIKE -> {
                        val name = filter.value.toString().trim().lowercase()

                        val fullNamePredicate = criteriaBuilder.like(
                            criteriaBuilder.lower(
                                criteriaBuilder.concat(
                                    criteriaBuilder.concat(
                                        root.get("firstName"),
                                        " "
                                    ),
                                    root.get("lastName")
                                )
                            ),
                            "%$name%"
                        )

                        fullNamePredicate
                    }

                    else -> throw IllegalArgumentException("Filter type not supported for this property")
                }

            }

            USERNAME_FILTER -> {
                return when (filter.filterType) {
                    FilterTypes.LIKE -> {
                        val username = filter.value.toString().trim().lowercase()

                        val usernamePredicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get<User>("user").get("username")),
                            "%$username%"
                        )

                        usernamePredicate
                    }

                    else -> throw IllegalArgumentException("Filter type not supported for this property")
                }
            }

            else -> super.basicComparePredicate(filter, root, criteriaBuilder)
        }

    }
}