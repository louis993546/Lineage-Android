package io.github.louistsaitszho.lineage.model.dao

import android.arch.persistence.room.*
import io.github.louistsaitszho.lineage.model.Video

/**
 * Created by louis on 02.12.17.
 */
@Dao
interface VideoDao {
    @Query("SELECT * FROM videos WHERE module_id = :moduleId")
    fun getAllVideosInModule(moduleId: String): List<Video>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertVideos(videos: List<Video>)

    @Delete
    fun deleteVideos(video: List<Video>)
}