package org.pentales.pentalesrest.config

import org.springframework.context.annotation.*
import org.springframework.web.servlet.config.annotation.*

@Configuration
class WebConfig : WebMvcConfigurer {


    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(CustomHandlerInterceptor())
    }
}
