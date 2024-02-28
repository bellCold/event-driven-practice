package event.userservice.integration.domain

import event.userservice.domain.user.User
import event.userservice.domain.user.UserRepository
import event.userservice.domain.user.UserRole
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserTest(
    @Autowired
    val userRepository: UserRepository
) {

    @Test
    fun userCreateTest() {
        val saveUser = User(
            username = "bellCold",
            email = "whdcks420@gmail.com",
            role = UserRole.ADMIN,
            password = "123456789"
        )

        userRepository.save(saveUser)

        val findUser = userRepository.findById(1).orElseThrow { throw NoSuchElementException() }

        assertThat(findUser.username).isEqualTo("bellCold")
        assertThat(findUser.role).isSameAs(UserRole.ADMIN)
    }
}