package io.github.louistsaitszho.lineage

import io.github.louistsaitszho.lineage.model.Module

/**
 * Created by louis on 03.12.17.
 */
class ModuleDiff(private val oldModules: List<Module>, private val newModules: List<Module>) {
    /**
     * What should be remove?
     * - modules that only exist in oldModules (by id)
     */
    fun generateToBeRemoveModules(): Set<Module> {
        val list = mutableSetOf<Module>()
        oldModules.forEach { oldModule ->
            var needsToBeRemove = true
            newModules.forEach { newModule ->
                if (newModule.id == oldModule.id) {
                    needsToBeRemove = false
                }
            }
            if (needsToBeRemove) {
                list.add(oldModule)
            }
        }
        return list
    }

    /**
     * What should be added?
     * - modules that only exist in newModules (by id)
     */
    fun generateToBeAddedModules(): Set<Module> {
        val list = mutableSetOf<Module>()
        newModules.forEach { newModule ->
            var needsToBeAdded = true
            oldModules.forEach { oldModule ->
                if (newModule.id == oldModule.id) {
                    needsToBeAdded = false
                }
            }
            if (needsToBeAdded) {
                list.add(newModule)
            }
        }
        return list
    }

    /**
     * What should be update?
     * - modules exist in both lists (by id)
     * - module name does not match between the 2 modules
     */
    fun generateToBeUpdateModules(): Set<Module> {
        val list = mutableSetOf<Module>()
        oldModules.forEach { oldModule ->
            var toBeUpdate: Module? = null
            newModules.forEach { newModule ->
                if (oldModule.id == newModule.id) {
                    if (oldModule.name != newModule.name) {
                        toBeUpdate = newModule
                        toBeUpdate?.needsAutoDownload = oldModule.needsAutoDownload
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