package com.yahya.quizbuilderkt.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.yahya.quizbuilderkt.model.User
import com.yahya.quizbuilderkt.service.IUserService
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthorizationFilter(authenticationManager: AuthenticationManager?, userService: IUserService) :
    BasicAuthenticationFilter(authenticationManager) {
    private val userService: IUserService

    companion object {
        private val LOG = LoggerFactory.getLogger(JWTAuthorizationFilter::class.java)
    }

    init {
        this.userService = userService
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val header: String? = request.getHeader(SecurityConstants.HEADER_STRING)
        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response)
            return
        }
        val authenticationToken: UsernamePasswordAuthenticationToken? = getAuthentication(request)
        SecurityContextHolder.getContext().authentication = authenticationToken
        chain.doFilter(request, response)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        var token: String = request.getHeader(SecurityConstants.HEADER_STRING)
            ?: return null
        try {
            token = token.replace(SecurityConstants.TOKEN_PREFIX, "")
            val username: String = JWT
                .require(Algorithm.HMAC512(SecurityConstants.SECRET.toByteArray()))
                .build()
                .verify(token)
                .subject ?: return null
            val loggedInUser: User = userService.findByUsername(username)
            return UsernamePasswordAuthenticationToken(
                loggedInUser,
                null,
                loggedInUser.authorities
            )
        } catch (ex: JWTVerificationException) {
            LOG.error(ex.message)
        } catch (ex: UsernameNotFoundException) {
            LOG.error(ex.message)
        }
        return null
    }
}