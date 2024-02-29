package event.userservice.global.dto

data class LoginRequest(
    val name: String,
    val email: String,
    val password: String
)
