package io.github.louistsaitszho.lineage.model;

import java.util.List;

/**
 * TODO need to return Call object so that it can be cancel if needed
 * Created by louistsai on 27.08.17.
 */
public interface DataCenter {

    /**
     * Get a list of videos
     * @return A Cancelable object so that you can cancel the request if necessary
     */
    Cancelable getVideos(DataListener<List<Video>> callback);

    /**
     * Most implementations of data storage or fetching library requires termination of something.
     * Implement whatever you need to do so that whoever use it don't need to know exactly how to
     * close each things specifically
     */
    void close();
}
