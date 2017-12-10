package io.github.louistsaitszho.lineage.attributes

/**
 * TODO make thumbnail url non-nullable when i fixed the api
 * Created by louistsai on 31.08.17.
 */
data class VideoAttribute(
        val thumbnailUrl: String?,
        val host : String,
        val link : String,
        val title : String,
        val module_id: String
)