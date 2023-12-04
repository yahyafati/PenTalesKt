package org.pentales.pentalesrest.services.basic

import org.springframework.data.domain.*

fun interface IPageRequestService<T> {

    fun run(pageRequest: PageRequest): T
}