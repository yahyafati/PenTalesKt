package org.pentales.pentalesrest.dto.user

data class ProfileMetaDto(
    var followerCount: Int = 0,
    var followingCount: Int = 0,
    var ratingCount: Int = 0,
    var reviewCount: Int = 0,
)
