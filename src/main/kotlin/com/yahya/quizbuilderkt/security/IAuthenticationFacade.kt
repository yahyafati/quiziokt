package com.yahya.quizbuilderkt.security

import org.springframework.security.core.Authentication

interface IAuthenticationFacade {
    val authentication: Authentication?
}