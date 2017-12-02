package io.github.louistsaitszho.lineage.services

import android.Manifest
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import io.github.louistsaitszho.lineage.model.*

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
                                result?.forEach {
                                    val videoDownloader = VideoDownloader(it)
                                    if (!videoDownloader.isVideoAvailableLocally()) {
                                        val permissionCheck = ContextCompat.checkSelfPermission(this@VideosUpdateService, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                                            videoDownloader.downloadVideoNow(this@VideosUpdateService)
                                        } else {
                                            //todo ask for permission, and after that resume download/schedule another video check
                                        }
                                    }
                                }
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