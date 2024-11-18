package com.example.plugins

import com.example.model.registry.GameRegistry
import com.example.model.update.UpdateGameStatus
import com.example.repository.GameRegistryRepository
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("/registry") {
            get("/") {
                val registries = GameRegistryRepository.allRegistries()
                call.respond(registries)
            }
            get("/byName/{name}") {
                val name = call.parameters["name"]
                if (name == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                val registry = GameRegistryRepository.registryByName(name)
                registry?.let {
                    call.respond(registry)
                } ?: call.respond(HttpStatusCode.NotFound)
            }
            get("/{id}") {
                val id = call.parameters["id"]
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                val registry = GameRegistryRepository.registryById(id)
                registry?.let {
                    call.respond(registry)
                } ?: call.respond(HttpStatusCode.NotFound)
            }
            get("/byStatus/{status}") {
                val status = call.parameters["status"]
                if (status == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                val registry = GameRegistryRepository.registriesByStatus(status)
                call.respond(registry)
            }
            post("/") {
                try {
                    val registry = call.receive<GameRegistry>()
                    GameRegistryRepository.addRegistry(registry)
                    call.respond(HttpStatusCode.NoContent)
                } catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
            delete("/{registryId}") {
                val id = call.parameters["registryId"]
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                if (GameRegistryRepository.removeRegistryById(id)) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
            put("/{id}") {
                val id = call.parameters["id"]
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@put
                }

                try {
                    val updateGameStatus = call.receive<UpdateGameStatus>()
                    GameRegistryRepository.updateGameRegistry(id = id, updateGameStatus = updateGameStatus)
                    call.respond(HttpStatusCode.NoContent)
                } catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
    }
}
