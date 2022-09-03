package com.yahya.quizbuilderkt.utils

import kotlin.random.Random
import kotlin.random.nextInt

fun interface PermalinkGenerator {

    companion object {
        private const val POSSIBLE_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_-"
        const val LENGTH = 8
        private val random: Random = Random(Random.Default.nextInt())

        fun getARandomChar(): Char {
            val nextInt = random.nextInt(IntRange(0, POSSIBLE_CHARACTERS.length - 1))
            return POSSIBLE_CHARACTERS[nextInt]
        }
    }

    fun generatePermalink(): String
}