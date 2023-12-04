package org.pentales.pentalesrest.models

import jakarta.persistence.*
import org.pentales.pentalesrest.models.keys.*

@Entity
class Follower(
    @EmbeddedId
    var id: UserUserKey = UserUserKey(),

    @ManyToOne
    @MapsId("followerId")
    var follower: User = User(),

    @ManyToOne
    @MapsId("followingId")
    var following: User = User()
) : IAudit()