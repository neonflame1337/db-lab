package com.kpi.lab

import com.kpi.lab.persistence.postgres.entity.UserEntity
import com.kpi.lab.persistence.postgres.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
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
class UserControllerTest {
    @Autowired private lateinit var webClient: WebTestClient
    @Autowired private lateinit var userRepository: UserRepository

    @BeforeEach
    fun deleteData() {
        userRepository.deleteAll()
    }

    @Test
    fun getListTest() {
        val testSamples = mutableListOf<UserEntity>()
        testSamples.add(saveUserSample("Test", "One"))
        testSamples.add(saveUserSample("Test", "Two"))

        webClient.get()
            .uri("api/v1/user/list")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .json(
                """
                   [
                     {
                       "id": "${testSamples.find { it.lastName == "One" }!!.id}",
                       "first_name": "Test",
                       "last_name": "One",
                       "is_active": false
                     },
                     {
                       "id": "${testSamples.find { it.lastName == "Two" }!!.id}",
                       "first_name": "Test",
                       "last_name": "Two",
                       "is_active": false
                     }
                   ]
                """.trimIndent()
            )
    }

    @Test
    fun getUserTest() {
        val userId = saveUserSample("Test", "User").id

        webClient.get()
            .uri("api/v1/user/$userId")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .json(
                """
                   {
                       "id": "$userId",
                       "first_name": "Test",
                       "last_name": "User",
                       "is_active": false
                     }
                """.trimIndent()
            )
    }

    @Test
    fun createUserTest() {
        webClient.post()
            .uri("api/v1/user/create")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                """
                    {
                       "first_name": "Test",
                       "last_name": "User"
                   }
            """.trimIndent()
            )
            .exchange()
            .expectStatus().isOk

        val result = userRepository.findAll()

        assertThat(result).hasSize(1)
        assertThat(result.first().firstName).isEqualTo("Test")
        assertThat(result.first().lastName).isEqualTo("User")
    }

    @Test
    fun activateUserTest() {
        val userId = saveUserSample("Test", "User").id

        webClient.get()
            .uri("api/v1/user/$userId/activate")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .json(
                """
                   {
                       "id": "$userId",
                       "first_name": "Test",
                       "last_name": "User",
                       "is_active": true
                     }
                """.trimIndent()
            )
    }

    @Test
    fun deactivateUserTest() {
        val userId = saveUserSample("Test", "User", true).id

        webClient.get()
            .uri("api/v1/user/$userId/deactivate")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .json(
                """
                   {
                       "id": "$userId",
                       "first_name": "Test",
                       "last_name": "User",
                       "is_active": false
                     }
                """.trimIndent()
            )
    }

    @Test
    fun deleteUserTest() {
        val userId = saveUserSample("Test", "User").id

        webClient.delete()
            .uri("api/v1/user/$userId")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .json(
                """
                   {
                       "id": "$userId",
                       "first_name": "Test",
                       "last_name": "User",
                       "is_active": false
                     }
                """.trimIndent()
            )

        assertThat(userRepository.findAll().first().deletedAt).isNotNull()

        webClient.get()
            .uri("api/v1/user/list")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .json(
                """
                   [
                   ]
                """.trimIndent()
            )
    }

    private fun saveUserSample(firstName: String, lastName: String, active: Boolean = false) =
        userRepository.save(
            UserEntity(
                firstName = firstName,
                lastName = lastName,
            ).apply { if (active) activate() }
        )
}