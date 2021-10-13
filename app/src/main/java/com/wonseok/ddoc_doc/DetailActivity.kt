package com.wonseok.ddoc_doc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wonseok.ddoc_doc.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.hospitalDetailNameTextView.text = intent.getStringExtra("hospitalName")
        binding.hospitalDetailTitleTextView.text = intent.getStringExtra("hospitalName")
        binding.hospitalDetailBackButton.setOnClickListener {
            onBackPressed()
        }
    }
}