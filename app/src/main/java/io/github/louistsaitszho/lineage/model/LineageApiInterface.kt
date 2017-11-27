package io.github.louistsaitszho.lineage.model

import io.github.louistsaitszho.lineage.attributes.VideoAttribute
import io.github.louistsaitszho.lineage.model.attributes.ModuleAttribute
import io.github.louistsaitszho.lineage.model.poko.JsonApiResponse
import io.github.louistsaitszho.lineage.model.poko.attributes.SchoolAttribute
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * This is basically the specification of the API
 *
 * Created by Louis on 31.08.17.
 */
interface LineageApiInterface {
    /**
     * @param accessToken is needed to get YOUR list of videos of this module
     * @param moduleId
     */
    @GET("videos")
    fun fetchVideos(
            @Header("access_token") accessToken: String,
            @Query("module_id") moduleId: String
    ): Call<JsonApiResponse<VideoAttribute>>

    /**
     * @param accessToken is needed to get YOUR list of modules
     */
    @GET("modules")
    fun fetchModules(
            @Header("access_token") accessToken: String
    ): Call<JsonApiResponse<ModuleAttribute>>

    @GET("schools")
    fun signIn(
            @Header("access_token") accessToken: String
    ): Call<JsonApiResponse<SchoolAttribute>>
}