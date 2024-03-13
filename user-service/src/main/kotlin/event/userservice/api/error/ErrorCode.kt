package event.userservice.api.error

import event.userservice.api.error.LogType.*

enum class ErrorCode(val code: Int, val logType: LogType, val message: String) {
    BAD_REQUEST(400, ERROR, "요청 오류"),
    ALREADY_EXISTED_USER(400, ERROR, "이미 존재하는 회원입니다."),
    INTER_SERVER_ERROR(500, ERROR, "유저 서버 에러"),
    USER_NOT_FOUND(400, ERROR, "해당 유저를 찾을수 없습니다."),
    INVALID(403, ERROR, "유효한 인증이 아닙니다.")
}

enum class LogType {
    WARN,
    ERROR
}