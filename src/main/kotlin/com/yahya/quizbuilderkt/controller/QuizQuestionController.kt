package com.yahya.quizbuilderkt.controller

import com.yahya.quizbuilderkt.model.Question
import com.yahya.quizbuilderkt.model.Quiz
import com.yahya.quizbuilderkt.service.IQuestionService
import com.yahya.quizbuilderkt.utils.Util
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

//@RestController
//@RequestMapping("/api/v1/quiz/{quizId}")
class QuizQuestionController(val questionService: IQuestionService) {

    @PostMapping("/questions")
    fun postQuestion(@PathVariable quizId: Int, @Valid @RequestBody question: Question): ResponseEntity<Any> {
        question.id = 0
        question.quiz = Quiz(id = quizId)
        val saveQuestion = questionService.save(question)
        return ResponseEntity.ok(saveQuestion)
    }

    @PutMapping("/questions/{questionId}")
    fun updateQuestion(
        @PathVariable quizId: Int,
        @PathVariable questionId: Int,
        @RequestBody @Valid question: Question
    ): ResponseEntity<Any> {
        question.id = questionId
        question.quiz = Quiz(id = quizId)
        val saveQuestion = questionService.update(question)
        return ResponseEntity.ok(saveQuestion)
    }

    @GetMapping("/questions")
    fun getQuestions(@PathVariable quizId: Int): ResponseEntity<Any> {
        val questions = questionService.getQuestionsByQuizId(quizId)
        val value = Util.applyFilterIn(questions, "QuestionFilter", "quiz")
        return ResponseEntity.ok(value)
    }

    @DeleteMapping("/questions/{questionId}")
    fun deleteQuestion(@PathVariable quizId: Int, @PathVariable questionId: Int): ResponseEntity<Any> {
        questionService.deleteByQuizAndId(quizId, questionId)
        return ResponseEntity.ok().build()
    }

}