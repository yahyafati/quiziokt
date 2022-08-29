package com.yahya.quizbuilderkt.controller

import com.yahya.quizbuilderkt.model.Quiz
import com.yahya.quizbuilderkt.security.IAuthenticationFacade
import com.yahya.quizbuilderkt.service.IQuizService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/quiz")
class QuizController(val quizService: IQuizService, private val authenticationFacade: IAuthenticationFacade) {


    @GetMapping("")
    fun getAll(): ResponseEntity<Any> {
        val quizzes = quizService.findQuizzesBy(authenticationFacade.username)
        return ResponseEntity.ok(quizzes)
    }

    @GetMapping("/{id}")
    fun getQuiz(@PathVariable id: Int): ResponseEntity<Any> {
        val quiz: Quiz = quizService.findQuizById(id)
        if (!authenticationFacade.equalsAuth(quiz)) {
            throw org.springframework.security.access.AccessDeniedException("can't access this quiz")
        }
        return ResponseEntity.ok(quiz)
    }

    @PostMapping("")
    fun post(@RequestBody @Valid quiz: Quiz): ResponseEntity<Any> {
        quiz.id = 0
        val saved = quizService.save(quiz)
        return ResponseEntity.ok(saved)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody @Valid quiz: Quiz): ResponseEntity<Any> {
        quiz.id = id
        val existing = quizService.findQuizById(id)
        if (!authenticationFacade.equalsAuth(existing)) {
            throw org.springframework.security.access.AccessDeniedException("can't update this quiz")
        }
        val updatedQuiz = quizService.update(quiz)
        return ResponseEntity.ok(updatedQuiz)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Any> {
        val existing = quizService.findQuizById(id)
        if (!authenticationFacade.equalsAuth(existing)) {
            throw org.springframework.security.access.AccessDeniedException("can't delete this quiz")
        }
        quizService.delete(id)
        return ResponseEntity.ok().build()
    }
}