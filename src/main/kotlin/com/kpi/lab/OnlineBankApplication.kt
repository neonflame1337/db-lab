package com.kpi.lab

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OnlineBankApplication

fun main(args: Array<String>) {
	runApplication<OnlineBankApplication>(*args)
}
