package com.wonseok.ddoc_doc

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wonseok.ddoc_doc.databinding.ActivityDetailBinding
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }

    var phoneNum: String = "tel:"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.hospitalDetailNameTextView.text = intent.getStringExtra("hospitalName")
        binding.hospitalDetailTitleTextView.text = intent.getStringExtra("hospitalName")
        binding.hospitalAddressTextView.text = intent.getStringExtra("addr")

        binding.hospitalDetailBackButton.setOnClickListener {
            onBackPressed()
        }

        binding.hospitalDetailCallButton.setOnClickListener {
            phoneNum += intent.getStringExtra("telno")
            var intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse(phoneNum)
            startActivity(intent)

            phoneNum = "tel:"
        }

        setBuiltYear()
    }

    private fun setBuiltYear() {

        // 현재시간을 가져오기
        val long_now = System.currentTimeMillis()

        // 현재 시간을 Date 타입으로 변환
        val t_date = Date(long_now)

        // 날짜, 시간을 가져오고 싶은 형태 선언
        val t_dateFormat = SimpleDateFormat("yyyy", Locale("ko", "KR"))

        // 현재 시간을 dateFormat 에 선언한 형태의 String 으로 변환
        val str_date = t_dateFormat.format(t_date)

        val year = str_date.toInt() - (intent.getStringExtra("estbDd")?.slice(0..3)?.toInt() ?: 0) + 1
        
        binding.hospitalDetailYearTextView.text = "설립 ${year}년"
    }
}