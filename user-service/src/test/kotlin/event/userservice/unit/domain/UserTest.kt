package event.userservice.unit.domain

import event.userservice.domain.user.User
import event.userservice.domain.user.UserRepository
import event.userservice.domain.user.UserRole
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.boot.test.mock.mockito.MockBean

class UserTest {

    @MockBean
    private var userRepository: UserRepository = Mockito.mock(UserRepository::class.java)

    @Test
    fun userCreateTest() {
        val saveUser = User(
            username = "bellCold",
            email = "whdcks420@gmail.com",
            role = UserRole.ADMIN,
            password = "123456789"
        )

        `when`(userRepository.save(saveUser)).thenReturn(saveUser)

        val savedUser = userRepository.save(saveUser)

        assertThat(savedUser.username).isEqualTo("bellCold")
        assertThat(savedUser.role).isSameAs(UserRole.ADMIN)
    }

}