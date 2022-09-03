package com.yahya.quizbuilderkt.service

import com.yahya.quizbuilderkt.model.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

interface IUserService : UserDetailsService {

    @kotlin.jvm.Throws(UsernameNotFoundException::class)
    fun findByUsername(username: String): User

    fun findUserIdByUsername(username: String): Int

    fun usernameExists(username: String): Boolean
    fun save(user: User): User
    fun signup(user: User): User

}