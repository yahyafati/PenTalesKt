package org.pentales.pentalesrest.config

import jakarta.servlet.http.*
import org.springframework.stereotype.*
import org.springframework.web.servlet.*

@Component
class CustomHandlerInterceptor : HandlerInterceptor {


    @Throws(Exception::class)
    override fun postHandle(
        request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?
    ) {
        super.postHandle(request, response, handler, modelAndView)
    }
}
