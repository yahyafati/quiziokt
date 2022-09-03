package com.yahya.quizbuilderkt.service

import com.yahya.quizbuilderkt.dao.ChoiceDao
import com.yahya.quizbuilderkt.exception.InvalidQuestionException
import com.yahya.quizbuilderkt.exception.QuizAlreadyPublished
import com.yahya.quizbuilderkt.exception.ResourceNotFoundException
import com.yahya.quizbuilderkt.model.Choice
import com.yahya.quizbuilderkt.model.Question
import com.yahya.quizbuilderkt.security.IAuthenticationFacade
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChoiceService(
    val choiceDao: ChoiceDao,
    val questionService: IQuestionService,
    val authenticationFacade: IAuthenticationFacade
) : IChoiceService {

    override fun findChoices(): List<Choice> {
        return choiceDao.findAll()
    }

    override fun findChoiceById(id: Int): Choice {
        val choiceOptional = choiceDao.findById(id)
        return choiceOptional.orElseThrow { ResourceNotFoundException.createWith("choice", id) }
    }

    override fun findChoiceByQuestionAndId(questionId: Int, choiceId: Int): Choice {
        return choiceDao.findByQuestionAndId(Question(id = questionId), choiceId)
            ?: throw ResourceNotFoundException.createWith("choice")
    }

    override fun getChoicesByQuestionId(questionId: Int): List<Choice> {
        if (!questionService.exists(questionId)) {
            throw ResourceNotFoundException.createWith("question", questionId)
        }
        return choiceDao.findAllByQuestionId(questionId)
    }

    private fun validateChoices(choices: List<Choice>, question: Question) {
        val numberOfAnswers = choices.count { it.answer }
        if (numberOfAnswers == 0) {
            // Check if it has any answer
            throw InvalidQuestionException.noAnswerProvided()
        } else if (numberOfAnswers > 1 && !question.multi) {
            // Check the number of answers
            throw InvalidQuestionException.multipleAnswerProvided()
        }
    }

    override fun save(choice: Choice): Choice {
        val question: Question = choice.question ?: throw IllegalArgumentException("no question provided")
        val questionFromDB = questionService.findQuestionById(question.id)
        val quiz = question.quiz ?: throw IllegalArgumentException("quiz is null")
        if (quiz.published) {
            throw QuizAlreadyPublished.createWith((quiz.id))
        }
        val choices = mutableListOf<Choice>()

        choices.addAll(choiceDao.findAllByQuestionId(questionId = questionFromDB.id))
        choices.removeIf { it.id == choice.id }
        choices.add(choice)

        validateChoices(choices, questionFromDB)
        return choiceDao.save(choice)

    }

    @Transactional
    override fun saveAll(choices: List<Choice>, replace: Boolean): List<Choice> {
        if (choices.isEmpty()) {
            throw InvalidQuestionException.noChoiceProvided()
        }
        val questionId = choices[0].question?.id ?: throw IllegalArgumentException("no question provided")
        val question = questionService.findQuestionById(questionId)
        val quiz = question.quiz ?: throw IllegalArgumentException("quiz is null")
        if (quiz.published) {
            throw QuizAlreadyPublished.createWith(quiz.id)
        }
        if (!authenticationFacade.equalsAuth(question)) {
            throw AccessDeniedException("can't access this question")
        }
        if (replace) {
            choiceDao.deleteAllByQuestionId(questionId)
        }
        val allChoices: List<Choice> =
            if (replace) choices else {
                val list = mutableListOf<Choice>()
                list.addAll(choiceDao.findAllByQuestionId(questionId))
                list.addAll(choices)
                return list
            }
        val anyConflict = choices.any { it.question?.id != questionId }
        if (anyConflict) {
            throw IllegalArgumentException("all choices must be for the same question")
        }
        validateChoices(allChoices, question)

        return choiceDao.saveAll(choices)
    }

    @kotlin.jvm.Throws(AccessDeniedException::class)
    override fun update(choice: Choice): Choice {
        val exists = checkChoiceValidAuth(choice.id)
        choice.question = exists.question
        return save(choice)
    }

    @kotlin.jvm.Throws(AccessDeniedException::class)
    private fun checkChoiceValidAuth(id: Int): Choice {
        val exists =
            choiceDao.findById(id).orElseThrow { ResourceNotFoundException.createWith("choice", id) }
        if (authenticationFacade.equalsAuth(exists.question)) {
            throw AccessDeniedException("can't access this quiz")
        }
        return exists
    }

    @kotlin.jvm.Throws(AccessDeniedException::class)
    override fun delete(id: Int) {
        try {
            checkChoiceValidAuth(id)
            choiceDao.deleteById(id)
        } catch (e: EmptyResultDataAccessException) {
            throw ResourceNotFoundException.createWith("choice", id)
        }
    }

    override fun deleteByQuestionAndId(questionId: Int, choiceId: Int) {
        try {
            choiceDao.deleteByQuestionAndId(Question(id = questionId), choiceId)
        } catch (e: EmptyResultDataAccessException) {
            throw ResourceNotFoundException("no choice exists with the given question id ($questionId) and choice id ($choiceId)")
        }
    }

    override fun getChoicesByQuestionIdAndUsername(questionId: Int, currentUsername: String): List<Choice> {
        return choiceDao.findAllByQuestionIdAndQuestionCreatedByUsername(questionId, currentUsername)
    }

}