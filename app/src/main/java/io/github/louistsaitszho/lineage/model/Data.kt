package io.github.louistsaitszho.lineage.model

/**
 * According to JSON.API, a data object will wrap around everything first
 * Created by louistsai on 31.08.17.
 */
data class Data<out T>(val id : String, val type : String, val attributes : T)