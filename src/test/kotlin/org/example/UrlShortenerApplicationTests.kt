package org.example

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UrlShortenerApplicationTests {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun testShortenAndRedirect() {
        val originalUrl = "https://example.com"

        val shortenResponse: ResponseEntity<Map<*, *>> = restTemplate.postForEntity(
            "http://localhost:$port/shorten",
            mapOf("url" to originalUrl),
            Map::class.java
        )

        assertEquals(HttpStatus.OK, shortenResponse.statusCode)
        val shortUrl = shortenResponse.body?.get("short_url") as String

        val shortCode = shortUrl.split("/").last()
        assertTrue(shortUrl.endsWith("/$shortCode"))

        val redirectResponse = restTemplate.getForEntity(
            "http://localhost:$port/$shortCode",
            String::class.java
        )

        assertEquals(HttpStatus.FOUND, redirectResponse.statusCode)
        assertEquals(originalUrl, redirectResponse.headers.location.toString())
    }
}
