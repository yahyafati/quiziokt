package com.example.quizbuilder.service

import com.example.quizbuilder.dao.QuizDao
import com.example.quizbuilder.exception.ResourceNotFoundException
import com.example.quizbuilder.model.Quiz
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
class QuizService(val quizDao: QuizDao) : IQuizService {
    override fun exists(id: Int): Boolean {
        return quizDao.existsById(id);
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