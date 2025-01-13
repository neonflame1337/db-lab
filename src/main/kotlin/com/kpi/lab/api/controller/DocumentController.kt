package com.kpi.lab.api.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/document")
class DocumentController {
    @PostMapping("/add")
    fun add() {}

    @DeleteMapping("/{documentId}")
    fun deleteDocument(@PathVariable documentId: String) {}
}