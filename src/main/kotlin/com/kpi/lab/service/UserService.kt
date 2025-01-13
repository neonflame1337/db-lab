package com.kpi.lab.service

import com.kpi.lab.exception.EntityNotFoundException
import com.kpi.lab.persistence.mongo.entity.UserDocument
import com.kpi.lab.persistence.mongo.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Instant


@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun getById(id: String) =
        userRepository.findByIdOrNull(id.toString()) ?: throw EntityNotFoundException("user with id: $id was not found")

    fun create(firstName: String, lastName:  String)=
        userRepository.save(
            UserDocument(
                firstName = firstName,
                lastName = lastName,
            )
        )

    fun findAll() = userRepository.findAll()

    fun activate(id: String) = userRepository.save(getById(id).also { it.activate() })

    fun deactivate(id: String) = userRepository.save(getById(id).also { it.deactivate() })

    fun delete(id: String) = userRepository.save(getById(id).also { it.deletedAt = Instant.now() })
}