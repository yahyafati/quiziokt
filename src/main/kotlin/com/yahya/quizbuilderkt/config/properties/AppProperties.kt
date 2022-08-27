package com.yahya.quizbuilderkt.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("com.app")
data class AppProperties(
    val error: ErrorProperties?
)