package com.example.quizbuilder.service

import com.example.quizbuilder.dao.QuestionDao
import com.example.quizbuilder.exception.ResourceNotFoundException
import com.example.quizbuilder.model.Question
import com.example.quizbuilder.model.Quiz
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
class QuestionService(val questionDao: QuestionDao, val quizService: IQuizService) : IQuestionService{
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

    override fun findQuestionByQuizAndId(quizId: Int, questionId: Int): Question? {
        return questionDao.findByQuizAndId(Quiz(id = quizId), questionId)
    }

    override fun findQuestionsByQuizId(quizId: Int): List<Question> {
        return questionDao.findAllByQuizId(quizId)
    }

    override fun save(question: Question): Question {
        val quiz: Quiz = question.quiz ?: throw IllegalArgumentException("no quiz provided")
        if (quizService.findQuizById(quiz.id) == null)
            throw ResourceNotFoundException("no quiz exists with given id (${quiz.id})")
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

    override fun deleteByQuizAndId(quizId: Int, questionId: Int): Boolean {
        try {
            questionDao.deleteByQuizAndId(Quiz(id = quizId), questionId)
        } catch (e: EmptyResultDataAccessException) {
            return false
        }
        return true
    }

}