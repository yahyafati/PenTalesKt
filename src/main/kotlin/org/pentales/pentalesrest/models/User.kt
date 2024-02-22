package org.pentales.pentalesrest.models

import jakarta.persistence.*
import jakarta.validation.constraints.*
import org.pentales.pentalesrest.models.embeddables.*
import org.pentales.pentalesrest.models.interfaces.*
import org.pentales.pentalesrest.models.intermediates.*
import org.springframework.security.core.*
import org.springframework.security.core.userdetails.*

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long = 0L,
    @NotBlank(message = "Username is required")
    private var username: String = "",
    @NotBlank(message = "Password is required")
    private var password: String = "",
    @NotBlank(message = "Email is required")
    var email: String = "",
    @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.REMOVE], mappedBy = "user")
    var profile: UserProfile? = null,
    @Embedded
    var provider: UserProvider = UserProvider(),
) : IModel(), UserDetails {

    @Transient
    var __isFollowed: Boolean = false
    private var isAccountNonExpired: Boolean = true
    private var isAccountNonLocked: Boolean = true

    //    private var isCredentialsNonExpired: Boolean = true
    private var isEnabled: Boolean = true

    @ManyToOne(fetch = FetchType.EAGER)
    var role: Role = Role()

    @OneToMany(
        mappedBy = "user",
        fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST, CascadeType.REMOVE]
    )
    var authorities: MutableSet<AuthorityUser> = mutableSetOf()

    override fun toString(): String {
        return "User(id=$id, username='$username', email='$email', password='$password')"
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authorities.map { it.authority }.toMutableList()
    }

    override fun getPassword(): String {
        return this.password
    }

    fun setPassword(password: String) {
        this.password = password
    }

    override fun getUsername(): String {
        return this.username
    }

    fun setUsername(username: String) {
        this.username = username
    }

    override fun isAccountNonExpired(): Boolean {
        return this.isAccountNonExpired
    }

    fun setAccountNonExpired(isAccountNonExpired: Boolean) {
        this.isAccountNonExpired = isAccountNonExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return this.isAccountNonLocked
    }

    fun setAccountNonLocked(isAccountNonLocked: Boolean) {
        this.isAccountNonLocked = isAccountNonLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return this.isEnabled
    }

    fun setEnabled(isEnabled: Boolean) {
        this.isEnabled = isEnabled
    }
}