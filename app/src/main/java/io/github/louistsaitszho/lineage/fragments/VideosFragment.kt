package io.github.louistsaitszho.lineage.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.github.louistsaitszho.lineage.OnItemClickListener
import io.github.louistsaitszho.lineage.R
import io.github.louistsaitszho.lineage.RecyclerViewAdapter
import io.github.louistsaitszho.lineage.activities.MainActivity
import io.github.louistsaitszho.lineage.model.*
import kotlinx.android.synthetic.main.fragment_videos.*
import timber.log.Timber

/**
 * This fragment display a list of videos
 * Created by louistsai on 31.08.17.
 */
class VideosFragment : Fragment(), OnItemClickListener<Video> {
    private val dataCenter : DataCenter = DataCenterImpl()
    private var getVideoCancelable : Cancelable? = null
    private var videosAdapter : RecyclerViewAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_videos, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getVideoCancelable = dataCenter.getVideos("1", object: DataListener<List<Video>> {
            override fun onSuccess(source: Int, result: List<Video>?) {
                val listIsEmpty = result?.isEmpty()
                if (listIsEmpty == true) {
                    videosAdapter = RecyclerViewAdapter(result, MainActivity.NO_THUMBNAIL)
                    recycler_view.adapter = videosAdapter
                    recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                } else {
                    //TODO show something
                    Toast.makeText(this@VideosFragment.context, "No videos yet!", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(error: Throwable) {
                Timber.d("get videos failed")
                Toast.makeText(this@VideosFragment.context, "Something's wrong when fetching videos", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        getVideoCancelable?.cancelNow()
    }

    /**
     * When a video is click -> TODO the following 2 tasks
     * 1) Open it if available
     * 2) Ask user to download it if not available
     */
    override fun onSelect(item: Video) {
        Timber.d("a video selected")
        Toast.makeText(this.context, item.toString(), Toast.LENGTH_LONG).show()
    }
}