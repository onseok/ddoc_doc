package com.wonseok.ddoc_doc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.wonseok.ddoc_doc.adapters.MainViewPagerAdapter
import com.wonseok.ddoc_doc.databinding.ActivityMainBinding
import com.wonseok.ddoc_doc.databinding.ActivityPermissionBinding
import com.wonseok.ddoc_doc.fragment.HistoryFragment
import com.wonseok.ddoc_doc.fragment.MyPageFragment
import com.wonseok.ddoc_doc.fragment.SearchFragment


class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    //뒤로가기 연속 클릭 대기 시간
    private var backPressedTime : Long = 0

    private lateinit var searchFragment: SearchFragment
    private lateinit var historyFragment: HistoryFragment
    private lateinit var myPageFragment: MyPageFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        searchFragment = SearchFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.frameLayout, searchFragment).commit()

        onNavigationItemSelectedListener

    }



    override fun onBackPressed() {
        // 2초내 다시 클릭하면 앱 종료
        if (System.currentTimeMillis() - backPressedTime < 2000) {
            moveTaskToBack(true)
            finish()
            android.os.Process.killProcess(android.os.Process.myPid())
            return
        }
        // 처음 클릭 메시지
        Toast.makeText(this, "앱을 종료합니다.", Toast.LENGTH_SHORT).show()
        backPressedTime = System.currentTimeMillis()
    }

    private val onNavigationItemSelectedListener by lazy {
        binding.mainBottomNavigationView
        .setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_search -> {
                    searchFragment = SearchFragment.newInstance()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, searchFragment).commit()
                }
                R.id.menu_history -> {
                    historyFragment = HistoryFragment.newInstance()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, historyFragment).commit()
                }
                R.id.menu_my_page -> {
                    myPageFragment = MyPageFragment.newInstance()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, myPageFragment).commit()
                }
            }
            true
        }
    }
}