package io.github.louistsaitszho.lineage.trees

import android.content.Context
import android.widget.Toast
import timber.log.Timber

/**
 * Created by louis on 02.12.17.
 */
class DebugTree(private val applicationContext: Context): Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        super.log(priority, tag, message, t)
    }
}