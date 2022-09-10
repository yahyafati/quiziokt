package com.yahya.quizbuilderkt.service

import com.yahya.quizbuilderkt.dao.UserProfileDao
import com.yahya.quizbuilderkt.model.UserProfile
import org.springframework.stereotype.Service

@Service
class UserProfileService(private val userProfileDao: UserProfileDao) : IUserProfilerService {

    override fun save(profile: UserProfile): UserProfile {
        return userProfileDao.save(profile)
    }
}