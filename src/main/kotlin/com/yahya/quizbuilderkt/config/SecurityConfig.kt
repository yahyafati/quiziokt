package com.yahya.quizbuilderkt.config

import com.yahya.quizbuilderkt.security.JWTAuthenticationFilter
import com.yahya.quizbuilderkt.security.JWTAuthorizationFilter
import com.yahya.quizbuilderkt.security.SecurityConstants
import com.yahya.quizbuilderkt.service.IUserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain


@Configuration
class SecurityConfig(private val authenticationManager: AuthenticationManager, private val userService: IUserService) {


    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity
            .cors().and()
            .csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // this disables session creation on Spring Security
            .and()
            .addFilter(JWTAuthenticationFilter(authenticationManager))
            .addFilter(JWTAuthorizationFilter(authenticationManager, userService))
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/api/v1/quiz/permalink/*").permitAll()
            .antMatchers(HttpMethod.POST, SecurityConstants.LOGIN_URL).permitAll()
            .antMatchers(HttpMethod.POST, "/api/v1/user/register").permitAll()
            .antMatchers(HttpMethod.GET, "/api/v1/user/isAvailable").permitAll()
            .anyRequest().authenticated()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(CustomEntryPoint(HttpStatus.UNAUTHORIZED, "You are not authorized"))
        return httpSecurity.build()
        // Add a filter to validate the tokens with every request
//        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
//        return httpSecurity.build()
    }


}