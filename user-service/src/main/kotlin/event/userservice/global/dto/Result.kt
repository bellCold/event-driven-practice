package event.userservice.global.dto

data class Result<T>(
    private val code: Code,
    private val message: String?,
    private val data: T?
) {

    companion object {
        fun <T> success(data: T?): Result<T> {
            return Result(
                code = Code.SUCCESS,
                message = null,
                data = data
            )
        }
    }
}

enum class Code {
    SUCCESS,
    ERROR;
}