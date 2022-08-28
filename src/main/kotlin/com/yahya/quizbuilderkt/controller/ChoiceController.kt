package com.yahya.quizbuilderkt.controller

import com.yahya.quizbuilderkt.model.Choice
import com.yahya.quizbuilderkt.model.Question
import com.yahya.quizbuilderkt.service.IChoiceService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/choice")
class ChoiceController(private val choiceService: IChoiceService) {

    @GetMapping("")
    fun getAll(@RequestParam(name = "question") questionId: Int): ResponseEntity<Any> {
        val choicesByQuestionId = choiceService.getChoicesByQuestionId(questionId)
        return ResponseEntity.ok(choicesByQuestionId)
    }

    @PostMapping("")
    fun post(
        @Valid @RequestBody items: List<Choice>,
        @RequestParam(name = "question") questionId: Int,
//        @RequestParam(defaultValue = "true") replace: Boolean
    ): ResponseEntity<Any> {
        items.forEach {
            it.id = 0
            it.question = Question(id = questionId)
        }
        val saved = choiceService.saveAll(items, true)
        return ResponseEntity.ok(saved)
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Int): ResponseEntity<Any> {
        val choice = choiceService.findChoiceById(id)
        return ResponseEntity.ok(choice)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @Valid @RequestBody item: Choice): ResponseEntity<Any> {
        item.id = id
        val updated = choiceService.update(item)
        return ResponseEntity.ok(updated)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Any> {
        choiceService.delete(id)
        return ResponseEntity.ok().build()
    }


}