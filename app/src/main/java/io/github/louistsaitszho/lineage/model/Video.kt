package io.github.louistsaitszho.lineage.model

import android.net.Uri
import io.github.louistsaitszho.lineage.attributes.VideoAttribute
import io.github.louistsaitszho.lineage.model.poko.Data

/**
 * TODO should those URLs be URL/URI instead?
 * TODO Add host (YouTube, Google Drive, Dropbox, etc) as int/string (that act as enum but not enum)
 * Created by louistsai on 21.08.17.
 */
data class Video(val id : String, val url : String, val thumbnailUrl : String?, val title: String, val moduleId: String) {
    /**
     * Takes what API response and gives you a simple Video object
     */
    constructor(apiResponse: Data<VideoAttribute>) : this(apiResponse.id, apiResponse.attributes.link, apiResponse.attributes.thumbnailUrl, apiResponse.attributes.title, apiResponse.attributes.module_id)

    /**
     * Turns url from String to Uri
     */
    fun getVideoUri(): Uri = Uri.parse(url)
}