package io.github.louistsaitszho.lineage.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import io.github.louistsaitszho.lineage.ItemListUnit
import io.github.louistsaitszho.lineage.R
import io.github.louistsaitszho.lineage.RecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    //This is the way to declare Static final in Kotlin
    companion object{
        const val MEDIUM = "Medium"
        const val LARGE = "Large"
        const val NO_THUMBNAIL = "No Thumbnail"

    }


    //Layout option prone to change
    private var layoutOption: String? = null

    //List holding the dummy information
    private var listItems: MutableList<ItemListUnit>? = null



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
        when (item.itemId) {
            R.id.no_thubnail -> {
                layoutOption = NO_THUMBNAIL
                runAdapter()
                return true
            }
            R.id.medium -> {
                layoutOption = MEDIUM
                runAdapter()
                return true
            }
            R.id.large -> {
                layoutOption = LARGE
                runAdapter()
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
        runAdapter()
    }


    fun runAdapter(){

        //POOR IMPLEMENTATION DUE TO MY POOR KOTLIN SKILLS
        //Also, just a temporal implementation.
        val someSome = ItemListUnit("January","http://flaglane.com/download/german-flag/german-flag-large.png","http://techslides.com/demos/sample-videos/small.mp4")
        val someSome1 = ItemListUnit("February ","http://flaglane.com/download/german-flag/german-flag-large.png","http://techslides.com/demos/sample-videos/small.mp4")
        val someSome2 = ItemListUnit("March","https://upload.wikimedia.org/wikipedia/commons/thumb/5/5b/Flag_of_Hong_Kong.svg/2000px-Flag_of_Hong_Kong.svg.png","http://techslides.com/demos/sample-videos/small.mp4")
        val someSome3 = ItemListUnit("May","http://www.freepngimg.com/download/india/4-2-india-flag-png-hd.png","http://techslides.com/demos/sample-videos/small.mp4")
        val someSome4 = ItemListUnit("June","https://upload.wikimedia.org/wikipedia/commons/1/17/Flag_of_Mexico.png","http://techslides.com/demos/sample-videos/small.mp4")
        val someSome5 = ItemListUnit("July","https://drive.google.com/open?id=0BzUGskuag7oNZHR2TFNsbnFxaU0","http://techslides.com/demos/sample-videos/small.mp4")
        val someSome6 = ItemListUnit("August","https://drive.google.com/open?id=0BzUGskuag7oNZHR2TFNsbnFxaU0","http://techslides.com/demos/sample-videos/small.mp4")
        val someSome7 = ItemListUnit("September","https://drive.google.com/open?id=0BzUGskuag7oNZHR2TFNsbnFxaU0","http://techslides.com/demos/sample-videos/small.mp4")
        val someSome8 = ItemListUnit("October","https://drive.google.com/open?id=0BzUGskuag7oNZHR2TFNsbnFxaU0","http://techslides.com/demos/sample-videos/small.mp4")
        val someSome9 = ItemListUnit("Noviembre","https://drive.google.com/open?id=0BzUGskuag7oNZHR2TFNsbnFxaU0","http://techslides.com/demos/sample-videos/small.mp4")
        val someSome10 = ItemListUnit("Diciembre","https://drive.google.com/open?id=0BzUGskuag7oNZHR2TFNsbnFxaU0","http://techslides.com/demos/sample-videos/small.mp4")
        val someSome11 = ItemListUnit("THIS IS A VERY LONG STRING TO CHECK HOW LONG TEXT BEHAVES IN THIS SITUATION. IT'S ALSO FRIDAY TODAY, RAINY DAY","http://flaglane.com/download/german-flag/german-flag-large.png","http://techslides.com/demos/sample-videos/small.mp4")

        //List that will hold the dummy information
        listItems = mutableListOf(someSome, someSome1,someSome2,someSome3,someSome4,someSome5,someSome6, someSome7,someSome8,someSome9,someSome10,someSome11)



        //Instantiating the adapter with the magic of Kotlin:
        recycler_view.layoutManager = LinearLayoutManager(this)


        recycler_view.setHasFixedSize(true)
        //Instantiating the Adapter with the List, The Context, and the Layout Size Setting & Setting the adapter to Recycler View
        recycler_view.adapter = RecyclerViewAdapter(listItems, this, layoutOption)

    }
}
