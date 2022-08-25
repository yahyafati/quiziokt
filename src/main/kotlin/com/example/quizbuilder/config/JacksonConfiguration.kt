package com.example.quizbuilder.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfiguration(objectMapper: ObjectMapper) {
    init {
        objectMapper.setFilterProvider(SimpleFilterProvider().setFailOnUnknownId(false))
    }
}