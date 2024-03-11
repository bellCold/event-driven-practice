package event.userservice.api.error

import event.userservice.api.error.LogType.*

enum class ErrorCode(val code: Int, val logType: LogType, val message: String) {
    BAD_REQUEST(400, ERROR, "요청 오류"),
    ALREADY_EXISTED_USER(400, ERROR, "존재하는 회원입니다."),
    INTER_SERVER_ERROR(500, ERROR, "주문 서버 에러")
}

enum class LogType {
    WARN,
    ERROR
}