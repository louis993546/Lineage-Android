package io.github.louistsaitszho.lineage;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.VideoView;

/**
 * Created by lsteamer on 10/10/2017.
 */

public class VideoPlayerActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressDialog mDialog;
    private VideoView videoView;
    private ImageButton btnPlayPause;

    private String videoURL;







    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_layout);

        Bundle extras = getIntent().getExtras();
        videoURL = extras.getString("videoURL");

        videoView = (VideoView)findViewById(R.id.videoView);
        btnPlayPause = (ImageButton)findViewById(R.id.button_play_pause);
        btnPlayPause.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        playVideo();
    }

    public void playVideo(){


        mDialog = new ProgressDialog(VideoPlayerActivity.this);
        mDialog.setMessage(VideoPlayerActivity.this.getString(R.string.wait));
        mDialog.setCanceledOnTouchOutside(false);
        try{

            if(!videoView.isPlaying()){

                mDialog.show();
                Uri uri = Uri.parse(videoURL);
                videoView.setVideoURI(uri);
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        btnPlayPause.setImageResource(R.drawable.ic_play);
                    }
                });

            }
            else{
                videoView.pause();
                btnPlayPause.setImageResource(R.drawable.ic_play);
            }

        }catch (Exception ex){

        }

        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mDialog.dismiss();
                mediaPlayer.setLooping(false);
                videoView.start();
                btnPlayPause.setImageResource(R.drawable.ic_pause);
            }
        });


    }

}
