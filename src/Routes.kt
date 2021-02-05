package com.example

import com.example.database.*
import com.google.gson.FieldNamingPolicy
import com.google.gson.JsonObject
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*
import io.ktor.routing.routing
import org.joda.time.DateTime

fun Application.routes() {
    install(ContentNegotiation) {
        gson {
            registerTypeAdapter(DateTime::class.java, DateTimeTypeAdapter())
            setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        }
    }
    routing {
        get("aircrafts") {
            val aircrafts = AircraftDAO.getAllAircrafts()
            call.respond(aircrafts)
        }
        get("/") {
            call.respondText("Hello Bro")
        }
        post("/aircraft") {
            val aircraft = call.receive<Aircraft>()
            AircraftDAO.insertAircraft(aircraft)
        }
        get("/projects") {
            val projects = AircraftDAO.getAllProjects()
            call.respond(projects)

        }
        post("/project") {
            val project = call.receive<Project>()
            AircraftDAO.insertProject(project)
        }

        get("/inspections/{model}") {
            val inspections = AircraftDAO.getInspectionsForModel(call.parameters["model"]!!)
            call.respond(inspections)
        }

        get("/aircraft/{id}") {
            val aircraft = AircraftDAO.getAircraftById(call.parameters["id"]!!.toInt())
            call.respond(aircraft!!)
        }

        get("/models") {
            val models = AircraftDAO.getAllModels()
            call.respond(models)
        }

    }
}
