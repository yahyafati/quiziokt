package com.yahya.quizbuilderkt.service

import com.yahya.quizbuilderkt.dao.ChoiceDao
import com.yahya.quizbuilderkt.exception.ResourceNotFoundException
import com.yahya.quizbuilderkt.model.Choice
import com.yahya.quizbuilderkt.model.Question
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
class ChoiceService(val choiceDao: ChoiceDao, val questionService: IQuestionService) : IChoiceService {

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
        return choiceDao.findAllByQuestionId(questionId)
    }

    override fun save(choice: Choice): Choice {
        val question: Question = choice.question ?: throw ResourceNotFoundException("no question provided")
        choice.question = questionService.findQuestionById(question.id)
        return choiceDao.save(choice)

    }

    override fun update(choice: Choice): Choice {
        val exists =
            choiceDao.findById(choice.id).orElseThrow { ResourceNotFoundException.createWith("choice", choice.id) }
        choice.question = Question(id = exists.id)
        return save(choice)
    }

    override fun delete(id: Int) {
        try {
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

}