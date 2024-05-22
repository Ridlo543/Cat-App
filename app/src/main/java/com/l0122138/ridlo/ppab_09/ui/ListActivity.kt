package com.l0122138.ridlo.ppab_09.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.l0122138.ridlo.ppab_09.databinding.ActivityListBinding
import com.l0122138.ridlo.ppab_09.model.Cat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private lateinit var catAdapter: CatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        loadCats()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        catAdapter = CatAdapter(listOf())
        binding.recyclerView.adapter = catAdapter
    }

    private fun loadCats() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = try {
                RetrofitInstance.api.getCats()
            } catch(e: Exception) {
                e.printStackTrace()
                listOf<Cat>()
            }
            if (response.isNotEmpty()) {
                withContext(Dispatchers.Main) {
                    catAdapter = CatAdapter(response)
                    binding.recyclerView.adapter = catAdapter
                }
            }
        }
    }
}
