package com.l0122138.ridlo.ppab_09.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.l0122138.ridlo.ppab_09.R
import com.l0122138.ridlo.ppab_09.databinding.ActivityVoteCatsBinding
import com.l0122138.ridlo.ppab_09.model.VoteRequest
import com.l0122138.ridlo.ppab_09.model.VoteResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VoteCatsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVoteCatsBinding
    private var currentCatImageId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoteCatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadRandomCat()
        setupButtons()
    }

    private fun loadRandomCat() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.getRandomCatImage()
                if (response.isSuccessful && response.body() != null && response.body()!!
                        .isNotEmpty()
                ) {
                    val imageInfo = response.body()!![0]  // Ambil gambar pertama dari response
                    currentCatImageId = imageInfo.id
                    withContext(Dispatchers.Main) {
                        Glide.with(this@VoteCatsActivity)
                            .load(imageInfo.url)
                            .into(binding.imageToVoteOn)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext,
                            "Gagal memuat gambar",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "Error: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun setupButtons() {
        binding.btnVoteUp.setOnClickListener {
            voteOnCat(1)
        }

        binding.btnVoteDown.setOnClickListener {
            voteOnCat(-1)
        }

        binding.btnShowHistory.setOnClickListener {
            showHistoricVotes()
        }
    }

    private fun voteOnCat(value: Int) {
        val voteRequest = VoteRequest(
            image_id = currentCatImageId ?: "",
            sub_id = "user-123",  // ID pengguna unik untuk demo
            value = value
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.postVote(voteRequest)
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext,
                            "Vote berhasil dikirim!",
                            Toast.LENGTH_SHORT
                        ).show()
                        loadRandomCat()  // Muat gambar kucing baru setelah voting
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext,
                            "Gagal mengirim vote",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "Error: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun showHistoricVotes() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.getVotes(
                    subId = "user-123",
                    limit = 10,
                    order = "DESC"
                )  // misal sub_id sebagai "user-123"
                if (response.isSuccessful && response.body() != null) {
                    // Konversi tanggal dari format ISO 8601 jika diperlukan atau simpan data
                    val votes = response.body()!!
                    withContext(Dispatchers.Main) {
                        val successfulMessage =
                            "Histori vote diterima: ${votes.size} entri terakhir."
                        Toast.makeText(applicationContext, successfulMessage, Toast.LENGTH_LONG)
                            .show()
                    }

                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext,
                            "Gagal mengambil histori",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        applicationContext,
                        "Error: ${e.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

