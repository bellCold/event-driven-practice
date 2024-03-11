package event.orderservice.api.error

import event.orderservice.api.error.LogType.ERROR

enum class ErrorCode(val code: Int, val logType: LogType, val message: String) {
    BAD_REQUEST(400, ERROR, "요청 오류"),
    ORDER_NOT_FOUND(400, ERROR, "해당 주문을 찾을 수 없습니다."),
    INTER_SERVER_ERROR(500, ERROR, "주문 서버 에러")
}

enum class LogType {
    WARN,
    ERROR
}