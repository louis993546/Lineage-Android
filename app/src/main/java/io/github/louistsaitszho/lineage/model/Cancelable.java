package io.github.louistsaitszho.lineage.model;

/**
 * Created by Louis on 31.08.17.
 */
public interface Cancelable {
    /**
     * TODO add param (in case there are different reason why it needs to be cancel/extra param for cancellation)
     * @return true if cancel is (probably) successful
     */
    boolean cancelNow();
}
