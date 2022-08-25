package com.example.quizbuilder.controller

import com.example.quizbuilder.model.Question
import com.example.quizbuilder.service.IQuestionService
import com.example.quizbuilder.utils.Util
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/question")
class QuestionController (val questionService: IQuestionService) : BasicController<Question> {

    @GetMapping("")
    override fun getAll(): ResponseEntity<Any> {
        val questions = questionService.findQuestions()
        val value = Util.applyFilterOut(questions, "QuestionFilter", "id", "text", "multi")
        return ResponseEntity.ok(value)
    }

    @GetMapping("/{id}")
    override fun getOne(@PathVariable id: Int): ResponseEntity<Any> {
        val question = questionService.findQuestionById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(question)
    }

    @PostMapping("")
    override fun post(@RequestBody item:Question) : ResponseEntity<Any> {
        item.id=0
        return ResponseEntity.ok(questionService.save(item))
    }

    @PutMapping("/{id}")
    override fun update(@PathVariable id: Int, @RequestBody item: Question): ResponseEntity<Any> {
        item.id = id
        val updated = questionService.update(item) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(updated)
    }

    @DeleteMapping("/{id}")
    override fun delete(@PathVariable id: Int): ResponseEntity<Any> {
        questionService.delete(id)
        return ResponseEntity.ok().build()
    }

}