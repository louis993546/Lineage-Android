package io.github.louistsaitszho.lineage.fragments

import kotlinx.android.synthetic.main.fragment_videos.*

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.louistsaitszho.lineage.R
import io.github.louistsaitszho.lineage.VideosAdapter
import io.github.louistsaitszho.lineage.model.DataCenter
import io.github.louistsaitszho.lineage.model.DataCenterImpl
import io.github.louistsaitszho.lineage.model.DataListener
import io.github.louistsaitszho.lineage.model.Video

/**
 * This fragment display a list of videos
 * Created by louistsai on 31.08.17.
 */
class VideosFragment : Fragment() {
    val dataCenter : DataCenter = DataCenterImpl()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_videos, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataCenter.getVideos(object: DataListener<List<Video>> {
            override fun onSuccess(source: Int, result: List<Video>?) {
                recycler_view.adapter = VideosAdapter(result)
                recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }

            override fun onFailure(error: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }
}