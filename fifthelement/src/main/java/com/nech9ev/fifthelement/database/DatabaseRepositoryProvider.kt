package com.nech9ev.fifthelement.database

import android.content.Context


object DatabaseRepositoryProvider {

    private var repository: DatabaseRepository? = null

    fun provide(): DatabaseRepository {
        return repository!!
    }

    fun initialize(applicationContext: Context) {
        if (repository != null) return

        val database = FifthElementDatabase.createDatabase(applicationContext)
        repository = DatabaseRepository(
            transactionsDao = database.transactionsDao(),
            deviceConfigDao = database.deviceDao(),
        )
    }
}
