package io.github.louistsaitszho.lineage

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import io.realm.Realm
import timber.log.Timber

/**
 * Application class of the app. Handles a whole bunch of initiations
 * - Timber
 * - LeakCanary
 * - Realm
 *
 * Created by louistsai on 21.08.17.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        when {
            BuildConfig.DEBUG -> {
                if (LeakCanary.isInAnalyzerProcess(this)) {
                    // This process is dedicated to LeakCanary for heap analysis.
                    // You should not init your app in this process.
                    return
                }
                LeakCanary.install(this)
                Timber.plant(Timber.DebugTree())
            }
            else -> Timber.plant(ProductionTree())
        }
        Realm.init(this)
    }
}