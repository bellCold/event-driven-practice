package event.userservice.api.error

class UserServerException(val errorCode: ErrorCode) : RuntimeException() {
}