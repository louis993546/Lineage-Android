package io.github.louistsaitszho.lineage;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.github.louistsaitszho.lineage.activities.MainActivity;
import io.github.louistsaitszho.lineage.model.Video;

/**
 * todo rename to sth like video adapter (we will have other adapter)
 * Created by lsteamer on 15/09/2017.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    //List that holds the information for all the Contents
    private List<Video> listContents;

    /*
    FOLLOWING IS A "DUMMY" variable that 'states' that there is no THUMBNAIL ATM
    NO_THUMBNAIL
    MEDIUM
    LARGE
     */
    private String thumbnail;

    //Construction of the adapter. provides the Contents and the context
    public RecyclerViewAdapter(List<Video> listContents, String thumbnail) {
        this.listContents = listContents;
        this.thumbnail = thumbnail;
    }


    //Method creates (inflates) the View Holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Depending on the setting 'thumbnail' we use a different layout
        @LayoutRes int layoutRes;
        switch (thumbnail) {
            case MainActivity.LARGE:
                layoutRes = R.layout.view_holder_video_large_thumbnail;
                break;
            case MainActivity.MEDIUM:
                layoutRes = R.layout.view_holder_video_medium_thumbnail;
                break;
            default:
                layoutRes = R.layout.view_holder_video_no_thumbnail;
                break;
        }
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(layoutRes, parent, false));
    }


    //Method gives the ViewHolder it's contents
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Video listUnit = listContents.get(position);
        holder.textUnitDescription.setText(listUnit.getTitle());

        //Since we don't have Thumbnail, these variables are not used
        if (!thumbnail.equals(MainActivity.NO_THUMBNAIL)) {
            //holder.urlUnitImage.setText(listUnit.getUrlUnitImage());
            //holder.urlUnitVideo.setText(listUnit.getUrlUnitVideo());
            Glide.with(holder.unitImage.getContext())
                    .load(listUnit.getThumbnailUrl())
                    .into(holder.unitImage);

        }
    }

    @Override
    public int getItemCount() {
        return listContents.size();
    }

    //Reference to the Views for each data item
    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textUnitDescription;
        private ImageView unitImage;
        private TextView urlUnitVideo;

        //Constructor
        ViewHolder(View itemView) {
            super(itemView);
            switch (thumbnail) {
                case MainActivity.LARGE:
                    //Here we Fill the Thumbnail LARGE
                    textUnitDescription = itemView.findViewById(R.id.textViewUnitDescriptionLarge);
                    unitImage = itemView.findViewById(R.id.image_view_large);
                    break;
                case MainActivity.MEDIUM:
                    //Here we Fill the Thumbnail MEDIUM
                    textUnitDescription = itemView.findViewById(R.id.textViewUnitDescriptionMedium);
                    unitImage = itemView.findViewById(R.id.image_view_medium);
                    break;
                default:
                    //Here we're filling with NO_THUMBNAIL
                    textUnitDescription = itemView.findViewById(R.id.textViewUnitDescriptionNoThumbnail);
                    break;
            }
        }
    }


}
