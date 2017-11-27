package io.github.louistsaitszho.lineage.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.louistsaitszho.lineage.model.DataCenterImpl
import io.github.louistsaitszho.lineage.model.DataListener

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //todo check if there is school code, if yes, open MainActivity; else, open SignInActivity
        val dataCenter = DataCenterImpl(this)
        dataCenter.getSchoolCodeLocally(object : DataListener<String> {
            override fun onSuccess(source: Int, result: String?) {
                val mainIntent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(mainIntent)
                finish()
            }

            override fun onFailure(error: Throwable) {
                val mainIntent = Intent(this@SplashActivity, SignInActivity::class.java)
                startActivity(mainIntent)
                finish()
            }
        })
    }
}