package io.github.louistsaitszho.lineage

/**
 * TODO should those URLs be URL/URI instead?
 * TODO host (YouTube, Google Drive, Dropbox, etc) & should they be enum -> NO enum
 * Created by louistsai on 21.08.17.
 */
data class Video(val id : String, val url : String, val thumbnailUrl : String)