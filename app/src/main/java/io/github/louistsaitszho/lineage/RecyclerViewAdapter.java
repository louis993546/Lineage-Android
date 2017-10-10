package io.github.louistsaitszho.lineage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import io.github.louistsaitszho.lineage.activities.MainActivity;

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
        if(thumbnail.equals(MainActivity.LARGE)){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_holder_video_large_thumbnail, parent, false);
        }
        else if(thumbnail.equals(MainActivity.MEDIUM)){
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
        final ItemListUnit listUnit = listContents.get(position);
        holder.textUnitDescription.setText(listUnit.getTextUnitDescription());



        //Here we call the launchVideoActivity method (which in turn creates the New activity where the video will be played)
        if(thumbnail.equals(MainActivity.LARGE)||thumbnail.equals(MainActivity.MEDIUM)){
            holder.unitImage.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    launchVideoActivity(listUnit.getUrlUnitVideo());
                }
            });
        }
        else{
            holder.textUnitDescription.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    launchVideoActivity(listUnit.getUrlUnitVideo());

                }
            });

        }


        //Since we don't have Thumbnail, these variables are not used
        if(!thumbnail.equals(MainActivity.NO_THUMBNAIL)){
            //holder.urlUnitImage.setText(listUnit.getUrlUnitImage());
            //holder.urlUnitVideo.setText(listUnit.getUrlUnitVideo());
            Glide.with(context)
                    .load(listUnit.getUrlUnitImage())
                    .into(holder.unitImage);

        }
    }

    //Method that plays the video.
    /*
        At the moment, I'm launching a new Activity to play it,
        but if specs change, this should be modified
     */
    public void launchVideoActivity(String videoURL){

        Intent intent = new Intent(context, VideoPlayerActivity.class);
        intent.putExtra("videoURL",videoURL);


        context.startActivity(intent);



    }


    @Override
    public int getItemCount() {
            return listContents.size();
    }


    //Reference to the Views for each data item
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textUnitDescription;
        private ImageView unitImage;
        private TextView urlUnitVideo;


        //Constructor
        public ViewHolder(View itemView) {
            super(itemView);
            if(thumbnail.equals(MainActivity.LARGE)){
                //Here we Fill the Thumbnail LARGE
                textUnitDescription = itemView.findViewById(R.id.textViewUnitDescriptionLarge);
                unitImage = itemView.findViewById(R.id.image_view_large);

            }
            else if(thumbnail.equals(MainActivity.MEDIUM)){
                //Here we Fill the Thumbnail MEDIUM
                textUnitDescription = itemView.findViewById(R.id.textViewUnitDescriptionMedium);
                unitImage = itemView.findViewById(R.id.image_view_medium);
            }
            else{
                //Here we're filling with NO_THUMBNAIL
                textUnitDescription = itemView.findViewById(R.id.textViewUnitDescriptionNoThumbnail);
            }
        }


    }


}
