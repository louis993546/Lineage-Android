package io.github.louistsaitszho.lineage

import android.util.Log
import timber.log.Timber

/**
 * Specify how you want
 * Created by Louis on 14.09.17.
 */
class ProductionTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String?, t: Throwable?) {
        Log.println(priority, tag, message)
    }
}