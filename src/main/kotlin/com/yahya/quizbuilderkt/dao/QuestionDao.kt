package com.yahya.quizbuilderkt.dao

import com.yahya.quizbuilderkt.model.Question
import com.yahya.quizbuilderkt.model.Quiz
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface QuestionDao : JpaRepository<Question, Int> {
    fun findAllByQuizId(id: Int): List<Question>
    fun findByQuizAndId(quiz: Quiz, id: Int): Optional<Question>
    fun deleteByQuizAndId(quiz: Quiz, questionId: Int)
}