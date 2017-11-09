package io.github.louistsaitszho.lineage.model

import io.github.louistsaitszho.lineage.attributes.VideoAttribute
import io.github.louistsaitszho.lineage.model.poko.Data

/**
 * TODO should those URLs be URL/URI instead?
 * TODO Add host (YouTube, Google Drive, Dropbox, etc) as int/string (that act as enum but not enum)
 * Created by louistsai on 21.08.17.
 */
data class Video(val id : String, val url : String, val thumbnailUrl : String) {
    /**
     * Takes what API response and gives you a simple Video object
     */
    constructor(apiResponse: Data<VideoAttribute>) : this(apiResponse.id, apiResponse.attributes.url, apiResponse.attributes.thumbnailUrl)
}