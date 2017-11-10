package io.github.louistsaitszho.lineage.model;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Louis on 27.08.17.
 */
public interface DataListener<ReturnType> {
    int SOURCE_REMOTE = 0;
    int SOURCE_LOCAL = 1;
    @IntDef({SOURCE_REMOTE, SOURCE_LOCAL})
    @Retention(RetentionPolicy.SOURCE)
    @interface SourceType {}

    /**
     * Call this when data retrieval is successful
     * @param source should be either SOURCE_REMOTE or SOURCE_LOCAL (lint will warn you when you
     *               don't follow the rules
     * @param result is the thing you are asking for. Nullable
     */
    void onSuccess(@SourceType int source, @Nullable ReturnType result);

    /**
     * Call this when data retrieval failed
     * TODO you might want to add more info here (e.g. error code so that the UI can display specific instruction)
     * @param error is the error. It may be a completely custom message, or it could be something
     *              from another library. Nullable
     */
    void onFailure(@NonNull Throwable error);
}
