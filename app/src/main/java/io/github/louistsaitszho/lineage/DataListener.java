package io.github.louistsaitszho.lineage;

/**
 * Created by louistsai on 27.08.17.
 */
public interface DataListener<T> {
    void onSuccess(T result);
    void onFailure(Throwable error);
}
