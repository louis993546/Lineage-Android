package io.github.louistsaitszho.lineage

import io.github.louistsaitszho.lineage.model.Video
import org.junit.Assert
import org.junit.Test

/**
 * Created by louis on 04.12.17.
 */
class VideoDiffTest {
    val oldVideoList = listOf(
            Video("1", "url 1", "turl 1", "title 1", "mid 1"),
            Video("2", "url 2", "turl 2", "title 2", "mid 2"),
            Video("3", "url 3", "turl 3", "title 3", "mid 3"),
            Video("4", "url 4", "turl 4", "title 4", "mid 4"),
            Video("5", "url 5", "turl 5", "title 5", "mid 5"),
            Video("6", "url 6", "turl 6", "title 6", "mid 6"),
            Video("7", "url 7", "turl 7", "title 7", "mid 7"),
            Video("8", "url 8", "turl 8", "title 8", "mid 8"),
            Video("9", "url 9", "turl 9", "title 9", "mid 9"),
            Video("10", "url 10", "turl 10", "title 10", "mid 10")
    )
    val newVideoList = listOf(
            Video("3", "url 3", "turl 3", "title 3", "mid 3"),
            Video("4", "url 4", "turl 4", "title 4", "mid 4"),
            Video("5", "url not 5", "turl 5", "title 5", "mid 5"),
            Video("6", "url 6", "not turl 6", "title 6", "mid 6"),
            Video("7", "url 7", "turl 7", "not title 7", "mid 7"),
            Video("8", "url 8", "turl 8", "title 8", "mid 8"),
            Video("9", "url 9", "turl 9", "title 9", "mid 9"),
            Video("10", "url 10", "turl 10", "title 10", "mid 10"),
            Video("11", "url 10", "turl 10", "title 10", "mid 10"),
            Video("12", "url 10", "turl 10", "title 10", "mid 10")
    )
    private val vd = VideoDiff(oldVideoList, newVideoList)

    @Test
    fun remove_isCorrect() {
        val expectation = listOf(
            Video("1", "url 1", "turl 1", "title 1", "mid 1"),
            Video("2", "url 2", "turl 2", "title 2", "mid 2")
        )
        val answer = vd.generateToBeRemoveList()
        Assert.assertEquals("remove list is not correct", expectation, answer)
    }

    @Test
    fun add_isCorrect() {
        val expectation = listOf(
                Video("11", "url 10", "turl 10", "title 10", "mid 10"),
                Video("12", "url 10", "turl 10", "title 10", "mid 10")
        )
        val answer = vd.generateToBeAddedList()
        Assert.assertEquals("add list is not correct", expectation, answer)
    }

    @Test
    fun update_isCorrect() {
        val expectation = listOf(
                Video("5", "url not 5", "turl 5", "title 5", "mid 5"),
                Video("6", "url 6", "not turl 6", "title 6", "mid 6"),
                Video("7", "url 7", "turl 7", "not title 7", "mid 7")
        )
        val answer = vd.generateToBeUpdateList()
        Assert.assertEquals("update list is not correct", expectation, answer)
    }
}