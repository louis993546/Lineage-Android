package io.github.louistsaitszho.lineage.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.net.Uri
import io.github.louistsaitszho.lineage.attributes.VideoAttribute
import io.github.louistsaitszho.lineage.model.poko.Data

/**
 * TODO should those URLs be URL/URI instead?
 * TODO Add host (YouTube, Google Drive, Dropbox, etc) as int/string (that act as enum but not enum)
 * Created by louistsai on 21.08.17.
 */
@Entity(tableName = "videos")
data class Video(
        @PrimaryKey
        val id: String,
        @ColumnInfo(name = "url")
        val url: String,
        @ColumnInfo(name = "thumbnail_url")
        val thumbnailUrl: String?,
        @ColumnInfo(name = "title")
        val title: String,
        @ColumnInfo(name = "module_id")
        val moduleId: String) {
    /**
     * Takes what API response and gives you a simple Video object
     */
    constructor(apiResponse: Data<VideoAttribute>) : this(apiResponse.id, apiResponse.attributes.link, apiResponse.attributes.thumbnailUrl, apiResponse.attributes.title, apiResponse.attributes.module_id)

    /**
     * Turns url from String to Uri
     */
    fun getVideoUri(): Uri = Uri.parse(url)
}