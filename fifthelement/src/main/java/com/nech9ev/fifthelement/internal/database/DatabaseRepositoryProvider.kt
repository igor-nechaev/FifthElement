package com.nech9ev.fifthelement.internal.database

import android.content.Context


internal object DatabaseRepositoryProvider {

    private var repository: DatabaseRepository? = null

    fun provide(applicationContext: Context): DatabaseRepository {
        return repository ?: initialize(applicationContext)
    }

    @Synchronized //todo lock on any
    private fun initialize(applicationContext: Context): DatabaseRepository {
        val database = FifthElementDatabase.createDatabase(applicationContext)
        return DatabaseRepository(
            transactionsDao = database.transactionsDao(),
            deviceConfigDao = database.deviceDao(),
        ).also {
            repository = it
        }
    }
}
