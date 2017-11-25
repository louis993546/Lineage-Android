package io.github.louistsaitszho.lineage

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

/**
 * Created by louistsai on 21.08.17.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        Timber.plant(Timber.DebugTree())
        LeakCanary.install(this)
        //TODO crash report (check how to integrate with Timber)
    }
}