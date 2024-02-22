package org.pentales.pentalesrest.utils

import java.time.*

object TimeUtil {

    fun getCurrentYearUTC(): Int {
        return Year.now(ZoneId.of("UTC")).value
    }
}
