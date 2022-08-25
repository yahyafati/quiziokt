package com.example.quizbuilder.service

import com.example.quizbuilder.model.Question

interface IQuizQuestionService {

    fun getQuestions(quizId: Int): List<Question>

    fun getQuestion(quizId: Int, questionId: Int): Question

    fun saveQuestion(quizId: Int, question: Question): Question

    fun updateQuestion(quizId: Int, question: Question): Question

    fun deleteQuestion(quizId: Int, questionId: Int)

}