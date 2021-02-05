package com.example.database

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.datetime

val tables = arrayOf<Table>(
    Aircrafts,
    Inspections,
    Tasks,
    Operations,
    Projects,
    Models,
)

object Models : Table() {
    data class Model(val name: String)

    val name = text("name").uniqueIndex()
    override val primaryKey: PrimaryKey = PrimaryKey(name, name = "PK_Model")

}

object Aircrafts : Table() {

    val id = integer("id").autoIncrement()
    val name = text("name")
    val model = text("model") references Models.name
    val msn = text("msn")

    /// aircraft egyption number
    val aen = text("aen")
    val imageUrl = text("image_url")
    val flyingHours = integer("flying_hours")
    val calenderDate = datetime("calender_date")

    override val primaryKey = PrimaryKey(Inspections.id, name = "PK_Aircraft")
}

object Inspections : Table() {
    val id = integer("id").autoIncrement()
    val name = text("name")
    val aircraftModel = text("aircraft_model") references Models.name
//    val durationInDays = integer("duration_in_days")


    override val primaryKey = PrimaryKey(id, name = "PK_Inspection")

}


object Tasks : Table() {
    val id = integer("id").autoIncrement()
    val operationNumber = text("operation_number")
    val title = text("title")
    val description = text("description")
    val duration = integer("duration")
    val isQARequired = bool("is_qa_required")
    val startDate = datetime("start_date")
    val department = text("department")
    val type = enumerationByName("type", Type.values().size, Type::class)

    val aircraftModel = text("aircraft_model") references Models.name
    override val primaryKey = PrimaryKey(id, name = "PK_Task")

    enum class Type {
        Standard, OverAndAbove,
    }
}

object Operations : Table() {

    val id = integer("id").autoIncrement()
    val taskId = integer("task_id") references Tasks.id
    val operationState = enumerationByName("operation_state", State.values().size, State::class)

    override val primaryKey = PrimaryKey(id, name = "PK_Operation")

    enum class State {
        NotStarted, Working, Pending, Idle, Done
    }

}

object Projects : Table() {
    val id = integer("id").autoIncrement()

    val aircraftId = (integer("aircraft_id") references Aircrafts.id)
    val inspectionId = (integer("inspection_id") references Inspections.id)

    val startDate = datetime("start_date")
    val endDate = datetime("end_date").nullable()
    val durationInDays = integer("duration_in_days")
    val projectState = text("project_state").default("Not Started")

    override val primaryKey = PrimaryKey(id, name = "PK_Project")


}