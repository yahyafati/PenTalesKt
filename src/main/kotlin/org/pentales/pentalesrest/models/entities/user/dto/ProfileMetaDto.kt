package org.pentales.pentalesrest.models.entities.user.dto

data class ProfileMetaDto(
    var followerCount: Int = 0,
    var followingCount: Int = 0,
    var ratingCount: Int = 0,
    var reviewCount: Int = 0,
)
