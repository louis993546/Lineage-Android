package io.github.louistsaitszho.lineage;

import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import io.github.louistsaitszho.lineage.model.Video;

/**
 * Created by louistsai on 27.08.17.
 */
public class VideosAdapter extends RecyclerView.Adapter {
    //constants
    private final static int VIEW_TYPE_THUMBNAIL_NONE = 0;
    private final static int VIEW_TYPE_THUMBNAIL_MEDIUM = 1;
    private final static int VIEW_TYPE_THUMBNAIL_LARGE = 2;
    @IntDef({VIEW_TYPE_THUMBNAIL_NONE, VIEW_TYPE_THUMBNAIL_MEDIUM, VIEW_TYPE_THUMBNAIL_LARGE})
    @Retention(RetentionPolicy.SOURCE)
    @interface ViewType {}

    //data
    List<Video> videos;

    public VideosAdapter(List<Video> videos) {
        this.videos = videos;
    }

    /**
     * TODO return view type base on user selection
     * @param position of the item (but we don't care right now)
     * @return view type based on user selection
     */
    @ViewType
    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_THUMBNAIL_NONE;
    }

    /**
     * TODO
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    /**
     * TODO
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return videos == null ? 0 : videos.size();
    }
}
