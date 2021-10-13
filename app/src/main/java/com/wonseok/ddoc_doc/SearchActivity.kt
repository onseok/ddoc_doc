package com.wonseok.ddoc_doc

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.wonseok.ddoc_doc.adapters.HospitalAdapter
import com.wonseok.ddoc_doc.api.HospitalOpenApi
import com.wonseok.ddoc_doc.api.HospitalOpenService
import com.wonseok.ddoc_doc.data.Hospitals
import com.wonseok.ddoc_doc.data.Item
import com.wonseok.ddoc_doc.data.ListLayout
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
    private val listItems = arrayListOf<Item>()   // 리사이클러 뷰 아이템
    private val listAdapter = HospitalAdapter(listItems)    // 리사이클러 뷰 어댑터

    var hospitalName = ""
    var currentPlace = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        autoKeyboardShow()


        binding.searchActivityBackButton.setOnClickListener {
            onBackPressed()
        }

        initViews()
        initHospitalRecyclerView()

        // 실시간 검색 처리
        binding.searchActivityEditTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                hospitalName = binding.searchActivityEditTextView.text.toString()
                currentPlace = binding.hereLocationButton.text.substring(6)
                Log.d("HospitalName", hospitalName)
                loadHospitals(currentPlace, hospitalName)
            }
            override fun onTextChanged(str: CharSequence?, p1: Int, p2: Int, p3: Int) {
                hospitalName = binding.searchActivityEditTextView.text.toString()
                currentPlace = binding.hereLocationButton.text.substring(6)
                Log.d("HospitalName", hospitalName)
                loadHospitals(currentPlace, hospitalName)
            }
        })

    }

    private fun initViews() {
        binding.hereLocationButton.text = intent.getStringExtra("currentPlace") ?: ""
        binding.visitDayButton.text = intent.getStringExtra("currentDate") ?: ""
    }


    private fun initHospitalRecyclerView() {
        binding.searchActivityRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.searchActivityRecyclerView.adapter = listAdapter

        // 리스트 아이템 클릭 시 해당 정보를 가지고 새로운 액티비티 띄우기
        listAdapter.setItemClickListener(object: HospitalAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
//                val mapPoint = MapPoint.mapPointWithGeoCoord(listItems[position].y, listItems[position].x)
//                binding.mapView.setMapCenterPointAndZoomLevel(mapPoint, 1, true)

                val hospitalName = listItems[position].yadmNm
                val XPos = listItems[position].XPos.toString()
                val YPos = listItems[position].YPos.toString()
                val addr = listItems[position].addr
                val clCd  = listItems[position].clCd.toString()
                val clCdNm = listItems[position].clCdNm
                val cmdcGdrCnt = listItems[position].cmdcGdrCnt.toString()
                val cmdcIntnCnt = listItems[position].cmdcIntnCnt.toString()
                val cmdcResdntCnt = listItems[position].cmdcResdntCnt.toString()
                val cmdcSdrCnt = listItems[position].cmdcSdrCnt.toString()
                val detyGdrCnt = listItems[position].detyGdrCnt.toString()
                val detyIntnCnt = listItems[position].detyIntnCnt.toString()
                val detyResdntCnt = listItems[position].detyResdntCnt.toString()
                val detySdrCnt = listItems[position].detySdrCnt.toString()
                val drTotCnt = listItems[position].drTotCnt.toString()
                val emdongNm = listItems[position].emdongNm
                val estbDd = listItems[position].estbDd.toString()
                val hospUrl = listItems[position].hospUrl
                val mdeptGdrCnt = listItems[position].mdeptGdrCnt.toString()
                val mdeptIntnCnt = listItems[position].mdeptIntnCnt.toString()
                val mdeptResdntCnt = listItems[position].mdeptResdntCnt.toString()
                val mdeptSdrCnt = listItems[position].mdeptSdrCnt.toString()
                val postNo = listItems[position].postNo.toString()
                val sgguCd = listItems[position].sgguCd.toString()
                val sgguCdNm = listItems[position].sgguCdNm
                val sidoCd = listItems[position].sidoCd.toString()
                val sidoCdNm = listItems[position].sidoCdNm
                val telno = listItems[position].telno
                val ykiho= listItems[position].ykiho
                val intent = Intent(this@SearchActivity, DetailActivity::class.java)

                with(intent) {
                    putExtra("hospitalName", hospitalName)
                    putExtra("XPos", XPos)
                    putExtra("YPos", YPos)
                    putExtra("addr", addr)
                    putExtra("clCd", clCd)
                    putExtra("clCdNm", clCdNm)
                    putExtra("cmdcGdrCnt", cmdcGdrCnt)
                    putExtra("cmdcIntnCnt", cmdcIntnCnt)
                    putExtra("cmdcResdntCnt", cmdcResdntCnt)
                    putExtra("cmdcSdrCnt", cmdcSdrCnt)
                    putExtra("detyGdrCnt", detyGdrCnt)
                    putExtra("detyIntnCnt", detyIntnCnt)
                    putExtra("detyResdntCnt", detyResdntCnt)
                    putExtra("detySdrCnt", detySdrCnt)
                    putExtra("drTotCnt", drTotCnt)
                    putExtra("emdongNm", emdongNm)
                    putExtra("estbDd", estbDd)
                    putExtra("hospUrl", hospUrl)
                    putExtra("mdeptGdrCnt", mdeptGdrCnt)
                    putExtra("mdeptIntnCnt", mdeptIntnCnt)
                    putExtra("mdeptResdntCnt", mdeptResdntCnt)
                    putExtra("mdeptSdrCnt", mdeptSdrCnt)
                    putExtra("postNo", postNo)
                    putExtra("sgguCd", sgguCd)
                    putExtra("sgguCdNm", sgguCdNm)
                    putExtra("sidoCd", sidoCd)
                    putExtra("sidoCdNm", sidoCdNm)
                    putExtra("telno", telno)
                    putExtra("ykiho", ykiho)
                }
                startActivity(intent)
            }
        })
    }

    private fun loadHospitals(currentPlace: String, hospitalName: String) {

        val retrofit = Retrofit.Builder()
            .baseUrl(HospitalOpenApi.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val hospitalOpenService = retrofit.create(HospitalOpenService::class.java)

        hospitalOpenService
            .getHospitals(
                (getString(R.string.hospitalInfoApiKey)),
                1,
                1000,
                currentPlace,
                hospitalName,
                4000
            )
            .enqueue(object : Callback<Hospitals> {

                override fun onFailure(call: Call<Hospitals>, t: Throwable) {
                    // 통신 실패
                    Log.w("LocalSearch", "통신 실패: ${t.message}")
                }

                override fun onResponse(call: Call<Hospitals>, response: Response<Hospitals>) {
                    // 통신 성공
                    response.body()?.let {
                        it.response.body.items.item.forEach { hospital ->
                            Log.d("TAG", hospital.toString())
                        }
                    }

                    // 병원 검색어가 있어야지만 병원 검색 결과 처리 (실시간 처리 때문)
                    if (hospitalName != "") {
                        // 병원 검색 결과 처리 함수
                        showList()
                        showHospitalList(response.body())
                        Log.d("HideShow", "1")
                    }
                    else {
                        hideList()
                        Log.d("HideShow", "3")
                    }
                }

            })

    }

    private fun showHospitalList(searchResult: Hospitals?) {


        listItems.clear() // 리스트 초기화
        for (hospital in searchResult!!.response.body.items.item) {
            // 결과를 리사이클러 뷰에 추가
            val item = Item(
                hospital.XPos,
                hospital.YPos,
                hospital.addr,
                hospital.clCd,
                hospital.clCdNm,
                hospital.cmdcGdrCnt,
                hospital.cmdcIntnCnt,
                hospital.cmdcResdntCnt,
                hospital.cmdcSdrCnt,
                hospital.detyGdrCnt,
                hospital.detyIntnCnt,
                hospital.detyResdntCnt,
                hospital.detySdrCnt,
                hospital.drTotCnt,
                hospital.emdongNm,
                hospital.estbDd,
                hospital.hospUrl,
                hospital.mdeptGdrCnt,
                hospital.mdeptIntnCnt,
                hospital.mdeptResdntCnt,
                hospital.mdeptSdrCnt,
                hospital.postNo,
                hospital.sgguCd,
                hospital.sgguCdNm,
                hospital.sidoCd,
                hospital.sidoCdNm,
                hospital.telno,
                hospital.yadmNm,
                hospital.ykiho
            )
            listItems.add(item)
        }
        listAdapter.notifyDataSetChanged()
    }

    private fun showList() {
        binding.hereLocationButton.visibility = View.GONE
        binding.visitDayButton.visibility = View.GONE
        binding.searchActivitySecondDivider.visibility = View.GONE
        binding.searchActivityRecommendTextView.visibility = View.GONE
        binding.searchActivityRecommendSubtextView.visibility = View.GONE
        binding.firstRecommendButton.visibility = View.GONE
        binding.secondRecommendButton.visibility = View.GONE
        binding.thirdRecommendButton.visibility = View.GONE
        binding.fourthRecommendButton.visibility = View.GONE
        binding.fifthRecommendButton.visibility = View.GONE
        binding.recentSearchKeywordTextView.visibility = View.GONE
        binding.noSearchKeywordTextView.visibility = View.GONE
        binding.searchActivityRecyclerView.visibility = View.VISIBLE
        binding.noneImageView.visibility = View.GONE
        binding.noneFirstTextView.visibility = View.GONE
        binding.noneSecondTextView.visibility = View.GONE
    }

    private fun noneList() {
        binding.hereLocationButton.visibility = View.GONE
        binding.visitDayButton.visibility = View.GONE
        binding.searchActivitySecondDivider.visibility = View.GONE
        binding.searchActivityRecommendTextView.visibility = View.GONE
        binding.searchActivityRecommendSubtextView.visibility = View.GONE
        binding.firstRecommendButton.visibility = View.GONE
        binding.secondRecommendButton.visibility = View.GONE
        binding.thirdRecommendButton.visibility = View.GONE
        binding.fourthRecommendButton.visibility = View.GONE
        binding.fifthRecommendButton.visibility = View.GONE
        binding.recentSearchKeywordTextView.visibility = View.GONE
        binding.noSearchKeywordTextView.visibility = View.GONE
        binding.searchActivityRecyclerView.visibility = View.GONE
        binding.noneImageView.visibility = View.VISIBLE
        binding.noneFirstTextView.visibility = View.VISIBLE
        binding.noneSecondTextView.visibility = View.VISIBLE

    }

    private fun hideList() {
        binding.hereLocationButton.visibility = View.VISIBLE
        binding.visitDayButton.visibility = View.VISIBLE
        binding.searchActivitySecondDivider.visibility = View.VISIBLE
        binding.searchActivityRecommendTextView.visibility = View.VISIBLE
        binding.searchActivityRecommendSubtextView.visibility = View.VISIBLE
        binding.firstRecommendButton.visibility = View.VISIBLE
        binding.secondRecommendButton.visibility = View.VISIBLE
        binding.thirdRecommendButton.visibility = View.VISIBLE
        binding.fourthRecommendButton.visibility = View.VISIBLE
        binding.fifthRecommendButton.visibility = View.VISIBLE
        binding.recentSearchKeywordTextView.visibility = View.VISIBLE
        binding.noSearchKeywordTextView.visibility = View.VISIBLE
        binding.searchActivityRecyclerView.visibility = View.GONE
        binding.noneImageView.visibility = View.GONE
        binding.noneFirstTextView.visibility = View.GONE
        binding.noneSecondTextView.visibility = View.GONE
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