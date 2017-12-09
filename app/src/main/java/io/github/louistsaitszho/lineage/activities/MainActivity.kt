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
import io.github.louistsaitszho.lineage.R
import io.github.louistsaitszho.lineage.fragments.VideosFragment
import io.github.louistsaitszho.lineage.model.DataCenter
import io.github.louistsaitszho.lineage.model.DataCenterImpl
import io.github.louistsaitszho.lineage.model.DataListener
import io.github.louistsaitszho.lineage.model.Module
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

/**
 * This activity contains
 * - Drawer
 * - Toolbar
 * - VideoFragment
 */
class MainActivity : AppCompatActivity() {
    lateinit var dataCenter: DataCenter
    private val requestCodeWriteExternalStorage = 123
    var currentModule: Module? = null

    //This is the way to declare Static final in Kotlin
    companion object {
        const val MEDIUM = "Medium"
        const val LARGE = "Large"
        const val NO_THUMBNAIL = "No Thumbnail"
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        //TODO set checkbox accordingly
        return super.onPrepareOptionsMenu(menu)
    }

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
        val videoFragment = supportFragmentManager.findFragmentById(R.id.fragment_videos) as VideosFragment
        return when (item.itemId) {
            R.id.action_refresh -> {
                videoFragment.fetchVideos(videoFragment.currentModuleId)
                true
            }
            R.id.action_auto_download_checkbox -> {
                val shouldAutoDownload = item.isChecked.not()
                dataCenter.setModuleToNeedsDownload(currentModule, shouldAutoDownload)
                item.isChecked = shouldAutoDownload
                true
            }
            //todo figure out how to deal with these thumbnails later
            else -> false
        }
    }

    /**
     * TODO think about savedinstancestate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataCenter = DataCenterImpl(this)

        //toolbar and drawer setup
        setSupportActionBar(toolbar)
        val drawerToggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.content_description_open_drawer, R.string.content_description_close_drawer)
        drawer_layout.addDrawerListener(drawerToggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_light_24dp)

        makeSureWriteExternalStorageIsAvailable()

        dataCenter.getSchoolCodeLocally(object: DataListener<String> {
            override fun onSuccess(source: Int, result: String?) {
                dataCenter.getModules(object: DataListener<MutableList<Module>> {
                    override fun onSuccess(source: Int, result: MutableList<Module>?) {
                        if (source == DataListener.SOURCE_LOCAL) {
                            //todo figure out how to add sub header "Modules"
                            //todo assumption: local always gets loaded before remote
                            result?.forEachIndexed { index, module ->
                                navigation_view.menu.add(0, module.id.hashCode(), index, module.name)
                            }
                        } else if (source == DataListener.SOURCE_REMOTE) {
                            //only add modules that is new
                            result?.forEach { module ->
                                val existingModule = navigation_view.menu.findItem(module.id.hashCode())
                                if (existingModule != null) {
                                    //module already exist, next
                                } else {
                                    navigation_view.menu.add(0, module.id.hashCode(), navigation_view.menu.size(), module.name)
                                }
                            }
                        }
                        navigation_view.setNavigationItemSelectedListener { menuItem ->
                            result?.forEach { thisModule ->
                                if (thisModule.id.hashCode() == menuItem.itemId) {
                                    Timber.d("this module = %s", thisModule)
                                    currentModule = thisModule
                                    supportActionBar?.title = thisModule.name
                                    menuItem.isChecked = true
                                    return@forEach  //module has been found, can skip the rest
                                }
                            }
                            true
                        }
                    }

                    override fun onFailure(error: Throwable) {
                        //todo i need something in the drawer ui to allow user to refresh this list
                        Timber.d(Throwable("figure out what this could be and fix it", error))
                    }
                })
                //TODO schedule download here???
            }

            override fun onFailure(error: Throwable) {
                //TODO close everything down, go back to SignInActivity
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
                .setTitle(getString(R.string.title_rationale_ext_storage))
                .setMessage(getString(R.string.message_rationale_ext_storage))
                .setPositiveButton(getString(R.string.button_try_again)) { dialog, _ ->
                    dialog.dismiss()
                    askForExternalStoragePermission()   //i.e. try again
                }
                .setNegativeButton(getString(R.string.button_cancel)) { dialog, _ ->
                    dialog.dismiss()
//                    TODO("mark it to somewhere")
                }
                .show()
    }
}
