package com.example.quizbuilder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class QuizBuilderApplication

fun main(args: Array<String>) {
    runApplication<QuizBuilderApplication>(*args)
}
