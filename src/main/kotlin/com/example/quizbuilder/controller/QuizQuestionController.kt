package com.example.quizbuilder.controller

import com.example.quizbuilder.model.Question
import com.example.quizbuilder.service.IQuizQuestionService
import com.example.quizbuilder.utils.Util
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/quiz/{quizId}")
class QuizQuestionController(val quizQuestionService: IQuizQuestionService) {

    @PostMapping("/questions")
    fun postQuestion(@PathVariable quizId: Int, question: Question): ResponseEntity<Any> {
        question.id = 0
        val saveQuestion = quizQuestionService.saveQuestion(quizId, question)
        return ResponseEntity.ok(saveQuestion)
    }

    @PutMapping("/questions/{questionId}")
    fun updateQuestion(
        @PathVariable quizId: Int,
        @PathVariable questionId: Int,
        question: Question
    ): ResponseEntity<Any> {
        question.id = questionId
        val saveQuestion = quizQuestionService.updateQuestion(quizId, question)
        return ResponseEntity.ok(saveQuestion)
    }

    @GetMapping("/questions")
    fun getQuestions(@PathVariable quizId: Int): ResponseEntity<Any> {
        val questions = quizQuestionService.getQuestions(quizId)
        val value = Util.applyFilterIn(questions, "QuestionFilter", "quiz")
        return ResponseEntity.ok(value)
    }

}