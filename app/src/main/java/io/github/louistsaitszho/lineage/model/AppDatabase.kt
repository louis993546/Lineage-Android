package io.github.louistsaitszho.lineage.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import io.github.louistsaitszho.lineage.model.dao.ModuleDao
import io.github.louistsaitszho.lineage.model.dao.VideoDao

/**
 * Created by louis on 02.12.17.
 */
@Database(version = 1, entities = [(Module::class), (Video::class)])
abstract class AppDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao
    abstract fun moduleDao(): ModuleDao
}