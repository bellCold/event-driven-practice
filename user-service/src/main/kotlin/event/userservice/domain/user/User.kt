package event.userservice.domain.user

import event.userservice.domain.BaseEntity
import jakarta.persistence.*

@Table(name = "users")
@Entity
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val bulletAccountId: Long? = 0,
    var username: String,
    var email: String,
    var password: String,
    @Enumerated(EnumType.STRING)
    var role: UserRole? = UserRole.BASIC,
    var enabled: Boolean? = true,
) : BaseEntity()

enum class UserRole {
    BASIC, ADMIN
}
