package com.yahya.quizbuilderkt.dao

import com.yahya.quizbuilderkt.model.UserProfile
import org.springframework.data.jpa.repository.JpaRepository

interface UserProfileDao : JpaRepository<UserProfile, Int> {
}