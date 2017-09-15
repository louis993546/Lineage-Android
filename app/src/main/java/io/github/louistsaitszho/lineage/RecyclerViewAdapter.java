package io.github.louistsaitszho.lineage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lsteamer on 15/09/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    //List that holds the information for all the Contents
    private List<ItemListUnit> listContents;
    private Context context;


    /*
    FOLLOWING IS A "DUMMY" variable that 'states' that there is no THUMBNAIL ATM
    NO_THUMBNAIL
    MEDIUM
    LARGE
     */
    private String thumbnail;

    //Construction of the adapter. provides the Contents and the context
    public RecyclerViewAdapter(List<ItemListUnit> listContents, Context context, String thumbnail) {
        this.listContents = listContents;
        this.context = context;
        this.thumbnail = thumbnail;
    }



    //Method creates (inflates) the View Holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Depending on the setting 'thumbnail' we use a different layout
        View v;
        if(thumbnail.equals("LARGE")){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_holder_video_large_thumbnail, parent, false);
        }
        else if(thumbnail.equals("MEDIUM")){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_holder_video_medium_thumbnail, parent, false);
        }
        else{
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_holder_video_no_thumbnail, parent, false);

        }
        return new ViewHolder(v);
    }

    //Method gives the ViewHolder it's contents
    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        ItemListUnit listUnit = listContents.get(position);
        holder.textUnitDescription.setText(listUnit.getTextUnitDescription());

        //Since we don't have Thumbnail, these variables are not used
        if(!thumbnail.equals("NO_THUMBNAIL")){
            holder.urlUnitImage.setText(listUnit.getUrlUnitImage());
            holder.urlUnitVideo.setText(listUnit.getUrlUnitVideo());

        }
    }

    @Override
    public int getItemCount() {
            return listContents.size();
    }

    //Reference to the Views for each data item
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textUnitDescription;
        private TextView urlUnitImage;
        private TextView urlUnitVideo;


        //Constructor
        public ViewHolder(View itemView) {
            super(itemView);
            if(thumbnail.equals("LARGE")){
                //Here we Fill the Thumbnail LARGE
            }
            else if(thumbnail.equals("MEDIUM")){
                //Here we Fill the Thumbnail MEDIUM
            }
            else{
                //Here we're filling with NO_THUMBNAIL
                textUnitDescription = itemView.findViewById(R.id.textViewUnitDescriptionNoThumbnail);
            }
        }
    }


}
