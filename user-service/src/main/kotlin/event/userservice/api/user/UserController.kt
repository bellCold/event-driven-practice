package event.userservice.api.user

import event.userservice.api.requset.user.CreateUserRequestDto
import event.userservice.application.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class UserController(private val userService: UserService) {

    @PostMapping("/users")
    fun createUser(@RequestBody createUserRequestDto: CreateUserRequestDto) {
        userService.createUser(createUserRequestDto)
    }
}