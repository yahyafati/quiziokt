package com.example.quizbuilder.service

import com.example.quizbuilder.dao.QuestionDao
import com.example.quizbuilder.model.Question
import com.example.quizbuilder.model.Quiz
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
class QuestionService(val questionDao: QuestionDao) : IQuestionService{
    override fun findQuestions(): List<Question> {
        return questionDao.findAll();
    }

    override fun findQuestionById(id: Int): Question? {
        val questionOptional = questionDao.findById(id)
        if (questionOptional.isEmpty) {
            return null
        }
        return questionOptional.get()
    }

    override fun findQuestionsByQuizId(quizId: Int): List<Question> {
        return questionDao.findAllByQuizId(quizId)
    }

    override fun save(question: Question): Question {
        return questionDao.save(question)
    }

    override fun update(question: Question): Question? {
        val exists = questionDao.existsById(question.id)
        if (!exists) {
            return null
        }
        return save(question)
    }

    override fun delete(id: Int): Boolean {
        try {
            questionDao.deleteById(id)
        } catch (e: EmptyResultDataAccessException) {
            return false
        }
        return true
    }

}