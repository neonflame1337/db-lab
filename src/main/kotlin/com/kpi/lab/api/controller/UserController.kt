package com.kpi.lab.api.controller

import com.kpi.lab.api.model.user.UserCreateRequest
import com.kpi.lab.api.model.user.toDto
import com.kpi.lab.service.UserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("api/v1/user")
class UserController(
    private val userService: UserService,
) {
    @GetMapping("/list")
    fun getList() = userService.findAll().map { it.toDto() }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: UUID) = userService.getById(userId).toDto()

    @PostMapping("/create")
    fun createUser(@RequestBody request: UserCreateRequest) =
        userService.create(request.firstName, request.lastName).toDto()

    @GetMapping("{userId}/activate")
    fun activateUser(@PathVariable userId: UUID) = userService.activate(userId).toDto()

    @GetMapping("{userId}/deactivate")
    fun deactivateUser(@PathVariable userId: UUID) = userService.deactivate(userId).toDto()

    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: UUID) = userService.delete(userId).toDto()
}