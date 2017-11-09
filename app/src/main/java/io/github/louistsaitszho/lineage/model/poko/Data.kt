package io.github.louistsaitszho.lineage.model.poko

/**
 * According to JSON.API, a data object will wrap around every content (so that if in the future you
 * need to have 1 API returns multiple type it will be less painful)
 * Created by Louis Tsai on 31.08.17.
 */
data class Data<out Attribute>(
        val id : String,
        val type : String,
        val attributes : Attribute
)