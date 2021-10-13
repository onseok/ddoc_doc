package com.wonseok.ddoc_doc

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.wonseok.ddoc_doc.api.HospitalOpenApi
import com.wonseok.ddoc_doc.api.HospitalOpenService
import com.wonseok.ddoc_doc.data.Hospitals
import com.wonseok.ddoc_doc.databinding.ActivitySearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchActivity : AppCompatActivity() {

    val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    private val editText: EditText by lazy {
        findViewById(R.id.searchActivityEditTextView)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        autoKeyboardShow()


        binding.searchActivityBackButton.setOnClickListener {
            onBackPressed()
        }

        // 현위치 검색어 가져오기, 문자열 슬라이싱
        val currentPlace = intent.getStringExtra("currentPlace")?.substring(6) ?: ""

        loadHospitals(currentPlace)

        initViews()

    }

    private fun initViews() {
        binding.hereLocationButton.text = intent.getStringExtra("currentPlace") ?: ""
        binding.visitDayButton.text = intent.getStringExtra("currentDate") ?: ""
    }

    private fun loadHospitals(currentPlace: String) {
        var gson = GsonBuilder().setLenient().create()

        val retrofit = Retrofit.Builder()
            .baseUrl(HospitalOpenApi.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val hospitalOpenService = retrofit.create(HospitalOpenService::class.java)

        hospitalOpenService
            .getHospitals((getString(R.string.hospitalInfoApiKey)), 1, 10, currentPlace, 3000)
            .enqueue(object : Callback<Hospitals> {

                override fun onFailure(call: Call<Hospitals>, t: Throwable) {
                    Toast.makeText(baseContext, "서버에서 데이터를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", t.toString())
                }

                override fun onResponse(call: Call<Hospitals>, response: Response<Hospitals>) {
                    showHospitals(response.body() as Hospitals)

                    response.body()?.let {
                        Log.d("TAG", it.toString())

                        it.response.body.items.item.forEach { hospital ->
                            Log.d("TAG", hospital.toString())
                        }

                    }
                }

            })

    }

    private fun showHospitals(hospitals: Hospitals) {

    }

    private fun autoKeyboardShow() {
        //EditText에 포커싱
        editText.requestFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        hideKeyboard()
    }

    override fun onStop() {
        super.onStop()
        hideKeyboard()
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        hideKeyboard()
    }
}