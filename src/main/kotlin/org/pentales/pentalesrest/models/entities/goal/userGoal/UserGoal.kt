package org.pentales.pentalesrest.models.entities.goal.userGoal

import jakarta.persistence.*
import jakarta.validation.constraints.*
import org.pentales.pentalesrest.models.entities.entityKeys.*
import org.pentales.pentalesrest.models.entities.goal.*
import org.pentales.pentalesrest.models.entities.interfaces.*
import org.pentales.pentalesrest.models.entities.user.*

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

) : IAudit() {

    @Transient
    var __current: Int = 0

}

