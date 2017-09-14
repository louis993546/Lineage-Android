package io.github.louistsaitszho.lineage

/**
 * When something has been selected
 * Useful for RecyclerView and stuff
 * Created by louistsai on 31.08.17.
 */
interface OnItemClickListener<T> {

    /**
     * When something is being selected
     * @param
     */
    fun onSelect(item : T)
}