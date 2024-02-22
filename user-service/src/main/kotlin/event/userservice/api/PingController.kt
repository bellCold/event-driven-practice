package event.userservice.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PingController {

    @GetMapping("/user-service/ping")
    fun ping(): String {
        return "PONG";
    }
}