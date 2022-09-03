package com.yahya.quizbuilderkt.service

import com.yahya.quizbuilderkt.dao.QuizDao
import com.yahya.quizbuilderkt.exception.QuizAlreadyPublished
import com.yahya.quizbuilderkt.exception.ResourceNotFoundException
import com.yahya.quizbuilderkt.model.Quiz
import com.yahya.quizbuilderkt.utils.PermalinkGenerator
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
class QuizService(val quizDao: QuizDao, val permalinkGenerator: PermalinkGenerator) : IQuizService {
    override fun exists(id: Int): Boolean {
        return quizDao.existsById(id)
    }

    override fun existsByIdAndUsername(id: Int, username: String): Boolean {
        return quizDao.existsByIdAndCreatedByUsername(id, username)
    }

    override fun findQuizzes(): List<Quiz> {
        return quizDao.findAll()
    }

    override fun findQuizzesBy(username: String): List<Quiz> {
        return quizDao.findAllByCreatedByUsername(username)
    }

    override fun findQuizById(id: Int): Quiz {
        val quizOptional = quizDao.findById(id)
        return quizOptional.orElseThrow { ResourceNotFoundException.createWith("quiz", id) }
    }

    override fun save(quiz: Quiz): Quiz {
        return quizDao.save(quiz)
    }

    override fun update(quiz: Quiz): Quiz {
        val existingQuiz = findQuizById(quiz.id)
        if (existingQuiz.published) {
            throw QuizAlreadyPublished.createWith(quiz.id)
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

    override fun publishQuiz(id: Int): Quiz {
        val quiz = findQuizById(id)
        if (quiz.published) {
            throw QuizAlreadyPublished.createWith(id)
        }
        quiz.published = true
        quiz.permalink = permalinkGenerator.generatePermalink()
        return save(quiz)
    }
}