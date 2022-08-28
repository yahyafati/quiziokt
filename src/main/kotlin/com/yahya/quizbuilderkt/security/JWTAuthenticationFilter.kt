package com.yahya.quizbuilderkt.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import com.yahya.quizbuilderkt.model.User
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter(authenticationManager: AuthenticationManager) : UsernamePasswordAuthenticationFilter() {

    companion object {
        private val LOG = LoggerFactory.getLogger(JWTAuthenticationFilter::class.java)
    }

    init {
        this.authenticationManager = authenticationManager
        setFilterProcessesUrl(SecurityConstants.LOGIN_URL)
    }

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        return try {
            val credential: User = ObjectMapper().readValue(request.inputStream, User::class.java)
            val authentication: Authentication = UsernamePasswordAuthenticationToken(
                credential.getUsername(),
                credential.getPassword(),
                listOf<GrantedAuthority>()
            )
            authenticationManager.authenticate(authentication)
        } catch (e: IOException) {
            e.printStackTrace()
            throw RuntimeException(e)
        }
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        LOG.info("Successful Authentication")
        val principal: User = authResult.principal as User
        val token: String = JWT.create()
            .withSubject(principal.username)
            .withIssuedAt(Date())
            .withExpiresAt(Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
            .sign(Algorithm.HMAC512(SecurityConstants.SECRET.toByteArray()))
        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token)
    }

    @Throws(IOException::class, ServletException::class)
    override fun unsuccessfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        failed: AuthenticationException
    ) {
        LOG.error("UnSuccessful Authentication")
        response.writer.println("{\"error:\": \"Invalid Credentials\"}")
        super.unsuccessfulAuthentication(request, response, failed)
    }
}