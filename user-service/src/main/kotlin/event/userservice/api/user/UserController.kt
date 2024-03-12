package event.userservice.api.user

import event.userservice.api.requset.user.UserCreateRequestDto
import event.userservice.api.response.UserCreateResponseDto
import event.userservice.application.UserService
import event.userservice.api.Result
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
    fun createUser(@RequestBody userCreateRequestDto: UserCreateRequestDto): ResponseEntity<Result<UserCreateResponseDto>> {
        val result = userService.createUser(userCreateRequestDto)
        return ResponseEntity.status(HttpStatus.OK).body(Result.success(result))
    }
}