package io.github.louistsaitszho.lineage

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * @constructor
 * @author Louis Tsai
 * @since 21.08.2017
 */
class VideoListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     *
     */
    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * @param position of the item in the whole list
     * @return what type of view holder should be used
     */
    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    /**
     * @param parent
     * @param viewType
     */
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * @param holder
     * @param position SHOULD NOT BE USE! USE holder.getAdapterPosition() INSTEAD
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}