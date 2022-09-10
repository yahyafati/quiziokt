package com.yahya.quizbuilderkt.service

import com.yahya.quizbuilderkt.dao.UserDao
import com.yahya.quizbuilderkt.exception.UsernameTakenException
import com.yahya.quizbuilderkt.model.User
import com.yahya.quizbuilderkt.model.UserProfile
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userDao: UserDao,
    private val passwordEncoder: PasswordEncoder,
    private val userProfilerService: IUserProfilerService
) : IUserService {

    override fun findByUsername(username: String): User {
        return userDao.findUserByUsername(username)
            ?: throw UsernameNotFoundException("no user with username $username")
    }

    override fun findUserIdByUsername(username: String): Int {
        return userDao.findUserIdByUsername(username)
            ?: throw UsernameNotFoundException("no user with username $username")
    }

    override fun usernameExists(username: String): Boolean {
        return userDao.existsByUsername(username)
    }

    override fun save(user: User): User {
        val savedUser = userDao.save(user)
        val profile = savedUser.profile ?: UserProfile()
        profile.user = savedUser
        savedUser.profile = userProfilerService.save(profile)
        return savedUser
    }

    override fun signup(user: User): User {
        user.id = 0
        val exists = usernameExists(user.username)
        if (exists) {
            throw UsernameTakenException.createWith(user.username)
        }
//        user.profile = user.profile ?: UserProfile()
//        user.profile?.id = 0
        user.password = passwordEncoder.encode(user.password)
        return save(user)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        return findByUsername(username)
    }

}