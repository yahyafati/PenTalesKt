package org.pentales.pentalesrest.models

import jakarta.persistence.*
import org.pentales.pentalesrest.models.interfaces.*

@Entity
class ActivityShare(
    var comment: String = "",
    @ManyToOne
    var activity: Activity = Activity(),
) : IModel() {}