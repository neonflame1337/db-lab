package com.kpi.lab.persistence.postgres.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigInteger

@Entity
@Table(name = "employed")
class EmployedEntity(
    @Id
    val id: BigInteger,
    val industry: String,
    var majorOccupation: String,
    var minorOccupation: String,
    var raceGender: String,
    var industryTotal: Double,
    @Column(name = "employ_n")
    var employN: Double,
    var year: Int,
)