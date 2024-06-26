package org.pentales.pentalesrest.config.properties

import org.pentales.pentalesrest.utils.*
import org.springframework.boot.context.properties.*
import org.springframework.stereotype.*

@Component
@ConfigurationProperties(prefix = "org.pen-tales.goal")
data class GoalProperties(
    var default: DefaultGoalProperties = DefaultGoalProperties(),
) {

    @Component
    data class DefaultGoalProperties(
        var description: String = "",
        var title: String = "",
        var year: Int = TimeUtil.getCurrentYearUTC(),
    )

}
