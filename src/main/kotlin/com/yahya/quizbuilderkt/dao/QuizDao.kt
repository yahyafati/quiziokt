package com.yahya.quizbuilderkt.dao

import com.yahya.quizbuilderkt.model.Quiz
import org.springframework.data.jpa.repository.JpaRepository

interface QuizDao : JpaRepository<Quiz, Int> {

    fun findAllByCreatedByUsername(username: String): List<Quiz>

    fun existsByIdAndCreatedByUsername(id: Int, username: String): Boolean

}