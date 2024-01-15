package org.pentales.pentalesrest.services.basic.impl

import org.pentales.pentalesrest.exceptions.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.repo.*
import org.pentales.pentalesrest.services.basic.*
import org.springframework.stereotype.*

@Service
class ShareServices(
    private val shareRepository: ShareRepository,
) : IShareServices {

    val entityName = "ActivityShare"

    override fun getShareCountOf(rating: Rating): Int {
        return shareRepository.countAllByRating(rating)
    }

    override fun save(share: Share): Share {
        return shareRepository.save(share)
    }

    override fun getShareById(id: Long): Share {
        return shareRepository.findById(id).orElseThrow { NoEntityWithIdException.create(entityName, id.toString()) }
    }

    override fun saveNew(share: Share): Share {
        return save(share)
    }

    override fun deleteById(id: Long) {
        shareRepository.deleteById(id)
    }
}