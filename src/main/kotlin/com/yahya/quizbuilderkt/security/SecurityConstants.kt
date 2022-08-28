package com.yahya.quizbuilderkt.security

import java.util.concurrent.TimeUnit

object SecurityConstants {
    @JvmField
    val EXPIRATION_TIME = TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS).toInt() // 24*60*60*1000;
    const val SECRET = "SECRET_KEY"
    const val TOKEN_PREFIX = "Bearer "
    const val HEADER_STRING = "Authorization"
    const val LOGIN_URL = "/api/v1/user/login"
    const val PASSWORD_REGEX: String = "(.*?)"
//    const val PASSWORD_REGEX: String = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).{8,}\$"
}