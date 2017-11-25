package io.github.louistsaitszho.lineage.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import io.github.louistsaitszho.lineage.R

/**
 * TODO write something
 */
class MainActivity : AppCompatActivity() {

    //This is the way to declare Static final in Kotlin
    companion object {
        const val MEDIUM = "Medium"
        const val LARGE = "Large"
        const val NO_THUMBNAIL = "No Thumbnail"
    }

    //Layout option prone to change
    private var layoutOption: String? = null

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Default layoutOption
        layoutOption = NO_THUMBNAIL

        //Method that Instantiates and runs the adapter
//        runAdapter()
    }
}
