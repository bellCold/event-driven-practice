package event.userservice.api.response.auth

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*

data class JwtTokenResponseDto(
    val accessToken: String,
    @JsonIgnore
    val refreshToken: String,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val accessTokenExpiredDate: Date
) {
}