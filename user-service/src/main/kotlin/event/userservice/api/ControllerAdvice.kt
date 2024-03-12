package event.userservice.api

import event.userservice.api.error.ErrorCode
import event.userservice.api.error.LogType
import event.userservice.api.error.UserServerException
import event.userservice.global.logger.logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvice {
    val log = logger()

    companion object {
        private const val SUFFIX = "{}"
    }

    @ExceptionHandler(UserServerException::class)
    protected fun handleOrderServerException(e: UserServerException): ResponseEntity<Result<Nothing>> {
        errorLog("Order Server Exception occurred", e)
        return ResponseEntity.status(e.errorCode.code).body(Result.failure(e.errorCode))
    }

    @ExceptionHandler(Exception::class)
    protected fun handleException(e: Exception): ResponseEntity<Result<Nothing>> {
        errorLog("handle Exception", e)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(Result.failure(ErrorCode.INTER_SERVER_ERROR))
    }

    private fun errorLog(errorMessage: String, throwable: Throwable) {
        when (throwable) {
            is UserServerException -> {
                val errorCode = throwable.errorCode
                when (errorCode.logType) {
                    LogType.WARN -> log.warn("$errorMessage -> $SUFFIX", errorCode.message)
                    LogType.ERROR -> log.error("$errorMessage -> $SUFFIX", errorCode.message)
                }
            }

            else -> log.error(errorMessage, throwable)
        }
    }

}