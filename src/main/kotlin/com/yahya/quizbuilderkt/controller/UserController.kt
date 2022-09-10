package com.yahya.quizbuilderkt.controller

import com.yahya.quizbuilderkt.model.User
import com.yahya.quizbuilderkt.security.IAuthenticationFacade
import com.yahya.quizbuilderkt.service.IUserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/user")
class UserController(private val userService: IUserService, private val authenticationFacade: IAuthenticationFacade) {

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(UserController::class.java)
    }

    @PostMapping("/register")
    fun signup(@Valid @RequestBody user: User): ResponseEntity<Any> {
        userService.signup(user)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/current")
    fun currentUser(): ResponseEntity<Any> {
        val currentUser = authenticationFacade.currentUser!!
//        val currentUserMap = hashMapOf<String, Any?>()
//        currentUserMap["username"] = currentUser.username
//        currentUserMap["email"] = currentUser.email
//        currentUserMap["profile"] = currentUser.profile
        return ResponseEntity.ok(currentUser.profile)
    }
}