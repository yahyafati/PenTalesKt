package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.data.jpa.repository.*
import org.springframework.stereotype.*
import kotlin.reflect.*
import kotlin.reflect.full.*

@Service
class PublisherServices(private val publisherRepository: PublisherRepository) : IPublisherServices {

    override val repository: JpaRepository<Publisher, Long>
        get() = publisherRepository
    override val modelProperties: Collection<KProperty1<Publisher, *>>
        get() = Publisher::class.memberProperties
}
