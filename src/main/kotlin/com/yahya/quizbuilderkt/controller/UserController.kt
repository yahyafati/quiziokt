package com.yahya.quizbuilderkt.controller

import com.yahya.quizbuilderkt.model.User
import com.yahya.quizbuilderkt.service.IUserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/user")
class UserController(private val userService: IUserService) {

    @PostMapping("/register")
    fun signup(@Valid @RequestBody user: User): ResponseEntity<Any> {
        userService.signup(user)
        return ResponseEntity.ok().build()
    }
}