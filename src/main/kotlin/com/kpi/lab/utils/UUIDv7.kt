package com.kpi.lab.utils

import com.fasterxml.uuid.Generators

object UUIDv7 {
    fun generate() = Generators.timeBasedEpochGenerator().generate()
}