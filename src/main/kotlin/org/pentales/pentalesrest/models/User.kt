package org.pentales.pentalesrest.models

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) override var id: Long = 0L,
    var name: String = "",
    var username: String = "",
    var email: String = "",
    var password: String = "",
    var phone: String = "",
) : IModel() {

    override fun toString(): String {
        return "User(id=$id, name='$name', username='$username', email='$email', password='$password', phone='$phone')"
    }
}