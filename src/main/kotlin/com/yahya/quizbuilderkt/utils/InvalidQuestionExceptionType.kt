package com.yahya.quizbuilderkt.utils

enum class InvalidQuestionExceptionType(val message: String) {
    VALID_QUESTION("valid"),
    NO_ANSWER_PROVIDED("no answer provided"),
    QUESTION_HAD_MULTIPLE_ANSWERS("question had multiple answers"),
    MULTIPLE_ANSWER_PROVIDED("multiple answer provided"),
    NO_CHOICE_PROVIDED("no choice provided")
}