package com.yahya.quizbuilderkt.exception

class QuizAlreadyPublished(override val message: String) : RuntimeException(message) {

    companion object {
        fun createWith(quizId: Int): QuizAlreadyPublished {
            return QuizAlreadyPublished("Quiz (id = $quizId) has already been published")
        }
    }
}