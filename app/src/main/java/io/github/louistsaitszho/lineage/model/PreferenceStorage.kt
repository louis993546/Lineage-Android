package io.github.louistsaitszho.lineage.model

/**
 * These are a list of stuff that needs to be store locally as preference of the user/device.
 * Created by louis on 25.11.17.
 */
interface PreferenceStorage {
    fun getSchoolKey(): String?
    fun hasSchoolKey(): Boolean
    fun setSchoolKey(key: String)
//    fun getNeedsDownloadModulesId(): List<String>
//    fun addNeedsDownloadModulesId(vararg ids: String)
//    fun removeNeedsDownloadModulesId(vararg ids: String)
}