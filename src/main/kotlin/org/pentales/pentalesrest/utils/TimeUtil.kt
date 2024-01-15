package org.pentales.pentalesrest.utils

import java.time.*

class TimeUtil {

    companion object {

        fun getCurrentYearUTC(): Int {
            return Year.now(ZoneId.of("UTC")).value
        }
    }
}