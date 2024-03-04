package event.userservice.application

import event.userservice.domain.jwt.RefreshRedisTokenRepository
import event.userservice.domain.jwt.RefreshToken
import event.userservice.domain.user.UserRepository
import event.userservice.global.utils.JwtTokenProvider
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

@Service
class RefreshTokenService(
    private val userRepository: UserRepository,
    private val refreshRedisTokenRepository: RefreshRedisTokenRepository,
    private val jwtTokenProvider: JwtTokenProvider
) {

    fun updateRefreshToken(id: Long, uuid: String) {
        val user = userRepository.findById(id).orElseThrow { NotFoundException() }
        refreshRedisTokenRepository.save(RefreshToken(user.id.toString(), uuid))
    }

    fun logoutToken(accessToken: String) {
        if (!jwtTokenProvider.validateJwtToken(accessToken)) {
            throw RuntimeException("access token is not valid")
        }

        val refreshToken = refreshRedisTokenRepository.findById(jwtTokenProvider.getUserId(accessToken)).orElseThrow { throw NotFoundException() }

        refreshRedisTokenRepository.delete(refreshToken)
    }

}