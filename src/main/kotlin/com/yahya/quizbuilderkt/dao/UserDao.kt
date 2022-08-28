package com.yahya.quizbuilderkt.dao

import com.yahya.quizbuilderkt.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserDao : JpaRepository<User, Int> {

    fun findUserByUsername(username: String): User?
    
}