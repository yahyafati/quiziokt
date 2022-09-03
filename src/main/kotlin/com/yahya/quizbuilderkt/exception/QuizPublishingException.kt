package com.yahya.quizbuilderkt.exception

class QuizPublishingException(override val message: String, val validations: HashMap<Int, String>) :
    RuntimeException(message) {
}