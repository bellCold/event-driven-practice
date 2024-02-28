package event.userservice.domain.user

import jakarta.persistence.*

@Table(name = "users")
@Entity
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0,
    var username: String,
    var email: String,
    var password: String,
    @Enumerated(EnumType.STRING)
    var role: UserRole,
    var enabled: Boolean = true,
)

enum class UserRole {
    BASIC, ADMIN
}
