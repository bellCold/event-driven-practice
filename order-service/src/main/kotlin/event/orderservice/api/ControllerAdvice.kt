package event.orderservice.api

import event.orderservice.api.error.ErrorCode
import event.orderservice.api.error.LogType
import event.orderservice.api.error.OrderServerException
import event.orderservice.global.dto.Result
import event.orderservice.global.logger.logger
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

    @ExceptionHandler(OrderServerException::class)
    protected fun handleOrderServerException(e: OrderServerException): ResponseEntity<Result<Nothing>> {
        errorLog("Order Server Exception occurred", e)
        return ResponseEntity.status(e.errorCode.code).body(Result.failure(e.errorCode))
    }

    @ExceptionHandler(Exception::class)
    protected fun handleException(e: Exception) {
        errorLog("handle Exception", e)
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(Result.failure(ErrorCode.INTER_SERVER_ERROR))
    }

    private fun errorLog(errorMessage: String, throwable: Throwable) {
        when (throwable) {
            is OrderServerException -> {
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