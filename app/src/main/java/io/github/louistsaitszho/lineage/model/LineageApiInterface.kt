package io.github.louistsaitszho.lineage.model

import io.github.louistsaitszho.lineage.attributes.VideoAttribute
import retrofit2.Call

/**
 * Created by louistsai on 31.08.17.
 */
interface LineageApiInterface {
    fun fetchVideos() : Call<List<Data<VideoAttribute>>>
}