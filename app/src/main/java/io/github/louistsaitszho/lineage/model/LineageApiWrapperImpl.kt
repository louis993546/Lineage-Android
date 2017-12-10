package io.github.louistsaitszho.lineage.model

import io.github.louistsaitszho.lineage.SystemConfig
import io.github.louistsaitszho.lineage.model.poko.JsonApiResponse
import io.github.louistsaitszho.lineage.model.poko.attributes.ModuleAttribute
import io.github.louistsaitszho.lineage.model.poko.attributes.SchoolAttribute
import io.github.louistsaitszho.lineage.model.poko.attributes.VideoAttribute
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
     * implementation of get videos in a particular module: by asking retrofit to get it
     */
    override fun getVideo(moduleId: String, accessToken: String): Call<JsonApiResponse<VideoAttribute>> {
        return apiClient.fetchVideos(accessToken, moduleId)
    }

    /**
     * implementation of get modules: by asking retrofit to get it
     */
    override fun getModules(accessToken: String): Call<JsonApiResponse<ModuleAttribute>> {
        return apiClient.fetchModules(accessToken)
    }

    /**
     * sign in (it should be pretty obvious what it does)
     */
    override fun signIn(schoolCode: String): Call<JsonApiResponse<SchoolAttribute>> {
        return apiClient.signIn(schoolCode)
    }
}
