package org.pentales.pentalesrest.utils

import org.springframework.data.domain.*

class PageableUtil {

    companion object {

        fun getPageable(page: Int, size: Int, sort: Sort): Pageable {
            return PageRequest.of(page, size, sort)
        }
    }

}