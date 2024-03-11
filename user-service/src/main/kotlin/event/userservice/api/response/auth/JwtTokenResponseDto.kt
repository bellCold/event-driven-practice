package event.userservice.api.response.auth

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import event.userservice.global.constant.TIME_FORMAT
import java.util.*

data class JwtTokenResponseDto(
    val accessToken: String,
    @JsonIgnore
    val refreshToken: String,
    @JsonFormat(pattern = TIME_FORMAT)
    val accessTokenExpiredDate: Date
) {
}