package com.elior.coroutines.PagesPackage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import com.elior.coroutines.CustomAdaptersPackage.CustomAdaptersPart
import com.elior.coroutines.ModelsPackage.MovieModel
import com.elior.coroutines.ModelsPackage.Results
import com.elior.coroutines.R
import com.elior.coroutines.RetrofitPackage.WebAccess
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadParts()
    }

    private fun loadParts() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val webResponse = WebAccess.partsApi.getPartsAsync(
                    "/3/search/movie?/&query=q&api_key=" + getString(R.string.api_key)
                ).await()
                if (webResponse.isSuccessful) {
                    val partList: MovieModel? = webResponse.body()
                    partList?.results?.toList()?.let { initRecyclerView(it) }
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Error ${webResponse.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: IOException) {
                Toast.makeText(this@MainActivity, "Exception ${e.message}", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun initRecyclerView(dataList: List<Results>) {
        rv_parts.adapter = CustomAdaptersPart(dataList)
        rv_parts.layoutManager = LinearLayoutManager(this)
    }

}