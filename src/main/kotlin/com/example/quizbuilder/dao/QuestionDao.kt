package com.example.quizbuilder.dao

import com.example.quizbuilder.model.Question
import com.example.quizbuilder.model.Quiz
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionDao : JpaRepository<Question, Int> {
    fun findAllByQuizId(id: Int): List<Question>

    fun findByQuizAndId(quiz: Quiz, id: Int) : Question?
    fun deleteByQuizAndId(quiz: Quiz, questionId: Int)
}