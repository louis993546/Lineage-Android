package io.github.louistsaitszho.lineage.model

import io.github.louistsaitszho.lineage.model.poko.JsonApiResponse
import io.github.louistsaitszho.lineage.model.poko.attributes.ModuleAttribute
import io.github.louistsaitszho.lineage.model.poko.attributes.SchoolAttribute
import io.github.louistsaitszho.lineage.model.poko.attributes.VideoAttribute
import retrofit2.Call

/**
 * What is this?
 * > Definition of what you can actually do with the API
 * Why is this an interface? Why not just write the implementation
 * > I think this is like a best practice, since this makes future changes easier (if you want to
 * completely re-write the logic just create a separate class that implement this interface, and you
 * can leave the old one alone)
 * Created by Louis Tsai on 31.08.17.
 */
interface LineageApiWrapper {
    /**
     * Call GET videos
     * @param moduleId is the id of the module you want to access
     * @return a call object (so that you can do whatever you want to it)
     */
    fun getVideo(moduleId: String, accessToken: String): Call<JsonApiResponse<VideoAttribute>>

    /**
     * Call GET modules
     * @return a call object (so that you can do whatever you want to it)
     */
    fun getModules(accessToken: String): Call<JsonApiResponse<ModuleAttribute>>

    /**
     *
     */
    fun signIn(schoolCode: String): Call<JsonApiResponse<SchoolAttribute>>
}