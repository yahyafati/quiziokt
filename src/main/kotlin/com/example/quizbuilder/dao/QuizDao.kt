package com.example.quizbuilder.dao

import com.example.quizbuilder.model.Quiz
import org.springframework.data.jpa.repository.JpaRepository

interface QuizDao : JpaRepository<Quiz, Int> {
}