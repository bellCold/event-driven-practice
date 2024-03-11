package event.userservice.global.dto

import event.userservice.api.error.ErrorCode
import event.userservice.global.dto.Code.*

data class Result<T>(
    val code: Code,
    val message: String,
    val data: T? = null
) {
    companion object {
        fun success(): Result<Nothing> {
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

        fun failure(errorCode: ErrorCode): Result<Nothing> {
            return Result(
                code = FAIL,
                message = errorCode.message
            )
        }
    }
}

enum class Code(val description: String) {
    SUCCESS("성공"),
    FAIL("실패");
}

