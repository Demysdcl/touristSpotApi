package com.restapi.touristspot.domain.user

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class UserServiceTest {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userRepository: UserRepository

    @AfterEach
    fun destroy() {
        userRepository.deleteAll()
    }

    @Test
    fun `given a new User to register then save it`() {
        val user = userService.registerUser(
                User(name = "Demys Cota de Lima", email = "demysdcl@gmail.com", password = "123"))
        assertNotNull(user)
        assertNotNull(user.id)
    }

    @Test
    fun `given another User but with a existing e-mail then throw expection`() {
        this.`given a new User to register then save it`()
        val expectation = assertThrows<RuntimeException> {
            userService.registerUser(
                    User(name = "Zé da esquina", email = "demysdcl@gmail.com", password = "123"))
        }

        assertEquals("The e-mail: demysdcl@gmail.com already exists", expectation.message)
    }


}