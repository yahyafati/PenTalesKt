package org.pentales.pentalesrest.models.entities.book.userBook.activity

import jakarta.persistence.*
import org.pentales.pentalesrest.models.entities.activity.book.*
import org.pentales.pentalesrest.models.entities.book.userBook.status.*
import org.pentales.pentalesrest.models.entities.interfaces.*
import org.pentales.pentalesrest.models.entities.user.*

@Entity
class UserBookActivity(
    @ManyToOne
    var user: User = User(),
    @ManyToOne
    var book: ActivityBook = ActivityBook(),

    @Enumerated(EnumType.STRING)
    var status: EUserBookReadStatus = EUserBookReadStatus.NONE,
) : IModel()