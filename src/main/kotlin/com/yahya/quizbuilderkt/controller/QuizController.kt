package com.yahya.quizbuilderkt.controller

import com.yahya.quizbuilderkt.model.Quiz
import com.yahya.quizbuilderkt.service.IQuizService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/quiz")
class QuizController(val quizService: IQuizService) {

    @GetMapping("")
    fun getAll(): List<Quiz> {
        return quizService.findQuizzes()
    }

    @GetMapping("/{id}")
    fun getQuiz(@PathVariable id: Int): ResponseEntity<Any> {
        val quiz: Quiz = quizService.findQuizById(id)
        return ResponseEntity.ok(quiz)
    }

    @PostMapping("")
    fun post(@RequestBody @Valid quiz: Quiz): Quiz {
        quiz.id = 0
        return quizService.save(quiz)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody @Valid quiz: Quiz): ResponseEntity<Any> {
        quiz.id = id
        val updatedQuiz = quizService.update(quiz)
        return ResponseEntity.ok(updatedQuiz)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Any> {
        quizService.delete(id)
        return ResponseEntity.ok().build()
    }
}