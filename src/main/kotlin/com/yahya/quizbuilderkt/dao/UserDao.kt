package com.yahya.quizbuilderkt.dao

import com.yahya.quizbuilderkt.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserDao : JpaRepository<User, Int> {

    fun findUserByUsername(username: String): User?
    fun existsByUsername(username: String): Boolean

    @Query("select u.id from User u where u.username = :username")
    fun findUserIdByUsername(username: String): Int?

}