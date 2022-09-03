package com.yahya.quizbuilderkt.config

import com.yahya.quizbuilderkt.utils.PermalinkGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class BeanConfiguration {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun permalinkGenerator(): PermalinkGenerator {
        return PermalinkGenerator {
            val stringBuilder: StringBuilder = StringBuilder(PermalinkGenerator.LENGTH)
            for (i in 1..PermalinkGenerator.LENGTH) {
                stringBuilder.append(PermalinkGenerator.getARandomChar())
            }
            stringBuilder.toString()
        }
    }

}