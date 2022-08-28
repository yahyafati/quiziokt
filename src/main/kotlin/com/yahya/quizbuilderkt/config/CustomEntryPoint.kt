package com.yahya.quizbuilderkt.config

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomEntryPoint(
    private val status: HttpStatus,
    private val body: Any
) : AuthenticationEntryPoint {

    companion object {
        private val LOG = LoggerFactory.getLogger(CustomEntryPoint::class.java)
    }

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        LOG.error("{}", authException?.message)
        response?.status = status.value()
//        response!!.writer.use { writer -> writer.print(ObjectMapper().writeValueAsString(authException?.message)) }
    }
}