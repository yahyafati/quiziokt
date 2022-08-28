package com.yahya.quizbuilderkt.service

import com.yahya.quizbuilderkt.dao.UserDao
import com.yahya.quizbuilderkt.exception.UsernameTakenException
import com.yahya.quizbuilderkt.model.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userDao: UserDao, private val passwordEncoder: PasswordEncoder) : IUserService {

    override fun findByUsername(username: String): User {
        return userDao.findUserByUsername(username)
            ?: throw UsernameNotFoundException("no user with username $username")
    }

    override fun usernameExists(username: String): Boolean {
        return userDao.existsByUsername(username)
    }

    override fun save(user: User): User {
        return userDao.save(user)
    }

    override fun signup(user: User): User {
        user.id = 0
        val exists = usernameExists(user.username)
        if (exists) {
            throw UsernameTakenException.createWith(user.username)
        }
        user.password = passwordEncoder.encode(user.password)
        return save(user)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        return findByUsername(username)
    }

}