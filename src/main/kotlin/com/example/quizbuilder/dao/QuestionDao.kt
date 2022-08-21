package com.example.quizbuilder.dao

import com.example.quizbuilder.model.Question
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionDao : JpaRepository<Question, Int> {
    fun findAllByQuizId(id: Int): List<Question>
}