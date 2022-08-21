package com.example.quizbuilder.controller

import com.example.quizbuilder.model.IModel
import com.example.quizbuilder.model.Quiz
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

interface BasicController<T : IModel> {

    @GetMapping("")
    fun getAll(): ResponseEntity<Any>;

    @GetMapping("/{id}")
    fun getOne(@PathVariable id:Int): ResponseEntity<Any>;

    @PostMapping("")
    fun post(@RequestBody item: T): ResponseEntity<Any>;

    @PutMapping("/{id}")
    fun update(@PathVariable id:Int, @RequestBody item: T): ResponseEntity<Any> ;

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id:Int) : ResponseEntity<Any>

}