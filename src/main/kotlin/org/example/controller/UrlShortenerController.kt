package org.example.controller

import org.example.service.UrlShortenerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.InetAddress
import java.net.URI

@RestController
class UrlShortenerController(@Autowired val service: UrlShortenerService) {

    @PostMapping("/shorten")
    fun shorten(@RequestBody body: Map<String, String>): ResponseEntity<Map<String, String>> {
        val originalUrl = body["url"] ?: return ResponseEntity.badRequest().body(mapOf("error" to "Missing 'url'"))
        val shortCode = service.shortenUrl(originalUrl)
        val host = InetAddress.getLocalHost().hostAddress
        val port = ServletUriComponentsBuilder.fromCurrentRequestUri().build().port ?: 8080
        val baseUrl = "http://$host:$port/$shortCode"

        return ResponseEntity.ok(mapOf("short_url" to baseUrl))

    }

    @GetMapping("/{shortCode}")
    fun redirect(@PathVariable shortCode: String): ResponseEntity<Void> {
        val originalUrl = service.getOriginalUrl(shortCode)
            ?: return ResponseEntity.notFound().build()

        return try {
            ResponseEntity.status(302).location(URI.create(originalUrl)).build()
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }

    }
}
