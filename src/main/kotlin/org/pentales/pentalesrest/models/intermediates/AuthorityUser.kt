package org.pentales.pentalesrest.models.intermediates

import jakarta.persistence.*
import org.pentales.pentalesrest.models.*
import org.pentales.pentalesrest.models.enums.*
import org.pentales.pentalesrest.models.keys.*

@Entity
@Table(name = "authority_user")
class AuthorityUser(
    @EmbeddedId
    var id: AuthorityUserKey = AuthorityUserKey(),

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    var user: User = User(),

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("authorityId")
    var authority: Authority = Authority(EPermission.USER_READ),
)