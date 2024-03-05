package event.userservice.api.user

import event.userservice.api.requset.user.CreateUserRequestDto
import event.userservice.application.UserService
import event.userservice.global.dto.Result
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class UserController(private val userService: UserService) {

    @PostMapping("/users")
    fun createUser(@RequestBody createUserRequestDto: CreateUserRequestDto): ResponseEntity<Result<Nothing?>> {
        userService.createUser(createUserRequestDto)
        return ResponseEntity.status(HttpStatus.OK).body(Result.success())
    }
}