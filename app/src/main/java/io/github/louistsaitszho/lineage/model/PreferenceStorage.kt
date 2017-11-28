package io.github.louistsaitszho.lineage.model

/**
 * These are a list of stuff that needs to be store locally as preference of the user/device.
 * Created by louis on 25.11.17.
 */
interface PreferenceStorage {
    /**
     * get school key
     * nullable string
     */
    fun getSchoolKey(): String?

    /**
     *
     */
    fun hasSchoolKey(): Boolean

    /**
     *
     */
    fun setSchoolKey(key: String)

    /**
     * get a list of ids (String) of modules, which needs to be downloaded automatically
     * @return a set of ids (String), non-null (empty is fine but null is not)
     */
    fun getNeedsDownloadModulesId(): Set<String>
//    fun addNeedsDownloadModulesId(vararg ids: String)
//    fun removeNeedsDownloadModulesId(vararg ids: String)
}