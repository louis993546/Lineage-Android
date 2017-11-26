package io.github.louistsaitszho.lineage.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
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
        setSupportActionBar(toolbar)
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
}
