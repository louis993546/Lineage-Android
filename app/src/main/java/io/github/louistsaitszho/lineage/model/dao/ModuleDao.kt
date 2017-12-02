package io.github.louistsaitszho.lineage.model.dao

import android.arch.persistence.room.*
import io.github.louistsaitszho.lineage.model.Module

/**
 * Created by louis on 02.12.17.
 */
@Dao
interface ModuleDao {
    @Query("SELECT * FROM modules")
    fun getAllModules(): List<Module>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertModules(modules: List<Module>)

    @Delete
    fun deleteModules(modules: List<Module>)
}