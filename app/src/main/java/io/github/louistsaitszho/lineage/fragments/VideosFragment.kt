package io.github.louistsaitszho.lineage.fragments

import android.Manifest
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.github.louistsaitszho.lineage.OnItemClickListener
import io.github.louistsaitszho.lineage.R
import io.github.louistsaitszho.lineage.RecyclerViewAdapter
import io.github.louistsaitszho.lineage.VerticalDividerItemDecoration
import io.github.louistsaitszho.lineage.activities.MainActivity
import io.github.louistsaitszho.lineage.model.*
import kotlinx.android.synthetic.main.fragment_videos.*
import timber.log.Timber


/**
 * This fragment display a list of videos
 * Created by louistsai on 31.08.17.
 */
class VideosFragment : Fragment() {

    private var dataCenter: DataCenter? = null
    private var getVideoCancelable: Cancelable? = null
    private var videosAdapter: RecyclerViewAdapter? = null
    public var currentModuleId: String? = null      //todo this should be lateinit?

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataCenter = DataCenterImpl(context)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_videos, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchVideos("1")    //TODO hard code module id
        swipe_refresh_layout.setOnRefreshListener {
            fetchVideos(currentModuleId)
        }
        recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler_view.addItemDecoration(VerticalDividerItemDecoration(context, R.dimen.margin_between_videos))
    }

    /**
     * TODO write something
     */
    fun fetchVideos(moduleId: String?) {    //todo once currentmoduleid becomes non nullable lateinit, change it back to non-immutable
        currentModuleId = moduleId

        getVideoCancelable = dataCenter?.getVideos(moduleId, object: DataListener<List<Video>> {
            override fun onSuccess(source: Int, result: List<Video>?) {
                if (source == DataListener.SOURCE_LOCAL) {
                    Timber.d("getting offline info: '%s'", result)
                }

                swipe_refresh_layout.isRefreshing = false
                var listIsEmpty = true
                if (result != null && result.isEmpty().not()) {
                    listIsEmpty = false
                }
                if (listIsEmpty.not()) {
                    //TODO if user is refreshing, notify changes instead
                    videosAdapter = RecyclerViewAdapter(moduleId, MainActivity.NO_THUMBNAIL, result, object: OnItemClickListener<Video> {
                        override fun onSelect(item: Video) {
                            //TODO the following logic exist in 2 places: figure out how to reuse it
                            val vd = VideoDownloader(item)
                            if (vd.isVideoAvailableLocally()) {
                                //video exist, don't download it
                                val uri = Uri.parse(vd.generateAbsoluteVideoLocalFilePath().toString())
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                intent.setDataAndType(uri, "video/mp4") //TODO maybe just "video/*" ?
                                startActivity(intent)
                            } else {
                                downloadVideo(item)
                            }
                        }
                    }, object: OnItemClickListener<Video> {
                        override fun onSelect(item: Video) {
                            downloadVideo(item)
                        }
                    })
                    recycler_view.adapter = videosAdapter
                } else {
//                    TODO show something
                    Toast.makeText(this@VideosFragment.context, R.string.error_no_videos_here, Toast.LENGTH_LONG).show()
                }
                getVideoCancelable = null
            }

            override fun onFailure(error: Throwable) {
                swipe_refresh_layout.isRefreshing = false
                Timber.d("get videos failed")
                Toast.makeText(this@VideosFragment.context, "Something's wrong when fetching videos", Toast.LENGTH_LONG).show()
            }
        })
    }

    /**
     * If there is any residual network activities, kill them!
     */
    override fun onDestroy() {
        super.onDestroy()
        getVideoCancelable?.cancelNow()
        dataCenter?.close()
    }

    /**
     * Download this particular video to a specific location
     * TODO need to share receiver
     * @param video is the video that you want to download
     */
    private fun downloadVideo(video: Video) {
        Timber.d("a video selected")
        val videoDownloader = VideoDownloader(video)

        val permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        //todo do i really need shouldShowRequestPermissionRationale here???
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            val downloadId = videoDownloader.downloadVideoNow(context)

            val intentFilter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            val downloadCompleteReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    val someId = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0)
                    if (downloadId == someId) {
                        videosAdapter?.notifyVideoChangedByVideoId(video)
                        getContext().unregisterReceiver(this)
                    } else {
                        //todo ???
                        Timber.d("not the video I am looking for: %d vs %d", downloadId, someId)
                    }
                }
            }
            context.registerReceiver(downloadCompleteReceiver, intentFilter)
        } else {
            //todo ask for permission (again)
        }
    }
}