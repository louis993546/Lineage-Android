package io.github.louistsaitszho.lineage.model

import android.content.Context
import android.text.TextUtils

/**
 * This thing make accessing SharedPreference looks nicer
 * Created by louis on 24.11.17.
 */
class PreferenceStorageImpl(private val context: Context?) : PreferenceStorage {
    object FileName {
        val USER_PREFERENCE = "user_preference"
        val DEVICE_PREFERENCE = "device_preference"
    }

    object Keys {
        val SCHOOL_KEY = "school_key"
        val NEEDS_DOWNLOAD_MODULE_LIST_KEY = "module_list_key"
    }

    /**
     *
     */
    override fun getSchoolKey(): String? = getUserPreference()?.getString(Keys.SCHOOL_KEY, null)

    /**
     * This just wrap the getSchoolKey() method and make it nicer to use
     * return true if user has already enter school key
     */
    override fun hasSchoolKey() = TextUtils.isEmpty(getSchoolKey()).not()

    /**
     *
     */
    override fun setSchoolKey(key: String) {
        getUserPreference()?.edit()
                ?.putString(Keys.SCHOOL_KEY, key)
                ?.apply()
    }

//    /**
//     * TODO will this create NPE with null.toList()???
//     */
//    override fun getNeedsDownloadModulesId(): List<String> {
//        return getUserPreference()?.getStringSet(Keys.NEEDS_DOWNLOAD_MODULE_LIST_KEY, null)?.toList()
//    }

//    //TODO think about how to refactor add and remove NeedsDownloadModulesId -> they are only different by 2 line
//
//    override fun addNeedsDownloadModulesId(vararg ids: String) {
//        val currentIdList = getNeedsDownloadModulesId().toMutableList()
//        var needsUpdate = false
//        ids.forEach { id ->
//            if (!currentIdList.contains(id)) {
//                currentIdList.add(id)
//                needsUpdate = true
//            }
//        }
//        if (needsUpdate) {
//            getUserPreference().edit().putStringSet(Keys.NEEDS_DOWNLOAD_MODULE_LIST_KEY, currentIdList.toSet()).apply()
//        }
//    }
//
//    override fun removeNeedsDownloadModulesId(vararg ids: String) {
//        val currentIdList = getNeedsDownloadModulesId().toMutableList()
//        var needsUpdate = false
//        ids.forEach { id ->
//            if (currentIdList.contains(id)) {
//                currentIdList.remove(id)
//                needsUpdate = true
//            }
//        }
//        if (needsUpdate) {
//            getUserPreference().edit().putStringSet(Keys.NEEDS_DOWNLOAD_MODULE_LIST_KEY, currentIdList.toSet()).apply()
//        }
//    }

    private fun getUserPreference() = context?.getSharedPreferences(FileName.USER_PREFERENCE, Context.MODE_PRIVATE)

//    private fun getDevicePreference() = context?.getSharedPreferences(FileName.DEVICE_PREFERENCE, Context.MODE_PRIVATE)
}