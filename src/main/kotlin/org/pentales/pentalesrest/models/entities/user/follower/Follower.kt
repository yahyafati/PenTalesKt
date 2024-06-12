package org.pentales.pentalesrest.models.entities.user.follower

import jakarta.persistence.*
import org.pentales.pentalesrest.models.entities.entityKeys.*
import org.pentales.pentalesrest.models.entities.interfaces.*
import org.pentales.pentalesrest.models.entities.user.*

@Entity
class Follower(
    @EmbeddedId
    var id: UserUserKey = UserUserKey(),

    @ManyToOne
    @MapsId("followerId")
    var follower: User = User(), // This is the user who follows

    @ManyToOne
    @MapsId("followedId")
    var followed: User = User() // This is the user who is followed
) : IAudit()