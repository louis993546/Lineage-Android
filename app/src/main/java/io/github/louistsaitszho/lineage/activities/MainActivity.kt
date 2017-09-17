package io.github.louistsaitszho.lineage.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import io.github.louistsaitszho.lineage.ItemListUnit
import io.github.louistsaitszho.lineage.R
import io.github.louistsaitszho.lineage.RecyclerViewAdapter

class MainActivity : AppCompatActivity() {


    //This is the way to declare Static final in Kotlin
    companion object{
        const val MEDIUM = "Medium"
        const val LARGE = "Large"
        const val NO_THUMBNAIL = "No Thumbnail"

    }



    private var layoutOption: String? = null

    //RecyclerView
    private var recyclerView: RecyclerView? = null

    //RecyclerView.Adapter
    private var adapter: RecyclerView.Adapter<*>? = null

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
        var someSome: ItemListUnit = ItemListUnit("January","http://flaglane.com/download/german-flag/german-flag-large.png","This is the URL 2")
        var someSome1: ItemListUnit = ItemListUnit("February ","http://flaglane.com/download/german-flag/german-flag-large.png","This is the URL 2")
        var someSome2: ItemListUnit = ItemListUnit("March","https://upload.wikimedia.org/wikipedia/commons/thumb/5/5b/Flag_of_Hong_Kong.svg/2000px-Flag_of_Hong_Kong.svg.png","This is the URL 2")
        var someSome3: ItemListUnit = ItemListUnit("May","http://www.freepngimg.com/download/india/4-2-india-flag-png-hd.png","This is the URL 2")
        var someSome4: ItemListUnit = ItemListUnit("June","https://upload.wikimedia.org/wikipedia/commons/1/17/Flag_of_Mexico.png","This is the URL 2")
        var someSome5: ItemListUnit = ItemListUnit("July","https://drive.google.com/open?id=0BzUGskuag7oNZHR2TFNsbnFxaU0","This is the URL 2")
        var someSome6: ItemListUnit = ItemListUnit("August","https://drive.google.com/open?id=0BzUGskuag7oNZHR2TFNsbnFxaU0","This is the URL 2")
        var someSome7: ItemListUnit = ItemListUnit("September","https://drive.google.com/open?id=0BzUGskuag7oNZHR2TFNsbnFxaU0","This is the URL 2")
        var someSome8: ItemListUnit = ItemListUnit("October","https://drive.google.com/open?id=0BzUGskuag7oNZHR2TFNsbnFxaU0","This is the URL 2")
        var someSome9: ItemListUnit = ItemListUnit("Noviembre","https://drive.google.com/open?id=0BzUGskuag7oNZHR2TFNsbnFxaU0","This is the URL 2")
        var someSome10: ItemListUnit = ItemListUnit("Diciembre","https://drive.google.com/open?id=0BzUGskuag7oNZHR2TFNsbnFxaU0","This is the URL 2")
        var someSome11: ItemListUnit = ItemListUnit("THIS IS A VERY LONG STRING TO CHECK HOW LONG TEXT BEHAVES IN THIS SITUATION. IT'S ALSO FRIDAY TODAY, RAINY DAY","http://flaglane.com/download/german-flag/german-flag-large.png","This is the URL 2")

        //List that will hold the dummy information
        listItems = mutableListOf(someSome, someSome1,someSome2,someSome3,someSome4,someSome5,someSome6, someSome7,someSome8,someSome9,someSome10,someSome11)


        //Instantiating and setting recycler view attributes
        recyclerView = findViewById(R.id.recycler_view)
        (recyclerView)!!.setHasFixedSize(true)
        (recyclerView)!!.setLayoutManager(LinearLayoutManager(this))

        //Instantiating the Adapter with the List, The Context, and the Layout Size Setting
        adapter = RecyclerViewAdapter(listItems, this, layoutOption)

        //Setting the adapter to the Recycler View
        (recyclerView)!!.setAdapter(adapter)
    }
}
