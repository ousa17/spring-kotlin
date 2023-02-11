package com.oussamabw.shopee

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank

@Entity
data class Article(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @get: NotBlank
    val title: String = "",

    @get: NotBlank
    val description: String = "",

    @get: NotBlank
    val photoUrl: String = "",

    val unitPrice: Double = 0.0,

    val availableStock: Int = 0


)