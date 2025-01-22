package com.kpi.lab.api.controller

import com.kpi.lab.service.ReportService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("api/v1/reports")
class ReportController(
    private val reportService: ReportService
) {
    @GetMapping("/user_detail_report/{userId}")
    fun getDetailedReport(@PathVariable userId: UUID) = reportService.getUserDetailReport(userId)

    @GetMapping("/user_summary_report/{userId}")
    fun getSummaryReport(@PathVariable userId: UUID) = reportService.getUserSummaryReport(userId)
}