package org.example.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "url_mapping")
data class UrlMapping(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val shortCode: String = UUID.randomUUID().toString().substring(0, 6),

    @Column(nullable = false)
    val originalUrl: String
)
