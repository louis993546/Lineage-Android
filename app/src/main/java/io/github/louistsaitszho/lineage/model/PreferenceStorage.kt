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
}