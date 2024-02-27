package event.userservice.domain.jwt

import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("refresh_token")
class RefreshToken(
    @Id
    val userId: String,
    val refreshTokenId: String
) {
}