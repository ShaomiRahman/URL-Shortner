package org.example.repository

import org.example.model.UrlMapping
import org.springframework.data.jpa.repository.JpaRepository

interface UrlMappingRepository : JpaRepository<UrlMapping, Long> {
    fun findByShortCode(shortCode: String): UrlMapping?
    fun findByOriginalUrl(originalUrl: String): UrlMapping?
}
