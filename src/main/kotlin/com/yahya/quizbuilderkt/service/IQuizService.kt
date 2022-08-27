package com.yahya.quizbuilderkt.service

import com.yahya.quizbuilderkt.model.Quiz

interface IQuizService {

    fun exists(id: Int): Boolean
    fun findQuizzes(): List<Quiz>

    fun findQuizById(id: Int): Quiz

    fun save(quiz: Quiz): Quiz

    fun update(quiz: Quiz): Quiz

    fun delete(id: Int)

}