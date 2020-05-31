package com.restapi.touristspot.security.user

import com.restapi.touristspot.domain.user.UserService
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service("userDetailsService")
class CustomUserDetailsService(val userService: UserService) : UserDetailsService {
    override fun loadUserByUsername(username: String?) =
            CustomUserDetails(userService.findByEmail(username!!))
}