package event.userservice.domain.jwt

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("refresh_token")
class RefreshToken(
    @Id
    val userId: String,
    val refreshTokenId: String
)