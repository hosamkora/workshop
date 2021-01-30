package com.example.database

import io.ktor.application.Application
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.initDatabase() {
    val databaseUrl = this.environment.config.propertyOrNull("ktor.deployment.full_database_url")?.getString() ?: ""
    val driver = "org.postgresql.Driver"
    Database.connect(url = databaseUrl, driver = driver)
    transaction {
    }
    transaction {
        SchemaUtils.createMissingTablesAndColumns(*tables)
    }

}
