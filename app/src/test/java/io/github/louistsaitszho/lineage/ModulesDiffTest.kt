package io.github.louistsaitszho.lineage

import io.github.louistsaitszho.lineage.model.Module
import org.junit.Assert
import org.junit.Test

/**
 * This thing make sure ModuleDiff works correctly
 *
 * Created by louis on 03.12.17.
 */
class ModulesDiffTest {
    val oldModuleList = listOf(
            Module("1", "1", true),
            Module("2", "2", true),
            Module("3", "3", true),
            Module("4", "4", true),
            Module("5", "5", false),
            Module("6", "6", false),
            Module("7", "7", true),
            Module("8", "8", true),
            Module("9", "9", true),
            Module("10", "10", true)
    )

    val newModuleList = listOf(
            Module("3", "3", true),
            Module("4", "4", true),
            Module("5", "not 5", true),
            Module("6", "6", true),
            Module("7", "7", true),
            Module("8", "not 8", true),
            Module("9", "9", true),
            Module("10", "10", true),
            Module("11", "11", true),
            Module("12", "12", true),
            Module("13", "13", true)
    )

    private val md = ModuleDiff(oldModuleList, newModuleList)

    @Test
    fun remove_isCorrect() {
        val expectation = setOf(
                Module("1", "1", true),
                Module("2", "2", true)
        )
        val answer = md.generateToBeRemoveModules()
        Assert.assertEquals("remove list is not correct", expectation, answer)
    }

    @Test
    fun add_isCorrect() {
        val expectation = setOf(
                Module("11", "11", true),
                Module("12", "12", true),
                Module("13", "13", true)
        )
        val answer = md.generateToBeAddedModules()
        Assert.assertEquals("add list is not correct", expectation, answer)
    }

    @Test
    fun update_isCorrect() {
        val expectation = setOf(
                Module("5", "not 5", false),
                Module("8", "not 8", true)
        )
        val answer = md.generateToBeUpdateModules()
        Assert.assertEquals("update list is not correct", expectation, answer)
    }
}