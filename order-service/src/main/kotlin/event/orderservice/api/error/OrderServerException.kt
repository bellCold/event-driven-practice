package event.orderservice.api.error

class OrderServerException(val errorCode: ErrorCode) : RuntimeException() {
}