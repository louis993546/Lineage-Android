package io.github.louistsaitszho.lineage.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import io.github.louistsaitszho.lineage.ItemListUnit
import io.github.louistsaitszho.lineage.R
import io.github.louistsaitszho.lineage.RecyclerViewAdapter

class MainActivity : AppCompatActivity() {

    //RecyclerView
    private var recyclerView: RecyclerView? = null

    //RecyclerView.Adapter
    private var adapter: RecyclerView.Adapter<*>? = null

    //List holding the dummy information
    private var listItems: MutableList<ItemListUnit>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //POOR IMPLEMENTATION DUE TO MY POOR KOTLIN SKILLS
        var someSome: ItemListUnit = ItemListUnit("January","this is the URL 1","This is the URL 2")
        var someSome1: ItemListUnit = ItemListUnit("February ","this is the URL 1","This is the URL 2")
        var someSome2: ItemListUnit = ItemListUnit("March","this is the URL 1","This is the URL 2")
        var someSome3: ItemListUnit = ItemListUnit("May","this is the URL 1","This is the URL 2")
        var someSome4: ItemListUnit = ItemListUnit("June","this is the URL 1","This is the URL 2")
        var someSome5: ItemListUnit = ItemListUnit("July","this is the URL 1","This is the URL 2")
        var someSome6: ItemListUnit = ItemListUnit("August","this is the URL 1","This is the URL 2")
        var someSome7: ItemListUnit = ItemListUnit("September","this is the URL 1","This is the URL 2")
        var someSome8: ItemListUnit = ItemListUnit("October","this is the URL 1","This is the URL 2")
        var someSome9: ItemListUnit = ItemListUnit("Noviembre","this is the URL 1","This is the URL 2")
        var someSome10: ItemListUnit = ItemListUnit("Diciembre","this is the URL 1","This is the URL 2")
        var someSome11: ItemListUnit = ItemListUnit("THIS IS A VERY LONG STRING TO CHECK HOW LONG TEXT BEHAVES IN THIS SITUATION. IT'S ALSO FRIDAY TODAY, RAINY DAY","this is the URL 1","This is the URL 2")


        //List that will hold the dummy information
        listItems = mutableListOf(someSome, someSome1,someSome2,someSome3,someSome4,someSome5,someSome6, someSome7,someSome8,someSome9,someSome10,someSome11)

        //Instantiating and setting recycler view attributes
        recyclerView = findViewById(R.id.recycler_view)
        (recyclerView)!!.setHasFixedSize(true)
        (recyclerView)!!.setLayoutManager(LinearLayoutManager(this))

        //Instantiating the Adapter with the List, The Context, and the Visualisation Setting
        adapter = RecyclerViewAdapter(listItems, this,"NO_THUMBNAIL")

        //Setting the adapter to the Recycler View
        (recyclerView)!!.setAdapter(adapter)


    }
}
