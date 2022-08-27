package com.example.quizbuilder.dao

import com.example.quizbuilder.model.Choice
import com.example.quizbuilder.model.Question
import org.springframework.data.jpa.repository.JpaRepository

interface ChoiceDao : JpaRepository<Choice, Int> {

    fun findByQuestionAndId(question: Question, id: Int): Choice?

    fun findAllByQuestionId(questionId: Int): List<Choice>

    fun deleteByQuestionAndId(question: Question, id: Int)
}