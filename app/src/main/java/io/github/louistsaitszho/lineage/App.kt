package io.github.louistsaitszho.lineage

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import io.github.louistsaitszho.lineage.trees.DebugTree
import timber.log.Timber

/**
 * Created by Louis on 21.08.17.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        Timber.plant(DebugTree(applicationContext))
        LeakCanary.install(this)
    }
}