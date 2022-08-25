package com.example.quizbuilder.controller

import com.example.quizbuilder.exception.ResourceNotFoundException
import com.example.quizbuilder.model.Quiz
import com.example.quizbuilder.service.IQuizService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.http.HttpResponse
import java.util.StringJoiner

@RestController
@RequestMapping("/api/v1/quiz")
class QuizController (val quizService: IQuizService) {

    @GetMapping("")
    fun getAll(): List<Quiz> {
        return quizService.findQuizzes();
    }

    @GetMapping("/{id}")
    fun getQuiz(@PathVariable id:Int): ResponseEntity<Any> {
        val quiz : Quiz = quizService.findQuizById(id) ?: throw ResourceNotFoundException("no quiz with provided id ($id)")
        return ResponseEntity.ok(quiz);
    }

    @PostMapping("")
    fun post(@RequestBody quiz: Quiz): Quiz {
        quiz.id = 0
        return quizService.save(quiz);
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id:Int, @RequestBody quiz: Quiz): ResponseEntity<Any> {
        quiz.id = id
        val updatedQuiz = quizService.update(quiz) ?: return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updatedQuiz)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id:Int) : ResponseEntity<Any>{
        quizService.delete(id)
        return ResponseEntity.ok().build()
    }
}