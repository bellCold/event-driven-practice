package event.userservice.application

import event.userservice.api.requset.user.CreateUserRequestDto
import event.userservice.domain.user.User
import event.userservice.domain.user.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) {

    fun createUser(createUserRequestDto: CreateUserRequestDto) {
        if (userRepository.existsByEmail(createUserRequestDto.email)) {
            throw IllegalStateException()
        }

        val user = User(
            username = createUserRequestDto.username,
            email = createUserRequestDto.email,
            password = passwordEncoder.encode(createUserRequestDto.password)
        )

        userRepository.save(user)
    }

}
