package com.yahya.quizbuilderkt.controller

import com.yahya.quizbuilderkt.model.IModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

interface BasicController<T : IModel> {

    fun getAll(): ResponseEntity<Any>

    fun getOne(@PathVariable id: Int): ResponseEntity<Any>

    fun post(@RequestBody item: T): ResponseEntity<Any>

    fun update(@PathVariable id: Int, @RequestBody item: T): ResponseEntity<Any>

    fun delete(@PathVariable id: Int): ResponseEntity<Any>

}