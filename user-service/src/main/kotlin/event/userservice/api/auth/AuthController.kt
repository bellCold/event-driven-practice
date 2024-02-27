package event.userservice.api.auth

import event.userservice.application.AuthService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(private val authService: AuthService) {

    /**
     * TODO
     * login
     * logout
     * refresh
     */
}