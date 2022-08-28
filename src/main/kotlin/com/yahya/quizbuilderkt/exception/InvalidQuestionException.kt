package com.yahya.quizbuilderkt.exception

import org.springframework.http.HttpStatus

class InvalidQuestionException(override val message: String = "", val status: HttpStatus = HttpStatus.NOT_FOUND) :
    RuntimeException(message) {

    companion object {
        fun noAnswerProvided(): InvalidQuestionException {
            return InvalidQuestionException("no answer provided")
        }

        fun multipleAnswerProvided(): InvalidQuestionException {
            return InvalidQuestionException("more than one answer provided for a single answer question")
        }

        fun noChoiceProvided(): InvalidQuestionException {
            return InvalidQuestionException("empty choice provided")
        }
    }
}