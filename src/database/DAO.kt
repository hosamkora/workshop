package com.example.database

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime


object AircraftDAO {

    fun insertAircraft(aircraft: Aircraft) {
        transaction {
            Aircrafts.insert {
                it[name] = aircraft.name
                it[model] = aircraft.model
                it[msn] = aircraft.msn
                it[aen] = aircraft.aen
                it[imageUrl] = aircraft.imageUrl
                it[flyingHours] = aircraft.flyingHours
                it[calenderDate] = aircraft.calenderDate
            }
        }
    }

    fun getAllAircrafts(): List<Aircraft> {
        var aircrafts = listOf<Aircraft>()
        transaction {

            aircrafts = Aircrafts.selectAll().map {
                Aircraft(
                    id = it[Aircrafts.id],
                    name = it[Aircrafts.name],
                    model = it[Aircrafts.model],
                    msn = it[Aircrafts.msn],
                    aen = it[Aircrafts.aen],
                    imageUrl = it[Aircrafts.imageUrl],
                    flyingHours = it[Aircrafts.flyingHours],
                    calenderDate = it[Aircrafts.calenderDate],
                )
            }
        }
        return aircrafts
    }

    fun getAllProjects(): List<Project> {
        var projects = listOf<Project>()
        transaction {
            projects = Projects.selectAll().map { project ->
                Project(
                    id = project[Projects.id],
                    startDate = project[Projects.startDate],
                    endDate = project[Projects.endDate],
                    aircraft = getAircraftWithId(project[Projects.aircraftId]),
                    inspection = getInspectionWithId(project[Projects.inspectionId]),
                    durationInDays = project[Projects.durationInDays],
                    state = project[Projects.state]
                )
            }
        }
        return projects
    }


    fun insertProject(project: Project) {
        transaction {
            Projects.insert {
                it[startDate] = project.startDate
                it[endDate] = project.endDate
                it[aircraftId] = project.aircraft.id
                it[inspectionId] = project.inspection.id
                it[durationInDays] = project.durationInDays
            }
        }

    }

    fun getInspectionsForModel(model: String): List<Inspection> {
        var inspections = listOf<Inspection>()
        transaction {
            inspections = Inspections.select { Inspections.aircraftModel eq model }.map { inspection ->
                Inspection(
                    id = inspection[Inspections.id],
                    name = inspection[Inspections.name],
                    model = inspection[Inspections.aircraftModel],
                )
            }
        }
        return inspections
    }

    private fun getInspectionWithId(id: Int) =
        Inspections.select { Inspections.id eq id }
            .map { inspection ->
                Inspection(
                    id = inspection[Inspections.id],
                    name = inspection[Inspections.name],
                    model = inspection[Inspections.aircraftModel],
                )
            }.first()

    private fun getAircraftWithId(id: Int) =
        Aircrafts.select { Aircrafts.id eq id }.map { aircraft ->
            Aircraft(
                id = aircraft[Aircrafts.id],
                name = aircraft[Aircrafts.name],
                model = aircraft[Aircrafts.model],
                msn = aircraft[Aircrafts.msn],
                aen = aircraft[Aircrafts.aen],
                imageUrl = aircraft[Aircrafts.imageUrl],
                flyingHours = aircraft[Aircrafts.flyingHours],
                calenderDate = aircraft[Aircrafts.calenderDate],
            )
        }.first()
}