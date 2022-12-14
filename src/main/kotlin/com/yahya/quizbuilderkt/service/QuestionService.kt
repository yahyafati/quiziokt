package com.yahya.quizbuilderkt.service

import com.yahya.quizbuilderkt.dao.QuestionDao
import com.yahya.quizbuilderkt.exception.InvalidQuestionException
import com.yahya.quizbuilderkt.exception.QuizAlreadyPublishedException
import com.yahya.quizbuilderkt.exception.ResourceNotFoundException
import com.yahya.quizbuilderkt.model.Question
import com.yahya.quizbuilderkt.model.Quiz
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
class QuestionService(val questionDao: QuestionDao, val quizService: IQuizService) : IQuestionService {
    override fun exists(id: Int): Boolean {
        return questionDao.existsById(id)
    }

    override fun findQuestions(): List<Question> {
        return questionDao.findAll()
    }

    override fun findQuestionById(id: Int): Question {
        val questionOptional = questionDao.findById(id)
        return questionOptional.orElseThrow { ResourceNotFoundException.createWith("question", id) }
    }

    override fun findQuestionByQuizAndId(quizId: Int, questionId: Int): Question {
        return questionDao.findByQuizAndId(Quiz(id = quizId), questionId)
            .orElseThrow { ResourceNotFoundException.createWith("question") }
    }

    override fun findQuestionsByQuizId(quizId: Int): List<Question> {
        val exists = quizService.exists(quizId)
        if (!exists) {
            throw ResourceNotFoundException.createWith("quiz", quizId)
        }
        return questionDao.findAllByQuizId(quizId)
    }

    override fun save(question: Question): Question {
        val quiz: Quiz = question.quiz ?: throw ResourceNotFoundException("no quiz provided")
        val quizFromDB = quizService.findQuizById(quiz.id)
        question.quiz = quizFromDB
        if (quizFromDB.published) {
            throw QuizAlreadyPublishedException.createWith(quiz.id)
        }
        return questionDao.save(question)
    }

    override fun update(question: Question): Question {
        val oldQuestion = findQuestionById(question.id)
        val quiz = oldQuestion.quiz ?: throw IllegalArgumentException("no quiz provided")
        val multiStatusChanged = oldQuestion.multi != question.multi
        if (multiStatusChanged && !question.multi) {
            val count = questionDao.countAllAnswers(question.id)
            if (count > 1) {
                throw InvalidQuestionException.questionHadMultipleAnswer(question.choices)
            }
        }
        question.quiz = Quiz(id = quiz.id)
        return save(question)
    }

    override fun delete(id: Int) {
        try {
            questionDao.deleteById(id)
        } catch (e: EmptyResultDataAccessException) {
            throw ResourceNotFoundException.createWith("question", id)
        }
    }

    override fun deleteByQuizAndId(quizId: Int, questionId: Int) {
        try {
            questionDao.deleteByQuizAndId(Quiz(id = quizId), questionId)
        } catch (e: EmptyResultDataAccessException) {
            throw ResourceNotFoundException("no question exists with the given quiz id ($quizId) and question id ($questionId)")
        }
    }

    override fun findQuestionsByQuizIdAndUsername(quiz: Int, username: String): List<Question> {
        return questionDao.findAllByQuizIdAndQuizCreatedByUsername(quiz, username)
    }

}