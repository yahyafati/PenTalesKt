package org.pentales.pentalesrest.models.entities.user

import org.pentales.pentalesrest.global.dto.*
import org.pentales.pentalesrest.models.entities.user.dto.*
import org.pentales.pentalesrest.models.entities.user.role.*
import org.pentales.pentalesrest.utils.*
import org.springframework.data.domain.*
import org.springframework.http.*
import org.springframework.security.access.prepost.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasPermission('ADMIN', 'READ')")
class AdminController(
    private val userService: IUserServices,
) {

    @GetMapping("/users")
    fun getUsers(
        @RequestParam(required = false)
        roles: Set<ERole>?,
        @RequestParam(required = false)
        permissions: Set<EPermission>?,
    ): ResponseEntity<BasicResponseDto<Page<UserSecurityDto>>> {
        val pageRequest = ServletUtil.getPageRequest()

        if (roles != null && permissions != null) {
            throw IllegalArgumentException("You can only filter by roles or permissions, not both")
        }

        val baseURL = ServletUtil.getBaseURLFromCurrentRequest()
        val users = if (!roles.isNullOrEmpty()) {
            userService.findAllByRoles(roles, pageRequest)
        } else if (!permissions.isNullOrEmpty()) {
            userService.findAllByPermissions(permissions, pageRequest)
        } else {
            userService.findAll(pageRequest)
        }
        val dto = users.map { UserSecurityDto(it, baseURL) }
        return ResponseEntity.ok(BasicResponseDto.ok(dto))
    }

    @GetMapping("/users/{username}")
    fun getUserByUsername(
        @PathVariable
        username: String,
    ): ResponseEntity<BasicResponseDto<UserSecurityDto>> {
        val user = userService.findByUsername(username)
        val userDto = UserSecurityDto(user, ServletUtil.getBaseURLFromCurrentRequest())
        return ResponseEntity.ok(BasicResponseDto.ok(userDto))
    }

    @PatchMapping("/users/{username}/role")
    @PreAuthorize("hasPermission('ADMIN', 'WRITE')")
    fun changeRole(
        @PathVariable
        username: String,
        @RequestParam
        role: ERole,
    ): ResponseEntity<BasicResponseDto<UserSecurityDto>> {
        val user = userService.findByUsername(username)
        val status = userService.changeRole(user, role)
        val userDto = UserSecurityDto(user, ServletUtil.getBaseURLFromCurrentRequest())
        if (status) {
            userDto.role = role
        }
        return ResponseEntity.ok(BasicResponseDto.ok(userDto))
    }

    @PatchMapping("/users/{username}/permissions/add")
    @PreAuthorize("hasPermission('ADMIN', 'WRITE')")
    fun addPermissions(
        @PathVariable
        username: String,
        @RequestBody
        permissions: Set<EPermission>,
    ): ResponseEntity<BasicResponseDto<UserSecurityDto>> {
        val user = userService.findByUsername(username)
        val savedUser = userService.addPermissions(user, permissions)
        val userDto = UserSecurityDto(savedUser, ServletUtil.getBaseURLFromCurrentRequest())
        return ResponseEntity.ok(BasicResponseDto.ok(userDto))
    }

    @PatchMapping("/users/{username}/permissions/remove")
    @PreAuthorize("hasPermission('ADMIN', 'WRITE')")
    fun removePermissions(
        @PathVariable
        username: String,
        @RequestBody
        permissions: Set<EPermission>,
    ): ResponseEntity<BasicResponseDto<UserSecurityDto>> {
        val user = userService.findByUsername(username)
        val savedUser = userService.removePermissions(user, permissions)
        val userDto = UserSecurityDto(savedUser, ServletUtil.getBaseURLFromCurrentRequest())
        return ResponseEntity.ok(BasicResponseDto.ok(userDto))
    }

    @PostMapping("/users/{username}/permissions")
    @PreAuthorize("hasPermission('ADMIN', 'WRITE')")
    fun postPermissions(
        @PathVariable
        username: String,
        @RequestBody
        permissions: Set<EPermission>,
    ): ResponseEntity<BasicResponseDto<UserSecurityDto>> {
        val savedUser = userService.setPermissions(username, permissions)
        val userDto = UserSecurityDto(savedUser, ServletUtil.getBaseURLFromCurrentRequest())
        return ResponseEntity.ok(BasicResponseDto.ok(userDto))
    }
}