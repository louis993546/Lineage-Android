package io.github.louistsaitszho.lineage.services

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import io.github.louistsaitszho.lineage.R
import io.github.louistsaitszho.lineage.model.*
import timber.log.Timber


/**
 * Check if there is any video that needs to be download
 * Created by Louis on 28.11.17.
 */
class VideosUpdateService : JobService() {
    private val apiRequestSet = mutableListOf<Cancelable?>()
    val channelId = "testing"

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
                                                    try {
                                                        videoDownloader.downloadVideoNow(this@VideosUpdateService)
                                                    } catch (e: IllegalArgumentException) {
                                                        Timber.e("Video cannot be download! Check the data right now: '%s'", it.toString())
                                                    }
                                                } else {
                                                    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                                                    //intent: open the settings page of the app
                                                    val intent = Intent()
                                                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                                    val uri = Uri.fromParts("package", packageName, null)
                                                    intent.data = uri
                                                    val pendingIntent = PendingIntent.getActivity(this@VideosUpdateService, 123, intent, PendingIntent.FLAG_UPDATE_CURRENT)

                                                    val builder = NotificationCompat.Builder(this@VideosUpdateService)
                                                            .setSmallIcon(R.drawable.ic_app_logo)
                                                            .setContentTitle("title")
                                                            .setContentText("text")
                                                            .setShowWhen(true)
                                                            .setAutoCancel(true)
                                                            .setContentIntent(pendingIntent)

                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                        builder.setColor(ContextCompat.getColor(this@VideosUpdateService, R.color.primaryColor))
                                                                .setCategory(Notification.CATEGORY_ERROR)
                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                            val channel: NotificationChannel
                                                            channel = generateNotificationChannel()
                                                            notificationManager.createNotificationChannel(channel)
                                                            builder.setChannelId(channelId)
                                                        }
                                                    }

                                                    notificationManager.notify(1234, builder.build())
                                                    jobFinished(job, false)
                                                }
                                            }
                                        }
                                        jobFinished(job, false)
                                    }

                                    override fun onFailure(error: Throwable) {
                                        //todo
                                        //1) if sth about access token, show notification to ask user to sign in again
                                        //2) if just 500, retry later + log to somewhere
                                        //3) other failure case?
                                        jobFinished(job, true)
                                    }

                                })
                                apiRequestSet.add(anotherCancelable)
                            } else {
                                //nothing special for this module, skip
                            }
                        }
                        //no need to call jobfinished here, the callbacks above will handle it
                    }
                }
            }

            override fun onFailure(error: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
        apiRequestSet.add(cancelable)
        return true //so that all the callback above still works
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun generateNotificationChannel(): NotificationChannel {
        val channelName = "Auto download video"
        val channelDescription =  "blah blah blah"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, channelName, importance)
        channel.description = channelDescription
        channel.enableLights(true)
        channel.lightColor = Color.WHITE
        channel.setShowBadge(true)
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        return channel
    }

    override fun onStopJob(job: JobParameters?): Boolean {
        //cancel all unfinished jobs
        apiRequestSet.forEach {
            it?.cancelNow()
        }
        return true //the job is done
    }
}