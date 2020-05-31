package com.restapi.touristspot.domain.user

import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity
import java.security.Principal
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("users")
class UserController(
        private val userService: UserService,
        private val tokenServices: DefaultTokenServices,
        private val tokenStore: TokenStore
) {

    @PostMapping("/signup")
    fun signup(@RequestBody user: User): ResponseEntity<Unit> =
            ResponseEntity.noContent().build<Unit>().apply {
                userService.registerUser(user)
            }

    @PostMapping("/signin")
    fun signin(@RequestParam email: String, @RequestParam password: String) = RestTemplate()
            .apply { }
            .postForEntity<String>("localhost:8080/oauth/token?grant_type=password&username=$email&password=$password", null)

    @GetMapping("/main")
    fun getMainUser(principal: Principal) = userService.findByEmail(principal.name)

    @GetMapping("/logout")
    fun logout(request: HttpServletRequest): ResponseEntity<Unit> =
            ResponseEntity.noContent().build<Unit>().apply {
                when (val authHeader = request.getHeader("Authorization")) {
                    is String -> {
                        val accessToken = tokenServices
                                .readAccessToken(authHeader.replace("Bearer ", ""))
                        tokenStore.removeAccessToken(accessToken)
                        tokenServices.revokeToken(accessToken.value)
                    }
                }
            }

}