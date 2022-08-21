package com.example.quizbuilder.service

import com.example.quizbuilder.dao.QuizDao
import com.example.quizbuilder.model.Quiz
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
class QuizService(val quizDao: QuizDao) : IQuizService {

    override fun findQuizzes(): List<Quiz> {
        return quizDao.findAll()
    }

    override fun findQuizById(id: Int): Quiz? {
        val quizOptional = quizDao.findById(id)
        if (quizOptional.isEmpty) {
            return null
        }
        return quizOptional.get()
    }

    override fun save(quiz: Quiz): Quiz {
        return quizDao.save(quiz)
    }

    override fun update(quiz: Quiz): Quiz? {
        val exists = quizDao.existsById(quiz.id)
        if (!exists) {
            return null
        }
        return save(quiz)
    }

    override fun delete(id: Int): Boolean {
        try {
            quizDao.deleteById(id)
        } catch (e: EmptyResultDataAccessException) {
            return false
        }
        return true
    }
}