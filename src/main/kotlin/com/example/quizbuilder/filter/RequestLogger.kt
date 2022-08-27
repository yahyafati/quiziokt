package com.example.quizbuilder.filter

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class RequestLogger : Filter {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(RequestLogger::class.java)
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val req: HttpServletRequest = request as HttpServletRequest
        val res: HttpServletResponse = response as HttpServletResponse
        LOG.info("Logging Request  {} : {}", req.method, req.requestURI)
        chain!!.doFilter(request, response)
        LOG.info("Logging Response :{}", res.contentType)
    }

}