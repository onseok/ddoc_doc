package com.wonseok.ddoc_doc.fragment

import android.content.Intent
import android.os.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.wonseok.ddoc_doc.EditActivity
import com.wonseok.ddoc_doc.R
import com.wonseok.ddoc_doc.SearchActivity
import com.wonseok.ddoc_doc.adapters.MainViewPagerAdapter
import com.wonseok.ddoc_doc.databinding.FragmentSearchBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class SearchFragment : Fragment() {

    private var binding: FragmentSearchBinding? = null

    private var numBanner = 2
    private var currentPosition = 0
    private var myHandler = MyHandler()
    private val intervalTime = 3500.toLong() // 몇초 간격으로 페이지를 넘길것인지 (3500 = 3.5초)


    // 뷰가 생성되었을 때 - 프레그먼트와 레이아웃을 연결시켜주는 부분
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding?.let {
            Glide.with(this).load(R.drawable.main_animation_icon).into(it.mainAnimationGifImageView)
        }

        binding?.mainBannerViewPager?.adapter = MainViewPagerAdapter(getBannerList())
        binding?.mainBannerViewPager?.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding?.mainBannerViewPager?.setCurrentItem(currentPosition, false) // 현재 위치를 지정

        // 현재 몇 번째 배너인지 보여주는 숫자를 변경함
        val apply = binding?.mainBannerViewPager?.apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    when (state) {
                        // 뷰페이저에서 손 떼었을때 / 뷰페이저 멈춰있을 때
                        ViewPager2.SCROLL_STATE_IDLE -> autoScrollStart(intervalTime)

                        // 뷰페이저 움직이는 중
                        ViewPager2.SCROLL_STATE_DRAGGING -> autoScrollStop()
                    }
                }

            })
        }

        binding?.mainBannerViewPager?.setCurrentItem(++currentPosition, true)

        setCurrentDate()

        binding?.mainSearchButton?.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }


        return binding!!.root
    }

    private fun setCurrentDate() {

        // 현재시간을 가져오기
        val long_now = System.currentTimeMillis()

        // 현재 시간을 Date 타입으로 변환
        val t_date = Date(long_now)

        // 날짜, 시간을 가져오고 싶은 형태 선언
        val t_dateFormat = SimpleDateFormat("yy/MM/dd (E)", Locale("ko", "KR"))

        // 현재 시간을 dateFormat 에 선언한 형태의 String 으로 변환
        val str_date = t_dateFormat.format(t_date)

        binding?.visitDayButton?.text = str_date

    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun getBannerList(): ArrayList<Int> {
        return arrayListOf(R.drawable.main_banner_1, R.drawable.main_banner_2)
    }

    private fun autoScrollStart(intervalTime: Long) {
        myHandler.removeMessages(0) // 이거 안하면 핸들러가 1개, 2개, 3개 ... n개 만큼 계속 늘어남
        myHandler.sendEmptyMessageDelayed(0, intervalTime) // intervalTime 만큼 반복해서 핸들러를 실행하게 함
    }

    private fun autoScrollStop() {
        myHandler.removeMessages(0) // 핸들러를 중지시킴
    }

    private inner class MyHandler : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            if (msg.what == 0) {
                binding?.mainBannerViewPager?.setCurrentItem(++currentPosition, true) // 다음 페이지로 이동
                autoScrollStart(intervalTime) // 스크롤을 계속 이어서 한다.
            }
        }
    }

    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }
}