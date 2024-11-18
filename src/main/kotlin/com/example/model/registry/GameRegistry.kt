package com.example.model.registry

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class GameRegistry(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val startingDate: String,
    val endDate: String? = null,
    val status: GameStatus,
    val timeSpent: Float? = null,
)