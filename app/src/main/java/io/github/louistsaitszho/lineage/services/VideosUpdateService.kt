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
class VideosUpdateService : JobService() {
    private val apiRequestSet = mutableListOf<Cancelable?>()

    override fun onStartJob(job: JobParameters): Boolean {
        //1. ask locally for module id(s) that needs update
        val dataCenter = DataCenterImpl(this)

        val cancelable = dataCenter.getModules(object : DataListener<MutableList<Module>> {
            override fun onSuccess(source: Int, result: MutableList<Module>?) {
                if (source == DataListener.SOURCE_LOCAL) {
                    if (result == null || result.size == 0) {
                        //nothing needs to be download
                        jobFinished(job, false)
                    } else {
                        result.forEach { module ->
                            if (module.needsAutoDownload) {
                                val anotherCancelable = dataCenter.getVideos(module.id, object : DataListener<MutableList<Video>> {
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
                                apiRequestSet.add(anotherCancelable)
                            } else {
                                //nothing special for this module, skip
                            }
                        }
                        //no need to call jobfinished here, the callbacks above will handle it
                    }
                    //todo scroll through each module, check if they needs download
                }
            }

            override fun onFailure(error: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
        apiRequestSet.add(cancelable)
        return true //so that all the callback above still works
    }

    override fun onStopJob(job: JobParameters?): Boolean {
        //cancel all unfinished jobs
        apiRequestSet.forEach {
            it?.cancelNow()
        }
        return true //the job is done
    }
}