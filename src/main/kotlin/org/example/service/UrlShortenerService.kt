package org.example.service

import org.example.model.UrlMapping
import org.example.repository.UrlMappingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UrlShortenerService(@Autowired val repository: UrlMappingRepository) {

    fun shortenUrl(originalUrl: String): String {
        val existing = repository.findByOriginalUrl(originalUrl)
        if (existing != null) return existing.shortCode

        val newEntry = UrlMapping(originalUrl = originalUrl)
        return repository.save(newEntry).shortCode
    }

    fun getOriginalUrl(shortCode: String): String? {
        return repository.findByShortCode(shortCode)?.originalUrl
    }
}
