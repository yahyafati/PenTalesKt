package org.pentales.pentalesrest.models.entities.user.authority.authorityUser

import jakarta.persistence.*
import org.pentales.pentalesrest.models.entities.entityKeys.*
import org.pentales.pentalesrest.models.entities.user.*
import org.pentales.pentalesrest.models.entities.user.authority.*
import org.pentales.pentalesrest.models.entities.user.role.*

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
) {

    override fun toString(): String {
        return "AuthorityUser(id=$id, user=$user, authority=$authority)"
    }
}