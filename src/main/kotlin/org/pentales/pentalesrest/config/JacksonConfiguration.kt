package org.pentales.pentalesrest.config

import com.fasterxml.jackson.core.*
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.module.*
import com.fasterxml.jackson.datatype.jsr310.*
import com.fasterxml.jackson.module.kotlin.*
import de.codecentric.boot.admin.server.domain.values.*
import de.codecentric.boot.admin.server.utils.jackson.*
import org.slf4j.*
import org.springframework.context.annotation.*

@Configuration
class JacksonConfiguration {

    companion object {

        private val LOG = LoggerFactory.getLogger(JacksonConfiguration::class.java)
    }

    @Bean
    @Primary
    fun jacksonObjectMapper(): ObjectMapper {
        LOG.info("Configuring Jackson ObjectMapper")
        val objectMapper = ObjectMapper().registerKotlinModule()
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)

        val simpleModule = SimpleModule(
            "SimpleModule", Version.unknownVersion()
        )
        simpleModule.addDeserializer(
            Registration::class.java, RegistrationDeserializer()
        )
        objectMapper.registerModules(
            simpleModule,
            JavaTimeModule(),
            AdminServerJacksonModule(),
            com.fasterxml.jackson.module.paranamer.ParanamerModule()
        )
//        objectMapper.setInjectableValues(IgnoreNullFieldInjectableValues())
        LOG.info("Configured Jackson ObjectMapper")
        return objectMapper
    }
}