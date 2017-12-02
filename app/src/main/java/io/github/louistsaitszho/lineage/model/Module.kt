package io.github.louistsaitszho.lineage.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import io.github.louistsaitszho.lineage.model.attributes.ModuleAttribute
import io.github.louistsaitszho.lineage.model.poko.Data

/**
 * Created by louis on 09.11.17.
 */
@Entity(tableName = "modules")
data class Module(
        @PrimaryKey
        val id: String,
        @ColumnInfo(name = "name")
        val name: String
) {
    constructor(apiResponse: Data<ModuleAttribute>) : this(apiResponse.id, apiResponse.attributes.name)
}