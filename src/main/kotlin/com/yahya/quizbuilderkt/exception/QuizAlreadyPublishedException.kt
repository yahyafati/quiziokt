package com.yahya.quizbuilderkt.exception

class QuizAlreadyPublishedException(override val message: String) : RuntimeException(message) {

    companion object {
        fun createWith(quizId: Int): QuizAlreadyPublishedException {
            return QuizAlreadyPublishedException("Quiz (id = $quizId) has already been published")
        }
    }
}