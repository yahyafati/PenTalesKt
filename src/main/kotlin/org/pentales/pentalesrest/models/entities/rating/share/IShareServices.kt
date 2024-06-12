package org.pentales.pentalesrest.models.entities.rating.share

import org.pentales.pentalesrest.models.entities.rating.*

interface IShareServices {

    fun getShareCountOf(
        rating: Rating
    ): Int

    fun getShareById(
        id: Long
    ): Share

    fun save(
        share: Share
    ): Share

    fun saveNew(
        share: Share
    ): Share

    fun deleteById(
        id: Long
    )

}