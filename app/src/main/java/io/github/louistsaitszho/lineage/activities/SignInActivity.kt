package io.github.louistsaitszho.lineage.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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

//        experiments()

        fab.setOnClickListener { _ ->
            //todo put some spinner wheel or something
            val schoolCode = edit_text_school_code.text.toString()
            val dataCenter = DataCenterImpl(this)
            dataCenter.signIn(schoolCode, object : DataListener<School> {
                override fun onSuccess(source: Int, result: School?) {
                    val intent = Intent(this@SignInActivity, MainActivity::class.java)
                    startActivity(intent)
                }

                override fun onFailure(error: Throwable) {
                    //todo figure out how to display error message
                    Toast.makeText(this@SignInActivity, "Failed", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        when (requestCode) {
//            123 -> {
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    createFoldersAndStuff()
//                } else {
//                    //TODO let user know
//                }
//            }
//        }
//    }
//
//    private fun experiments() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                Timber.d("Need to show something to let user know why this permission is needed")
//                Toast.makeText(this, "Need to show something to let user know why this permission is needed", Toast.LENGTH_LONG).show()
//            } else {
//                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 123)
//            }
//        } else {
//            createFoldersAndStuff()
//        }
//    }
//
//    private fun createFoldersAndStuff() {
//        val downloadFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//        Timber.d("Download folder path = '%s'", downloadFolder.absolutePath)
//        val lineageFolder = File(downloadFolder, "${SystemConfig.downloadFolderName}/")
//        Timber.d("Lineage folder path = '%s'", lineageFolder.absolutePath)
//        if (!lineageFolder.exists() || !lineageFolder.isDirectory) {
//            Timber.d("Lineage folder does not exist, making it now")
//            val success = lineageFolder.mkdirs()
//            if (!success) {
//                Timber.d("Create lineage folder failed!")
//            }
//        }
//        val files = lineageFolder.list()
//        if (files == null || files.isEmpty()) {
//            Timber.d("Lineage folder is null/empty")
//        } else {
//            Timber.d("here's the list of files: '%s'", files.contentDeepToString())
//        }
//        //        val moduleFolder = File(lineageFolder, "$it/")
////        val files = moduleFolder.list()
//    }

}
