package com.yahya.quizbuilderkt.security

import com.yahya.quizbuilderkt.model.User
import com.yahya.quizbuilderkt.service.IUserService
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.util.*

@Component
class SecurityAuditorAware(private val authenticationFacade: IAuthenticationFacade, userService: IUserService) :
    AuditorAware<User> {
    private val userService: IUserService

    init {
        this.userService = userService
    }

    override fun getCurrentAuditor(): Optional<User> {
//        val name = authenticationFacade.authentication?.name ?: return Optional.empty()
//        return Optional.of<String>(name)
        val authentication = authenticationFacade.authentication ?: return Optional.empty()
        val isAnonymous = authentication.authorities.isNotEmpty() && authentication.authorities.stream()
            .allMatch { grantedAuthority: GrantedAuthority -> grantedAuthority.authority == "ROLE_ANONYMOUS" }
        return if (!authentication.isAuthenticated || isAnonymous) {
            Optional.empty()
        } else {
            val user = userService.findByUsername(authentication.name)
            Optional.of(user)
        }
    }
}