package org.pentales.pentalesrest.models

import jakarta.persistence.*
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.models.interfaces.*

@Entity
class UserBookActivity(
    @ManyToOne
    var user: User = User(),
    @ManyToOne
    var book: ActivityBook = ActivityBook(),

    @Enumerated(EnumType.STRING)
    var status: EUserBookReadStatus = EUserBookReadStatus.NONE,
) : IModel()