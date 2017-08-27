package io.github.louistsaitszho.lineage;

import java.util.List;

/**
 * Created by louistsai on 27.08.17.
 */
public interface DataCenter {

    /**
     * Get a list of videos
     * @return
     */
    void getVideos(DataListener<List<Video>> callback);

    /**
     *
     */
    void close();
}
