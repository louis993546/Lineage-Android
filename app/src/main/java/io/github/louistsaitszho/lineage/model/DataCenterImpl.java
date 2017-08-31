package io.github.louistsaitszho.lineage.model;

import java.util.ArrayList;
import java.util.List;

import io.github.louistsaitszho.lineage.attributes.VideoAttribute;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by louistsai on 27.08.17.
 */
public class DataCenterImpl implements DataCenter {
    LineageApiWrapper apiWrapper;

    public DataCenterImpl() {
        apiWrapper = new LineageApiWrapperImpl();
    }

    /**
     * TODO retrieve data from local storage
     * TODO param to fetch from local or remote
     * Get a list of videos from a callback
     * @param callback will be trigger once the data is ready (or failed)
     * @return a Cancelable object so you can cancel operation if you want to (memory leak, prevent
     * loading when the activity is dead, etc.)
     */
    @Override
    public Cancelable getVideos(final DataListener<List<Video>> callback) {
        final Call<List<Data<VideoAttribute>>> call = apiWrapper.getVideo();
        Cancelable cancelable = new Cancelable() {
            @Override
            public boolean cancelNow() {
                //TODO some kind of flag to indicate "Don't even try to return anything back"?
                call.cancel();
                return true;
            }
        };
        call.enqueue(new Callback<List<Data<VideoAttribute>>>() {
            @Override
            public void onResponse(Call<List<Data<VideoAttribute>>> call, Response<List<Data<VideoAttribute>>> response) {
                if (response.body() != null) {
                    List<Video> outputList = new ArrayList<>(response.body().size());
                    for (Data<VideoAttribute> videoData : response.body()) {
                        Video video = new Video(videoData.getId(), videoData.getAttributes().getUrl(), videoData.getAttributes().getThumbnailUrl());
                        outputList.add(video);
                    }
                    callback.onSuccess(DataListener.SOURCE_REMOTE, outputList);
                }
            }

            @Override
            public void onFailure(Call<List<Data<VideoAttribute>>> call, Throwable t) {
                Timber.e(t);
                callback.onFailure(new Throwable("API returns error when you are trying to fetch videos", t));
            }
        });
        return cancelable;
    }

    @Override
    public void close() {
        //TODO realm/sqlite
    }
}
