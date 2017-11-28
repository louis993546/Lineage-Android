package io.github.louistsaitszho.lineage.services

import android.os.Environment
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import io.github.louistsaitszho.lineage.SystemConfig
import io.github.louistsaitszho.lineage.model.Cancelable
import io.github.louistsaitszho.lineage.model.DataCenterImpl
import io.github.louistsaitszho.lineage.model.DataListener
import io.github.louistsaitszho.lineage.model.Video
import java.io.File

/**
 * Check if there is any video that needs to be download
 * Created by Louis on 28.11.17.
 */
class VideosUpdateService: JobService() {
    private val apiRequestSet = mutableListOf<Cancelable?>()

    override fun onStartJob(job: JobParameters): Boolean {
        //1. ask locally for module id(s) that needs update
        val dataCenter = DataCenterImpl(this)
        val cancelable = dataCenter.getNeedsDownloadModulesId(object : DataListener<List<String>> {
            override fun onSuccess(source: Int, result: List<String>?) {
                if (result?.size == 0) {
                    jobFinished(job, false)
                } else {
                    //2. ask api for each module(s)
                    result?.forEach {
                        dataCenter.getVideos(it, object : DataListener<MutableList<Video>> {
                            override fun onSuccess(source: Int, result: MutableList<Video>?) {
                                //todo

                                //3. get list of videos on device right now on this module
                                val downloadFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                val lineageFolder = File(downloadFolder, "${SystemConfig.downloadFolderName}/")
                                val moduleFolder = File(lineageFolder, "$it/")
                                val files = moduleFolder.list()

                                //4. compare and come up with a list of videos that needs to be download


                                //5. ask DownloadManager to download the content accordingly
//                                val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//                                downloadManager.enqueue(
//                                        DownloadManager.Request(null)
//                                                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)   //todo check with garima
//                                                .setAllowedOverRoaming(false)
//                                                .setTitle("Downloading something (Hard code)")
//                                                .setDescription("Insert title of video here")
//                                                .setVisibleInDownloadsUi(true)
//                                                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
//                                )
                            }

                            override fun onFailure(error: Throwable) {
                                //todo
                                //1) if sth about access token, show notification to ask user to sign in again
                                //2) if just 500, retry later + log to somewhere
                                //3) other failure case?
                            }
                        })
                    }
                }
            }

            override fun onFailure(error: Throwable) {
                //todo schedule this task to resume later
            }

        })
        apiRequestSet.add(cancelable)
        return true //so that all the callback above still works
    }

    override fun onStopJob(job: JobParameters?): Boolean {
        //cancel unfinished job if necessary
        apiRequestSet.forEach {
            it?.cancelNow()
        }
        return true //the job is done
    }
}