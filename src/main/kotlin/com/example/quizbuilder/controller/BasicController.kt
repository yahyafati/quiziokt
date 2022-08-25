package com.example.quizbuilder.controller

import com.example.quizbuilder.model.IModel
import com.example.quizbuilder.model.Quiz
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

interface BasicController<T : IModel> {

    fun getAll(): ResponseEntity<Any>;

    fun getOne(@PathVariable id:Int): ResponseEntity<Any>;

    fun post(@RequestBody item: T): ResponseEntity<Any>;

    fun update(@PathVariable id:Int, @RequestBody item: T): ResponseEntity<Any> ;

    fun delete(@PathVariable id:Int) : ResponseEntity<Any>

}