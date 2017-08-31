package io.github.louistsaitszho.lineage.model

import io.github.louistsaitszho.lineage.ServerConfig
import io.github.louistsaitszho.lineage.attributes.VideoAttribute
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by louistsai on 31.08.17.
 */
class LineageApiWrapperImpl : LineageApiWrapper {
    val apiClient : LineageApiInterface

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(ServerConfig.apiHost)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        apiClient = retrofit.create(LineageApiInterface::class.java)
    }

    override fun getVideo(): Call<List<Data<VideoAttribute>>> {
        return apiClient.fetchVideos()
    }
}