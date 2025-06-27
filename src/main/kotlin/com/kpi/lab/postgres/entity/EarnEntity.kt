package com.kpi.lab.persistence.postgres.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigInteger

@Entity
@Table(name = "employed")
class EarnEntity(
    @Id
    val id: BigInteger,
    val sex: String,
    var race: String,
    var ethnicOrigin: String,
    var age: String,
    var year: Int,
    var quarter: Int,
    var nPersons: Int,
    var medialWeeklyEarn: Int,
)