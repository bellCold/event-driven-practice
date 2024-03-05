package event.userservice.global.dto

import event.userservice.global.dto.Code.SUCCESS

data class Result(
    val code: Code,
    val message: String,
) {
    companion object {
        fun success(): Result {
            return Result(
                code = SUCCESS,
                message = SUCCESS.description
            )
        }
    }
}

enum class Code(val description: String) {
    SUCCESS("성공"),
    ERROR("실패");
}

