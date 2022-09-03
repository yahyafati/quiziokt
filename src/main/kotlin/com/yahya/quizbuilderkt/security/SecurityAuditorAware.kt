package com.yahya.quizbuilderkt.security

import com.yahya.quizbuilderkt.model.User
import org.springframework.data.domain.AuditorAware
import org.springframework.stereotype.Component
import java.util.*

@Component
class SecurityAuditorAware(private val authenticationFacade: IAuthenticationFacade) :
    AuditorAware<User> {

    override fun getCurrentAuditor(): Optional<User> {
        val currentUser = authenticationFacade.currentUser ?: return Optional.empty()
        return Optional.of(currentUser)
    }
}