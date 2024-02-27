package event.userservice.api.auth

import event.userservice.application.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class AuthController(private val authService: AuthService) {

    /**
     * TODO
     * login
     * logout
     * refresh
     */

    @PostMapping("/login")
    fun login(): String {
        return "login"
    }

    @PostMapping("/logout")
    fun logout(): String {
        return "logout"
    }

}