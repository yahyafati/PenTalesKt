package org.pentales.pentalesrest.models

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long?,

    var name: String?,
    var username: String?,
    var email: String?,
    var password: String?,
    var phone: String?,

    ) : IModel