package io.github.louistsaitszho.lineage.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
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
import java.io.File

/**
 * This fragment display a list of videos
 * Created by louistsai on 31.08.17.
 */
class VideosFragment : Fragment() {

    private var dataCenter: DataCenter? = null
    private var getVideoCancelable : Cancelable? = null
    private var videosAdapter : RecyclerViewAdapter? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        dataCenter = DataCenterImpl(context)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_videos, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val moduleId = "1"  //TODO hard code module id
        getVideoCancelable = dataCenter?.getVideos(moduleId, object: DataListener<List<Video>> {
            override fun onSuccess(source: Int, result: List<Video>?) {
                var listIsEmpty = true
                if (result != null && result.isEmpty().not()) {
                    listIsEmpty = false
                }
                if (listIsEmpty.not()) {
                    videosAdapter = RecyclerViewAdapter(moduleId, MainActivity.NO_THUMBNAIL, result, object: OnItemClickListener<Video> {
                        override fun onSelect(item: Video) {
                            //TODO download video if not available; else open it
                            val videoFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), VideoDownloader(item).generateVideoFilePath())
                            if (videoFile.exists() && videoFile.isFile) {
                                //video exist, don't download it
                                Toast.makeText(context, "Video already downloaded", Toast.LENGTH_LONG).show()
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
                    recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    recycler_view.addItemDecoration(VerticalDividerItemDecoration(context, R.dimen.margin_between_videos))
                } else {
//                    TODO show something
                    Toast.makeText(this@VideosFragment.context, R.string.error_no_videos_here, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(error: Throwable) {
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

    private fun downloadVideo(video: Video) {
        //TODO also include broadcast listener stuff here


        Timber.d("a video selected")
        val videoDownloader = VideoDownloader(video)

        val permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        //todo do i really need shouldShowRequestPermissionRationale here???
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            videoDownloader.downloadVideoNow(context)
        } else {
            //todo ask for permission (again)
        }
    }
}