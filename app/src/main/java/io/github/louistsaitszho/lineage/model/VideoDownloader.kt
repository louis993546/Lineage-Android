package io.github.louistsaitszho.lineage.model

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import android.support.annotation.RequiresPermission
import io.github.louistsaitszho.lineage.R
import io.github.louistsaitszho.lineage.SystemConfig
import java.io.File

/**
 * If you want to download a video this is the right place for you
 * Created by louis on 30.11.17.
 */
class VideoDownloader(val video: Video) {

    /**
     * generate path and file name of a video
     * Logic:
     * 1) Everything in the folder specified in SystemConfig
     * 2) Each module is a folder (folder name = module id)
     * 3) file name = video id + .mp4 (TODO this is straight up a bad idea but that's all i can do for now)
     */
    fun generateVideoFilePath() = "/${SystemConfig.downloadFolderName}/${video.moduleId}/${video.id}.mp4"

    /**
     *
     */
    fun generateAbsoluteVideoLocalFilePath() =
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), generateVideoFilePath())

    fun isVideoAvailableLocally(): Boolean {
        val videoFile = generateAbsoluteVideoLocalFilePath()
        return videoFile.exists() && videoFile.isFile
    }

    /**
     * method to ask DownloadManager to download video
     */
    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun downloadVideoNow(context: Context): Long {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        //todo try catch IAE: if uri is not http/https
        val id = downloadManager.enqueue(
                DownloadManager.Request(video.getVideoUri())
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, generateVideoFilePath())
                        .setTitle(context.getString(R.string.title_downloading_eclipses))
                        .setDescription(video.title)
                        .setVisibleInDownloadsUi(true)
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        )
        return id
    }
    //not sure: a wrapper around broadcast receiver for download complete? (maybe + failure?)
}