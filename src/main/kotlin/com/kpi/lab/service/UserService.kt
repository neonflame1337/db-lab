package com.kpi.lab.service

import com.kpi.lab.persistence.postgres.entity.UserEntity
import com.kpi.lab.persistence.postgres.repository.UserRepository
import com.kpi.lab.exception.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun getById(id: UUID) =
        userRepository.findByIdOrNull(id) ?: throw EntityNotFoundException("user with id: $id was not found")

    fun create(firstName: String, lastName:  String)=
        userRepository.save(
            UserEntity(
                firstName = firstName,
                lastName = lastName,
            )
        )

    fun findAll() = userRepository.findAll()

    fun activate(id: UUID) = userRepository.save(getById(id).also { it.activate() })

    fun deactivate(id: UUID) = userRepository.save(getById(id).also { it.deactivate() })

    fun delete(id: UUID) = userRepository.save(getById(id).also { it.deletedAt = Instant.now() })
}