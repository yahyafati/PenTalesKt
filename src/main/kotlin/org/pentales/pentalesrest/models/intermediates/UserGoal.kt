package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import jakarta.validation.constraints.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.keys.*

@Entity
class UserGoal(
    @EmbeddedId
    var id: UserGoalKey = UserGoalKey(),
    @field:Min(1)
    var target: Int = 1,

    @ManyToOne
    @MapsId("userId")
    var user: User = User(),

    @ManyToOne
    @MapsId("goalId")
    var goal: Goal = Goal()

) : IAudit() {}

