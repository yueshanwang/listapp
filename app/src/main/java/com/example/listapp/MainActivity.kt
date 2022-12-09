package com.example.listapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import java.io.IOException
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerview: RecyclerView

    //sort by list id, then name
    class ListData(val listID: Int, val name: String, val id: Int) : Comparable<ListData> {
        override fun compareTo(other: ListData) = compareValuesBy(this, other, { it.listID }, { it.name })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fetch = fetch()
        fetch.start()//start thread to download JSON
        fetch.join()//wait till the JSON has finished downloading
        val data = fetch.data
        val jArray = JSONArray(data)
        val list = ArrayList<ListData>()

        for (i in 0 until jArray.length()) {

            val jsonObject = jArray.getJSONObject(i)

            //not blank and not null name
            if(!jsonObject.get("name").equals("") && !jsonObject.get("name").equals(null)){
                val toAdd = ListData(jsonObject.get("listId") as Int, jsonObject.get("name") as String, jsonObject.get("id") as Int)
                list.add(toAdd)
            }
        }
        //send sorted list to adapter
        val adapter = MyAdapter(list.sorted())
        this.recyclerview = findViewById(R.id.fetchList)
        this.recyclerview.layoutManager = LinearLayoutManager(this)
        this.recyclerview.adapter = adapter
    }

    class fetch: Thread() {

        var data: String = ""
        override fun run(){
            try{
                this.data  = URL("https://fetch-hiring.s3.amazonaws.com/hiring.json").readText()
            } catch(e: IOException){
                e.printStackTrace()
            }
        }
    }
}