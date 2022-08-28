package com.yahya.quizbuilderkt.security

import com.yahya.quizbuilderkt.model.User
import com.yahya.quizbuilderkt.service.IUserService
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.GrantedAuthority
import java.util.*

//@Component
class SecurityAuditorAware(private val authenticationFacade: IAuthenticationFacade, userService: IUserService) :
    AuditorAware<User> {
    private val userService: IUserService

    init {
        this.userService = userService
    }

//    val currentAuditor: Optional<Any>
//        get() {
//            val authentication = authenticationFacade.authentication
//            return if (authentication == null ||
//                !authentication.isAuthenticated ||
//                authentication.authorities.stream()
//                    .allMatch { grantedAuthority: GrantedAuthority? -> grantedAuthority.getAuthority() == "ROLE_ANONYMOUS" }
//            ) {
//                Optional.empty()
//            } else Optional.of(userService.findByUsername(authentication.name))
//        }

    override fun getCurrentAuditor(): Optional<User> {
        val authentication = authenticationFacade.authentication
        return if (authentication == null ||
            !authentication.isAuthenticated ||
            authentication.authorities.stream()
                .allMatch { grantedAuthority: GrantedAuthority -> grantedAuthority.authority == "ROLE_ANONYMOUS" }
        ) {
            Optional.empty()
        } else Optional.of(userService.findByUsername(authentication.name))
    }
}