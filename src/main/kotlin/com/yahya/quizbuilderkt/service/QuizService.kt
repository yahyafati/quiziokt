package com.yahya.quizbuilderkt.service

import com.yahya.quizbuilderkt.dao.QuizDao
import com.yahya.quizbuilderkt.exception.ResourceNotFoundException
import com.yahya.quizbuilderkt.model.Quiz
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
class QuizService(val quizDao: QuizDao) : IQuizService {
    override fun exists(id: Int): Boolean {
        return quizDao.existsById(id)
    }

    override fun findQuizzes(): List<Quiz> {
        return quizDao.findAll()
    }

    override fun findQuizById(id: Int): Quiz {
        val quizOptional = quizDao.findById(id)
        return quizOptional.orElseThrow { ResourceNotFoundException.createWith("quiz", id) }
    }

    override fun save(quiz: Quiz): Quiz {
        return quizDao.save(quiz)
    }

    override fun update(quiz: Quiz): Quiz {
        val exists = exists(quiz.id)
        if (!exists) {
            throw ResourceNotFoundException.createWith("quiz", quiz.id)
        }
        return save(quiz)
    }

    override fun delete(id: Int) {
        try {
            quizDao.deleteById(id)
        } catch (e: EmptyResultDataAccessException) {
            throw ResourceNotFoundException("no question exists with the given quiz id: $id")
        }
    }
}