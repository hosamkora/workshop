package com.example.database

import org.joda.time.DateTime

data class Model(val name: String)


data class Aircraft(
    val id: Int,
    val name: String,
    val model: String,
    val msn: String,
    val aen: String,
    val imageUrl: String,
    val flyingHours: Int,
    val calenderDate: DateTime,
)

data class Inspection(
    val id: Int,
    val name: String,
    val model: String,
)

data class Project(
    val id: Int,
    val aircraft: Aircraft,
    val inspection: Inspection,
    val startDate: DateTime,
    val endDate: DateTime?,
    val projectState: String,
    val durationInDays: Int,
)

