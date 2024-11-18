package com.example.model.update

import com.example.model.registry.GameStatus
import kotlinx.serialization.Serializable

@Serializable
data class UpdateGameStatus(
    val endDate: String? = null,
    val status: GameStatus,
    val timeSpent: Float? = null,
)
