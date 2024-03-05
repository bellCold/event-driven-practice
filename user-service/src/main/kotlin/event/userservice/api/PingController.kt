package event.userservice.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class PingController {

    @GetMapping("/api/v1/ping")
    fun ping(): String {
        return "PONG";
    }
}