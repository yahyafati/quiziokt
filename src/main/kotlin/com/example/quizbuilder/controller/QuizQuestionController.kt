package com.example.quizbuilder.controller

import com.example.quizbuilder.exception.ResourceNotFoundException
import com.example.quizbuilder.model.Question
import com.example.quizbuilder.model.Quiz
import com.example.quizbuilder.service.IQuizQuestionService
import com.example.quizbuilder.service.QuizQuestionService
import com.example.quizbuilder.utils.ErrorResponse
import com.example.quizbuilder.utils.Util
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/quiz/{quizId}")
class QuizQuestionController(val quizQuestionService: IQuizQuestionService) {

    @PostMapping("/questions")
    fun postQuestion(@PathVariable quizId:Int, question: Question): ResponseEntity<Any> {
        question.id = 0
        question.quiz = Quiz(id = quizId)
        val saveQuestion = quizQuestionService.saveQuestion(quizId, question)
        return ResponseEntity.ok(saveQuestion)
    }

    @GetMapping("/questions")
    fun getQuestions(@PathVariable quizId:Int): ResponseEntity<Any> {
        val questions = quizQuestionService.getQuestions(quizId)
        val value = Util.applyFilterIn(questions, "QuestionFilter", "quiz")
        return ResponseEntity.ok(value)
    }

}