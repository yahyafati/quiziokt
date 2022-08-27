package com.example.quizbuilder.controller

import com.example.quizbuilder.model.Question
import com.example.quizbuilder.service.IQuestionService
import com.example.quizbuilder.utils.Util
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/question")
class QuestionController(val questionService: IQuestionService) : BasicController<Question> {

    @GetMapping("")
    override fun getAll(): ResponseEntity<Any> {
        val questions = questionService.findQuestions()
        val value = Util.applyFilterOut(questions, "QuestionFilter", "id", "text", "multi")
        return ResponseEntity.ok(value)
    }

    @GetMapping("/{id}")
    override fun getOne(@PathVariable id: Int): ResponseEntity<Any> {
        val question = questionService.findQuestionById(id)
        return ResponseEntity.ok(question)
    }

    //    @PostMapping("")
    override fun post(@RequestBody @Valid item: Question): ResponseEntity<Any> {
        throw NotImplementedError("Question can't be posted without quizId")
//        item.id = 0
//        return ResponseEntity.ok(questionService.save(item))
    }

    @PutMapping("/{id}")
    override fun update(@PathVariable id: Int, @RequestBody @Valid item: Question): ResponseEntity<Any> {
        item.id = id
        val updated = questionService.update(item)
        return ResponseEntity.ok(updated)
    }

    @DeleteMapping("/{id}")
    override fun delete(@PathVariable id: Int): ResponseEntity<Any> {
        questionService.delete(id)
        return ResponseEntity.ok().build()
    }

}