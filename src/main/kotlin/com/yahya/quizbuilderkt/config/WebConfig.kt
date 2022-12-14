package com.yahya.quizbuilderkt.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.ser.FilterProvider
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import com.yahya.quizbuilderkt.config.properties.AppProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit


@Configuration
@EnableWebMvc
class WebConfig(val appProperties: AppProperties) : WebMvcConfigurer {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(WebConfig::class.java)
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**")
//            .allowedOriginPatterns(
//                "https://*.prodegepeeq.com",
//                "http://*.pmrdev.com:[*]",
//                "https://*.pmrdev.com:[*]",
//                "http://*.devpeeq.com:[*]",
//                "https://*.devpeeq.com:[*]"
//            )
            .allowedOriginPatterns("*")
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
            .allowedHeaders("*")
            .maxAge(TimeUnit.HOURS.toSeconds(6))
    }

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        val mapper: ObjectMapper = Jackson2ObjectMapperBuilder
            .json()
            .filters(errorResponseFilterProvider())
            .dateFormat(SimpleDateFormat("yyyy-MM-dd HH:mm"))
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .build()
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)

        val converter = MappingJackson2HttpMessageConverter(mapper)
        converters.add(converter)
    }

    @Bean
    fun errorResponseFilterProvider(): FilterProvider {
        val showStackTrace = appProperties.error?.showStackTrace == true
        LOG.info("showStackTrace: {}", showStackTrace)
        val except: Set<String> = if (showStackTrace) setOf() else setOf("stacktrace")
        val filter: SimpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept(except)
        return SimpleFilterProvider().addFilter("errorFilter", filter).setFailOnUnknownId(false)
    }
}