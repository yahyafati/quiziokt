package com.yahya.quizbuilderkt.service

import com.yahya.quizbuilderkt.dao.QuizDao
import com.yahya.quizbuilderkt.exception.QuizAlreadyPublishedException
import com.yahya.quizbuilderkt.exception.QuizPublishingException
import com.yahya.quizbuilderkt.exception.ResourceNotFoundException
import com.yahya.quizbuilderkt.model.Quiz
import com.yahya.quizbuilderkt.utils.InvalidQuestionExceptionType
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

    override fun findQuizByPermalink(permalink: String): Quiz {
        return quizDao.findQuizByPermalink(permalink)
            ?: throw ResourceNotFoundException("no quiz can be found with the given permalink")
    }

    override fun save(quiz: Quiz): Quiz {
        return quizDao.save(quiz)
    }

    override fun update(quiz: Quiz): Quiz {
        val existingQuiz = findQuizById(quiz.id)
        if (existingQuiz.published) {
            throw QuizAlreadyPublishedException.createWith(quiz.id)
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

    private fun validateQuiz(quiz: Quiz): HashMap<Int, String> {
        val map: HashMap<Int, String> = hashMapOf()
        quiz.questions.forEach {
            val validation = ChoiceService.validateChoices(it.choices, emptyList(), it)
            if (validation != InvalidQuestionExceptionType.VALID_QUESTION) {
                map[it.id] = validation.message
            }
        }
        return map
//        return quiz
//            .questions
//            .map { ChoiceService.validateChoices(it.choices, emptyList(), it) }
//            .filter { it != InvalidQuestionExceptionType.VALID_QUESTION }
    }

    override fun publishQuiz(id: Int): Quiz {
        val quiz = findQuizById(id)
        if (quiz.published) {
            throw QuizAlreadyPublishedException.createWith(id)
        }
        quiz.published = true
        quiz.permalink = permalinkGenerator.generatePermalink()
        val validateQuiz = validateQuiz(quiz)
        if (validateQuiz.isNotEmpty()) {
            throw QuizPublishingException("quiz has invalid questions", validateQuiz)
        }
        return save(quiz)
    }
}