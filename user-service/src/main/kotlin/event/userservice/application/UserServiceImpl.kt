package event.userservice.application

import event.userservice.api.error.ErrorCode
import event.userservice.api.error.UserServerException
import event.userservice.domain.user.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username) ?: throw UserServerException(ErrorCode.USER_NOT_FOUND)

        val authorities = mutableListOf(SimpleGrantedAuthority(user.role.toString()))
        return User(user.bulletAccountId.toString(), user.password, authorities)
    }
}