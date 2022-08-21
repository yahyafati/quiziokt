package com.example.quizbuilder.service

import com.example.quizbuilder.model.Question
import com.example.quizbuilder.model.Quiz

interface IQuestionService {

    fun findQuestions() : List<Question>

    fun findQuestionById(id: Int): Question?

    fun findQuestionsByQuizId(quizId: Int): List<Question>

    fun save(question: Question) : Question

    fun update(question: Question): Question?

    fun delete(id: Int) : Boolean
}