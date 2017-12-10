package io.github.louistsaitszho.lineage.model

import io.github.louistsaitszho.lineage.SystemConfig
import io.github.louistsaitszho.lineage.attributes.VideoAttribute
import io.github.louistsaitszho.lineage.model.attributes.ModuleAttribute
import io.github.louistsaitszho.lineage.model.poko.JsonApiResponse
import io.github.louistsaitszho.lineage.model.poko.attributes.SchoolAttribute
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Implementation of LineageApiWrapper: actually fetch the content
 * Why not just directly use LineageApiInterface?
 * - So that I can skip some boiler plate for you, e.g. fetching access token
 * Created by Louis Tsai on 31.08.17.
 */
class LineageApiWrapperImpl : LineageApiWrapper {
    private val apiClient: LineageApiInterface

    /**
     * Constructor in kotlin looks weird
     */
    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(SystemConfig.apiHost)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        apiClient = retrofit.create(LineageApiInterface::class.java)
    }

    /**
     * TODO hard code access token
     */
    override fun getVideo(moduleId: String): Call<JsonApiResponse<VideoAttribute>> {
        return apiClient.fetchVideos("123", moduleId)
    }

    /**
     * TODO hard code access token
     */
    override fun getModules(): Call<JsonApiResponse<ModuleAttribute>> {
        return apiClient.fetchModules("123")
    }

    /**
     * TODO hard code access token
     */
    override fun signIn(schoolCode: String): Call<JsonApiResponse<SchoolAttribute>> {
        return apiClient.signIn(schoolCode)
    }
}
