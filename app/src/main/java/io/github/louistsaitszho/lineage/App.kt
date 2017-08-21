package io.github.louistsaitszho.lineage

import android.app.Application

/**
 * Created by louistsai on 21.08.17.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        //TODO LeakCanary
        //TODO crash report
    }
}