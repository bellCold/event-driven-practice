package event.userservice.global.dto

import event.userservice.global.dto.Code.SUCCESS

data class Result<T>(
    val code: Code,
    val message: String,
    val data: T
) {
    companion object {
        fun success(): Result<Nothing?> {
            return Result(
                code = SUCCESS,
                message = SUCCESS.description,
                data = null
            )
        }

        fun <T> success(data: T): Result<T> {
            return Result(
                code = SUCCESS,
                message = SUCCESS.description,
                data = data
            )
        }
    }
}

enum class Code(val description: String) {
    SUCCESS("성공"),
    ERROR("실패");
}

