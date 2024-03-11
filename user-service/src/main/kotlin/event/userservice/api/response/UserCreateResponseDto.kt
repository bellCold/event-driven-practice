package event.userservice.api.response

import event.userservice.domain.user.User
import java.time.LocalDateTime

data class UserCreateResponseDto(val email: String, val username: String, val createAt: LocalDateTime) {
    companion object {
        fun domainToDto(saveUser: User): UserCreateResponseDto {
            return UserCreateResponseDto(
                email = saveUser.email,
                username = saveUser.username,
                createAt = saveUser.createdAt
            )
        }
    }
}