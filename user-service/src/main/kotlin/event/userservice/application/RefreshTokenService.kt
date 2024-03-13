package event.userservice.application

import event.userservice.api.error.ErrorCode
import event.userservice.api.error.UserServerException
import event.userservice.api.response.auth.JwtTokenResponseDto
import event.userservice.domain.jwt.RefreshRedisTokenRepository
import event.userservice.domain.jwt.RefreshToken
import event.userservice.domain.user.UserRepository
import event.userservice.global.utils.JwtTokenProvider
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RefreshTokenService(
    private val userRepository: UserRepository,
    private val refreshRedisTokenRepository: RefreshRedisTokenRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val userDetailsService: UserDetailsService
) {

    fun updateRefreshToken(id: Long, uuid: String) {
        val user = userRepository.findById(id).orElseThrow { throw UserServerException(ErrorCode.USER_NOT_FOUND) }
        refreshRedisTokenRepository.save(RefreshToken(user.bulletAccountId.toString(), uuid))
    }

    fun logoutToken(accessToken: String) {
        if (!jwtTokenProvider.validateJwtToken(accessToken)) {
            throw UserServerException(ErrorCode.INVALID)
        }

        val refreshToken = refreshRedisTokenRepository.findById(jwtTokenProvider.getUserId(accessToken))
            .orElseThrow { throw NotFoundException() }

        refreshRedisTokenRepository.delete(refreshToken)
    }

    @Transactional
    fun refreshJwtToken(accessToken: String, refreshToken: String): JwtTokenResponseDto {
        val userId = jwtTokenProvider.getUserId(accessToken)
        val findRefreshToken = refreshRedisTokenRepository.findById(userId).orElseThrow { throw UserServerException(ErrorCode.USER_NOT_FOUND) }
        val findRefreshTokenId = findRefreshToken.refreshTokenId

        if (!jwtTokenProvider.equalRefreshTokenId(findRefreshTokenId, refreshToken)) {
            throw IllegalArgumentException()
        }

        if (!jwtTokenProvider.validateJwtToken(refreshToken)) {
            refreshRedisTokenRepository.delete(findRefreshToken)
            throw IllegalArgumentException()
        }

        val findUser = userRepository.findById(userId.toLong()).orElseThrow { throw UserServerException(ErrorCode.USER_NOT_FOUND) }

        val roles = getAuthentication(findUser.email).authorities
            .map(GrantedAuthority::getAuthority)
            .toList()

        val newAccessToken = jwtTokenProvider.createAccessToken(userId, "/reissue", roles)
        val expiredTime = jwtTokenProvider.getExpiredTime(newAccessToken)

        return JwtTokenResponseDto(
            accessToken = newAccessToken,
            refreshToken = refreshToken,
            accessTokenExpiredDate = expiredTime
        )
    }

    private fun getAuthentication(email: String): Authentication {
        val userDetails = userDetailsService.loadUserByUsername(email)
        return UsernamePasswordAuthenticationToken(userDetails, userDetails.password, userDetails.authorities)
    }

}