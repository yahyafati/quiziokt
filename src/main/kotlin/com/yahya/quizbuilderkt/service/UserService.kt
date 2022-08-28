package com.yahya.quizbuilderkt.service

import com.yahya.quizbuilderkt.dao.UserDao
import com.yahya.quizbuilderkt.model.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(private val userDao: UserDao) : IUserService {

    override fun findByUsername(username: String): User {
        return userDao.findUserByUsername(username)
            ?: throw UsernameNotFoundException("no user with username $username")
    }

    override fun loadUserByUsername(username: String): UserDetails {
        return findByUsername(username)
    }

}