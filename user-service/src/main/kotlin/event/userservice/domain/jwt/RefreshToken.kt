package event.userservice.domain.jwt

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "refresh_token", timeToLive = 3000)
class RefreshToken(
    @Id
    val userId: String,
    val refreshTokenId: String
)