package com.yahya.quizbuilderkt.service

import com.yahya.quizbuilderkt.model.Question


interface IQuestionService {

    fun exists(id: Int): Boolean
    fun findQuestions(): List<Question>

    fun findQuestionById(id: Int): Question

    fun findQuestionByQuizAndId(quizId: Int, questionId: Int): Question

    fun findQuestionsByQuizId(quizId: Int): List<Question>

    fun save(question: Question): Question

    fun update(question: Question): Question

    fun delete(id: Int)
    fun deleteByQuizAndId(quizId: Int, questionId: Int)
    fun findQuestionsByQuizIdAndUsername(quiz: Int, username: String): List<Question>
}