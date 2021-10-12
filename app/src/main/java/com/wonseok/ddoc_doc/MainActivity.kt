package com.wonseok.ddoc_doc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import com.wonseok.ddoc_doc.databinding.ActivityMainBinding
import com.wonseok.ddoc_doc.fragment.HistoryFragment
import com.wonseok.ddoc_doc.fragment.MyPageFragment
import com.wonseok.ddoc_doc.fragment.SearchFragment


class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    //뒤로가기 연속 클릭 대기 시간
    private var backPressedTime: Long = 0

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

        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
            }
            else if (tokenInfo != null) {
                Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }
        }



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
                when (it.itemId) {
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