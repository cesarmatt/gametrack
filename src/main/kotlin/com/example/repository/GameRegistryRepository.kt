package com.example.repository

import com.example.model.registry.GameRegistry
import com.example.model.registry.GameStatus
import com.example.model.update.UpdateGameStatus
import java.util.UUID

object GameRegistryRepository {

    private val registries = mutableListOf(
        GameRegistry(
            id = UUID.randomUUID().toString(),
            name = "Baldurs Gate 3",
            startingDate = "10/08/2024",
            endDate = "19/10/2024",
            status = GameStatus.FINISHED,
            timeSpent = 140f,
        )
    )

    fun allRegistries(): List<GameRegistry> = registries

    fun registriesByStatus(status: String) = registries.filter {
        it.status.toString() == status
    }

    fun registryByName(name: String) = registries.find {
        it.name.equals(name, ignoreCase = true)
    }

    fun registryById(id: String) = registries.find { it.id == id }

    fun addRegistry(registry: GameRegistry) {
        if (registryByName(registry.name) != null) {
            throw IllegalStateException("Cannot duplicate game names!")
        }
        registries.add(registry)
    }

    fun removeRegistryById(id: String): Boolean {
        return registries.removeIf { it.id== id }
    }

    fun updateGameRegistry(id: String, updateGameStatus: UpdateGameStatus) {
        registries.find { it.id == id }?.copy(
            status = updateGameStatus.status,
            timeSpent = updateGameStatus.timeSpent,
            endDate = updateGameStatus.endDate,
        )?.let {
            removeRegistryById(id)
            registries.add(it)
        } ?: throw IllegalStateException("Cannot delete non existing game!")
    }
}