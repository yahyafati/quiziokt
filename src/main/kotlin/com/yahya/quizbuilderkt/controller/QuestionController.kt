package com.yahya.quizbuilderkt.controller

import com.yahya.quizbuilderkt.model.Question
import com.yahya.quizbuilderkt.model.Quiz
import com.yahya.quizbuilderkt.service.IQuestionService
import com.yahya.quizbuilderkt.utils.Util
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/question")
class QuestionController(val questionService: IQuestionService) {

    @GetMapping("")
    fun getAll(@RequestParam quiz: Int): ResponseEntity<Any> {
        val questions = questionService.findQuestionsByQuizId(quiz)
        val value = Util.applyFilterOut(questions, "QuestionFilter", "id", "text", "multi")
        return ResponseEntity.ok(value)
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Int): ResponseEntity<Any> {
        val question = questionService.findQuestionById(id)
        return ResponseEntity.ok(question)
    }

    @PostMapping("")
    fun post(@RequestBody @Valid item: Question, @RequestParam(name = "quiz") quizId: Int): ResponseEntity<Any> {
        item.quiz = Quiz(id = quizId)
        val saved = questionService.save(item)
        return ResponseEntity.ok(saved)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody @Valid item: Question): ResponseEntity<Any> {
        item.id = id
        val updated = questionService.update(item)
        return ResponseEntity.ok(updated)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Any> {
        questionService.delete(id)
        return ResponseEntity.ok().build()
    }

}