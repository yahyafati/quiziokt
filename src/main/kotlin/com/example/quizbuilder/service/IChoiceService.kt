package com.example.quizbuilder.service

import com.example.quizbuilder.model.Choice

interface IChoiceService {

    fun findChoices(): List<Choice>

    fun findChoiceById(id: Int): Choice

    fun findChoiceByQuestionAndId(questionId: Int, choiceId: Int): Choice

    fun getChoicesByQuestionId(questionId: Int): List<Choice>

    fun save(choice: Choice): Choice

    fun update(choice: Choice): Choice

    fun delete(id: Int)
    fun deleteByQuestionAndId(questionId: Int, choiceId: Int)

}