package org.pentales.pentalesrest.models

import jakarta.persistence.*

@Entity
class ActivityShare(
    var comment: String = "",
    @ManyToOne
    var activity: Activity = Activity(),
) : IModel() {}