package io.github.louistsaitszho.lineage.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.widget.ProgressBar
import android.widget.Toast
import io.github.louistsaitszho.lineage.R
import io.github.louistsaitszho.lineage.model.DataCenterImpl
import io.github.louistsaitszho.lineage.model.DataListener
import io.github.louistsaitszho.lineage.model.School
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        fab.setOnClickListener { _ ->
            val schoolCode = edit_text_school_code.text.toString()
            val dataCenter = DataCenterImpl(this)
            if (view_switcher_sign_in.currentView is FloatingActionButton) {
                view_switcher_sign_in.showNext()
            }
            dataCenter.signIn(schoolCode, object : DataListener<School> {
                override fun onSuccess(source: Int, result: School?) {
                    val intent = Intent(this@SignInActivity, MainActivity::class.java)
                    startActivity(intent)
                }

                override fun onFailure(error: Throwable) {
                    Toast.makeText(this@SignInActivity, R.string.error_toast_sign_in_failed, Toast.LENGTH_LONG).show()
                    text_input_layout_school_code.error = getString(R.string.error_til_wrong_school_code)
                    if (view_switcher_sign_in.currentView is ProgressBar) {
                        view_switcher_sign_in.showNext()
                    }
                }
            })
        }
    }
}
