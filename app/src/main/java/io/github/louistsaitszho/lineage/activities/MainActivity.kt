package io.github.louistsaitszho.lineage.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import io.github.louistsaitszho.lineage.R
import io.github.louistsaitszho.lineage.model.DataCenterImpl
import io.github.louistsaitszho.lineage.model.DataListener
import io.github.louistsaitszho.lineage.model.Module
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

/**
 * TODO write something
 */
class MainActivity : AppCompatActivity() {
    val dataCenter = DataCenterImpl(this)
    private val requestCodeWriteExternalStorage = 123

    //This is the way to declare Static final in Kotlin
    companion object {
        const val MEDIUM = "Medium"
        const val LARGE = "Large"
        const val NO_THUMBNAIL = "No Thumbnail"
    }

    //Layout option prone to change
    private var layoutOption = NO_THUMBNAIL

    //This creates a menu that displays 'Settings' on the top right corner.
    //Such Menu can be modified in the Folder app/res/menu/main_menu.xml
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //This takes the Option from the Layout and sizes the app accordingly
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        //todo instead of calling runadapter, call the fragment
        when (item.itemId) {
            R.id.no_thubnail -> {
                layoutOption = NO_THUMBNAIL
                return true
            }
            R.id.medium -> {
                layoutOption = MEDIUM
                return true
            }
            R.id.large -> {
                layoutOption = LARGE
                return true
            }
            else -> return false
        }

    }

    /**
     * TODO think about savedinstancestate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //toolbar and drawer setup
        setSupportActionBar(toolbar)
        val drawerToggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.content_description_open_drawer, R.string.content_description_close_drawer)
        drawer_layout.addDrawerListener(drawerToggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)

        makeSureWriteExternalStorageIsAvailable()

        dataCenter.getSchoolCodeLocally(object: DataListener<String> {
            override fun onSuccess(source: Int, result: String?) {
                //TODO inflate menu accordingly
                dataCenter.getModules(object: DataListener<MutableList<Module>> {
                    override fun onSuccess(source: Int, result: MutableList<Module>?) {
                        //todo i am just gonna ignore the whole offline cache thing for now
                        //todo figure out how to add sub header "Modules"
                        result?.forEachIndexed { index, module ->
                            navigation_view.menu.add(0, index, index, module.name)
                        }
                        navigation_view.setNavigationItemSelectedListener {
                            Toast.makeText(this@MainActivity, "a module selected: " + it.title, Toast.LENGTH_LONG).show()
                            supportActionBar?.title = it.title
                            it.isChecked = true
                            true
                        }
                    }

                    override fun onFailure(error: Throwable) {
                        //todo ii need something in the drawer ui to allow user to refresh this list
                        Timber.d(Throwable("figure out what this could be and fix it", error))
                    }
                })
                //TODO schedule download here???
            }

            override fun onFailure(error: Throwable) {
                //TODO launch something and ask for school key first
                Timber.d(Throwable("Probably because no school code", error))
            }
        })
    }

    /**
     *
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            requestCodeWriteExternalStorage -> {
                if (!grantResults.isNotEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    showRationaleForWriteExternalStorage()
                }
            }
        }
    }

    /**
     * Make sure the WRITE_EXTERNAL_STORAGE is granted, so that I can download stuff in background
     * overnight
     */
    private fun makeSureWriteExternalStorageIsAvailable() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showRationaleForWriteExternalStorage()
            } else {
                askForExternalStoragePermission()
            }
        }
    }

    private fun askForExternalStoragePermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCodeWriteExternalStorage)
    }

    /**
     * If the system things that we need to explain to user why this permission is needed, this will
     * create a dialog to explain why
     */
    private fun showRationaleForWriteExternalStorage() {
        AlertDialog.Builder(this)
                .setTitle("We need the write to external storage permission")
                .setMessage("Without this permission, this app will not be able to download contents overnight!")
                .setPositiveButton("Try again") { dialog, which ->
                    dialog.dismiss()
                    askForExternalStoragePermission()   //i.e. try again
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
//                    TODO("mark it to somewhere")
                }
                .show()
    }
}
