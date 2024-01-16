package org.pentales.pentalesrest.config

import com.fasterxml.jackson.core.*
import com.fasterxml.jackson.databind.*
import de.codecentric.boot.admin.server.domain.values.*

class AdminServerJacksonModule : Module() {

    override fun version(): Version {
        return Version.unknownVersion()
    }

    override fun getModuleName(): String {
        return "AdminServerJacksonModule"
    }

    override fun setupModule(p0: SetupContext?) {
        p0?.setMixInAnnotations(
//            Any::class.java, IgnoreNullFieldMixin::class.java
            Any::class.java, Registration::class.java
        )
    }
}
