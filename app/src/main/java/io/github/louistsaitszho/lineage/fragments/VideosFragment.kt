package io.github.louistsaitszho.lineage.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.louistsaitszho.lineage.OnItemClickListener
import io.github.louistsaitszho.lineage.R
import io.github.louistsaitszho.lineage.RecyclerViewAdapter
import io.github.louistsaitszho.lineage.activities.MainActivity
import io.github.louistsaitszho.lineage.model.*
import kotlinx.android.synthetic.main.fragment_videos.*

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
        getVideoCancelable = dataCenter.getVideos(object: DataListener<List<Video>> {
            override fun onSuccess(source: Int, result: List<Video>?) {
                videosAdapter = RecyclerViewAdapter(result, MainActivity.NO_THUMBNAIL)
//                val recyclerView : RecyclerView? = view?.findViewById(R.id.recycler_view)
                recycler_view.adapter = videosAdapter
                recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }

            override fun onFailure(error: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}