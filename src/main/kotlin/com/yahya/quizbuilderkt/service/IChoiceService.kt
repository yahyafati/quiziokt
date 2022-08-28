package com.yahya.quizbuilderkt.service

import com.yahya.quizbuilderkt.model.Choice
import org.springframework.security.access.AccessDeniedException

interface IChoiceService {

    fun findChoices(): List<Choice>

    fun findChoiceById(id: Int): Choice

    fun findChoiceByQuestionAndId(questionId: Int, choiceId: Int): Choice

    fun getChoicesByQuestionId(questionId: Int): List<Choice>

    fun save(choice: Choice): Choice

    @kotlin.jvm.Throws(AccessDeniedException::class)
    fun saveAll(choices: List<Choice>, replace: Boolean): List<Choice>

    @kotlin.jvm.Throws(AccessDeniedException::class)
    fun update(choice: Choice): Choice

    @kotlin.jvm.Throws(AccessDeniedException::class)
    fun delete(id: Int)
    fun deleteByQuestionAndId(questionId: Int, choiceId: Int)
    fun getChoicesByQuestionIdAndUsername(questionId: Int, currentUsername: String): List<Choice>

}