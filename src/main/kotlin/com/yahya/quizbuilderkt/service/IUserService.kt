package com.yahya.quizbuilderkt.service

import com.yahya.quizbuilderkt.model.User
import org.springframework.security.core.userdetails.UserDetailsService

interface IUserService : UserDetailsService {

    fun findByUsername(username: String): User

}