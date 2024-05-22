package com.l0122138.ridlo.ppab_09.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.l0122138.ridlo.ppab_09.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadRandomCatImage()

        binding.btnOpenGallery.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }

        binding.btnVoteCats.setOnClickListener {
            val intent = Intent(this, VoteCatsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadRandomCatImage() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.getRandomCatImage()
                if (response.isSuccessful && response.body() != null && response.body()!!.isNotEmpty()) {
                    val imageInfo = response.body()!![0]
                    withContext(Dispatchers.Main) {
                        Glide.with(this@MainActivity)
                            .load(imageInfo.url)
                            .into(binding.imageRandomCat)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Failed to load image", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

