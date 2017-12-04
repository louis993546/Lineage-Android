package io.github.louistsaitszho.lineage

import io.github.louistsaitszho.lineage.model.Video

/**
 * Created by louis on 03.12.17.
 */
class VideoDiff(val oldVideos: List<Video>, val newVideos: List<Video>) {
    /**
     * What should be remove?
     * - videos that only exist in oldVideos (by id)
     */
    fun generateToBeRemoveList(): Set<Video> {
        val list = mutableSetOf<Video>()
        oldVideos.forEach { oldVideo ->
            var needsToBeRemove = true
            newVideos.forEach { newVideo ->
                if (newVideo.id == oldVideo.id) {
                    needsToBeRemove = false
                }
            }
            if (needsToBeRemove) {
                list.add(oldVideo)
            }
        }
        return list
    }

    /**
     * What should be added
     * - videos that only exist in newVideos (by id)
     */
    fun generateToBeAddedList(): Set<Video> {
        val list = mutableSetOf<Video>()
        newVideos.forEach { newVideo ->
            var needsToBeAdded = true
            oldVideos.forEach { oldVideo ->
                if (newVideo.id == oldVideo.id) {
                    needsToBeAdded = false
                }
            }
            if (needsToBeAdded) {
                list.add(newVideo)
            }
        }
        return list
    }

    /**
     * What should be update?
     * - videos exist in both lists (by id)
     * - any one of the following field does not match between the 2 video
     *   - title
     *   - url
     *   - thumbnail url
     */
    fun generateToBeUpdateList(): Set<Video> {
        val list = mutableSetOf<Video>()
        oldVideos.forEach { oldVideo ->
            var toBeUpdate: Video? = null
            newVideos.forEach { newVideo ->
                if (oldVideo.id == newVideo.id) {
                    if (oldVideo == newVideo) {
                        //the 2 items are exactly the same
                    } else {
                        toBeUpdate = newVideo
                    }
                }
            }
            if (toBeUpdate != null) {
                list.add(toBeUpdate!!)
            }
        }
        return list
    }
}