package event.userservice.domain.jwt

import org.springframework.data.repository.CrudRepository

interface RefreshRedisTokenRepository: CrudRepository<RefreshToken, String> {
}