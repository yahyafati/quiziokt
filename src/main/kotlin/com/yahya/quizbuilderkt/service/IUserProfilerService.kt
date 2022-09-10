package com.yahya.quizbuilderkt.service

import com.yahya.quizbuilderkt.model.UserProfile

interface IUserProfilerService {

    fun save(profile: UserProfile): UserProfile
    
}