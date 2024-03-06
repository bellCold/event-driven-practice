package event.orderservice.global.dto

import event.orderservice.global.dto.Code.SUCCESS

data class Result<T>(
    val code: Code,
    val message: String,
    val data: T? = null
) {
    companion object {
        fun success(): Result<Nothing?> {
            return Result(
                code = SUCCESS,
                message = SUCCESS.description
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

