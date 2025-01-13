package com.kpi.lab.exception

import jakarta.annotation.Priority
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@Priority(value = 3)
@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(EntityNotFoundException::class)
    fun entityNotFoundException(e: EntityNotFoundException): ResponseEntity<*> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            mapOf(
                "error" to "entity_not_found",
                "error_message" to e.message,
            )
        )

    @ExceptionHandler(InvalidOperationException::class)
    fun invalidOperationException(e: InvalidOperationException): ResponseEntity<*> =
        ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(
            mapOf(
                "error" to "invalid_operation",
                "error_message" to e.message,
            )
        )
}