package io.github.louistsaitszho.lineage.activities

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import io.github.louistsaitszho.lineage.R
import io.github.louistsaitszho.lineage.SystemConfig
import io.github.louistsaitszho.lineage.model.DataCenterImpl
import io.github.louistsaitszho.lineage.model.DataListener
import io.github.louistsaitszho.lineage.model.School
import kotlinx.android.synthetic.main.activity_sign_in.*
import timber.log.Timber
import java.io.File

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        fab.setOnClickListener { _ ->
            fab_progress.show()
            //todo put some spinner wheel or something
            val schoolCode = edit_text_school_code.text.toString()
            val dataCenter = DataCenterImpl(this)
            dataCenter.signIn(schoolCode, object : DataListener<School> {
                override fun onSuccess(source: Int, result: School?) {
                    fab_progress.beginFinalAnimation()
                    fab_progress.attachListener {
                        val intent = Intent(this@SignInActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                }

                override fun onFailure(error: Throwable) {
                    //todo hard code string
                    Toast.makeText(this@SignInActivity, "Sign in failed. Please try again", Toast.LENGTH_LONG).show()
                    text_input_layout_school_code.error = "Incorrect school code!"
                    fab_progress.hide()
                }
            })
        }
    }

    private fun createFoldersAndStuff() {
        val downloadFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        Timber.d("Download folder path = '%s'", downloadFolder.absolutePath)
        val lineageFolder = File(downloadFolder, "${SystemConfig.downloadFolderName}/")
        Timber.d("Lineage folder path = '%s'", lineageFolder.absolutePath)
        if (!lineageFolder.exists() || !lineageFolder.isDirectory) {
            Timber.d("Lineage folder does not exist, making it now")
            val success = lineageFolder.mkdirs()
            if (!success) {
                Timber.d("Create lineage folder failed!")
            }
        }
        val files = lineageFolder.list()
        if (files == null || files.isEmpty()) {
            Timber.d("Lineage folder is null/empty")
        } else {
            Timber.d("here's the list of files: '%s'", files.contentDeepToString())
        }
//        val moduleFolder = File(lineageFolder, "$it/")
//        val files = moduleFolder.list()
    }

}
