package io.github.louistsaitszho.lineage.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import io.github.louistsaitszho.lineage.model.poko.Data
import io.github.louistsaitszho.lineage.model.poko.attributes.ModuleAttribute

/**
 * Created by louis on 09.11.17.
 */
@Entity(tableName = "modules")
data class Module(
        @PrimaryKey
        val id: String,
        @ColumnInfo(name = "name")
        val name: String,
        @ColumnInfo(name = "auto_download")
        var needsAutoDownload: Boolean
) {
    constructor(apiResponse: Data<ModuleAttribute>) : this(apiResponse.id, apiResponse.attributes.name, true)   //by default all modules should be auto-download
}