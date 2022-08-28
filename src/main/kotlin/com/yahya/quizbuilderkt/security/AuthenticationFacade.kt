package com.yahya.quizbuilderkt.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthenticationFacade : IAuthenticationFacade {

    override val authentication: Authentication?
        get() = SecurityContextHolder.getContext().authentication
}