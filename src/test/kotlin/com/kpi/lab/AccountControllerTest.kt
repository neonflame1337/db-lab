package com.kpi.lab

import com.kpi.lab.persistence.postgres.entity.UserEntity
import com.kpi.lab.persistence.postgres.repository.AccountRepository
import com.kpi.lab.persistence.postgres.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(
    classes = [OnlineBankApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class AccountControllerTest {
    @Autowired private lateinit var webClient: WebTestClient
    @Autowired private lateinit var userRepository: UserRepository
    @Autowired private lateinit var accountRepository: AccountRepository

    @AfterEach
    fun deleteData() {
        accountRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    fun createAccount() {
        val user = saveUserSample("Test", "User", true)

        webClient.post()
            .uri("api/v1/account/create")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                """
                    {
                      "user_id": "${user.id}",
                      "name": "test-acc-1",
                      "balance": 30000
                    }
                """.trimIndent()
            ).exchange()
            .expectStatus().isOk

        val result = accountRepository.findAll()
        assertThat(result).hasSize(1)
        assertThat(result.first().balance).isEqualTo(30000)
        assertThat(result.first().publicId).isEqualTo("test-acc-1")
    }

    @Test
    fun deposit() {
        val user = saveUserSample("Test", "User", true)

        webClient.post()
            .uri("api/v1/account/create")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                """
                    {
                      "user_id": "${user.id}",
                      "name": "test-acc-1",
                      "balance": 30000
                    }
                """.trimIndent()
            ).exchange()
            .expectStatus().isOk

        var account = accountRepository.findAll().first()

        webClient.post()
            .uri("api/v1/account/${account.id}/deposit")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                """
                    {
                      "amount": 20000
                    }
                """.trimIndent()
            ).exchange()
            .expectStatus().isOk

        account = accountRepository.findAll().first()
        assertThat(account.balance).isEqualTo(50000)
    }

    @Test
    fun withdraw() {
        val user = saveUserSample("Test", "User", true)

        webClient.post()
            .uri("api/v1/account/create")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                """
                    {
                      "user_id": "${user.id}",
                      "name": "test-acc-1",
                      "balance": 30000
                    }
                """.trimIndent()
            ).exchange()
            .expectStatus().isOk

        var account = accountRepository.findAll().first()

        webClient.post()
            .uri("api/v1/account/${account.id}/withdraw")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                """
                    {
                      "amount": 20000
                    }
                """.trimIndent()
            ).exchange()
            .expectStatus().isOk

        account = accountRepository.findAll().first()
        assertThat(account.balance).isEqualTo(10000)
    }

    private fun saveUserSample(firstName: String, lastName: String, active: Boolean = false) =
        userRepository.save(
            UserEntity(
                firstName = firstName,
                lastName = lastName,
            ).apply { if (active) activate() }
        )
}