package com.yahya.quizbuilderkt.dao

import com.yahya.quizbuilderkt.model.Choice
import com.yahya.quizbuilderkt.model.Question
import org.springframework.data.jpa.repository.JpaRepository

interface ChoiceDao : JpaRepository<Choice, Int> {

    fun findByQuestionAndId(question: Question, id: Int): Choice?
    fun findAllByQuestionIdAndQuestionCreatedByUsername(id: Int, username: String): List<Choice>

    fun findAllByQuestionId(questionId: Int): List<Choice>

    fun deleteByQuestionAndId(question: Question, id: Int)

    fun deleteAllByQuestionId(questionId: Int)
}