package event.userservice.application

import event.userservice.api.error.ErrorCode
import event.userservice.api.error.UserServerException
import event.userservice.api.requset.user.UserCreateRequestDto
import event.userservice.api.response.UserCreateResponseDto
import event.userservice.domain.user.User
import event.userservice.domain.user.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder) {

    fun createUser(userCreateRequestDto: UserCreateRequestDto): UserCreateResponseDto  {
        if (userRepository.existsByEmail(email = userCreateRequestDto.email)) {
            throw UserServerException(ErrorCode.ALREADY_EXISTED_USER)
        }

        val user = User(
            username = userCreateRequestDto.username,
            email = userCreateRequestDto.email,
            password = passwordEncoder.encode(userCreateRequestDto.password)
        )

        val saveUser = userRepository.save(user)

        return UserCreateResponseDto.domainToDto(saveUser)

    }

}
