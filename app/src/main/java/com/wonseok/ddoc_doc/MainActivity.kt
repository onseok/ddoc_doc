package com.wonseok.ddoc_doc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.kakao.sdk.user.UserApiClient
import com.wonseok.ddoc_doc.databinding.ActivityMainBinding
import com.wonseok.ddoc_doc.fragment.HistoryFragment
import com.wonseok.ddoc_doc.fragment.MyPageFragment
import com.wonseok.ddoc_doc.fragment.SearchFragment
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_my_page.*


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

        // 처음 앱 실행 시 로그인 되었는지 확인하는 부분
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            // 한번도 로그인 안했다면
            if (error != null) {
//                Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
            }
            // 한번 로그인 되었다면
            else if (tokenInfo != null) {
                UserApiClient.instance.me { user, error ->
                    Toast.makeText(this, "${user?.kakaoAccount?.profile?.nickname}님 환영합니다!", Toast.LENGTH_SHORT).show()
                }
            }
        }



    }

    fun updateHistoryFragmentUI() {

        historyFragment = HistoryFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, historyFragment).commit()

        UserApiClient.instance.me { user, error ->
            if (user?.kakaoAccount?.profile?.nickname == null) {
                historyFragment.loginDialog()
            }
            else {
                historyFragment.fragmentHistoryBannerImageView.visibility = View.VISIBLE
                historyFragment.fragmentHistoryUserNameTextView.text = "${user?.kakaoAccount?.profile?.nickname}님"
                historyFragment.fragmentHistoryUserNameTextView.visibility = View.VISIBLE
            }
        }
    }

    fun updateMyPageFragmentUI() {
        myPageFragment = MyPageFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, myPageFragment).commit()

        UserApiClient.instance.me { user, error ->
            if (user?.kakaoAccount?.profile?.nickname == null) {
                myPageFragment.myPageTextView.visibility = View.VISIBLE
                myPageFragment.myPageLoginTextView.visibility = View.VISIBLE
                myPageFragment.myPageEditTextView.visibility = View.GONE
            }
            else {
                myPageFragment.myPageTextView.visibility = View.VISIBLE
                myPageFragment.myPageTextView.text = "${user?.kakaoAccount?.profile?.nickname}"
                myPageFragment.myPageEditTextView.visibility = View.VISIBLE
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

    val onNavigationItemSelectedListener by lazy {
        binding.mainBottomNavigationView
            .setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_search -> {
                        searchFragment = SearchFragment.newInstance()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, searchFragment).commit()
                    }
                    R.id.menu_history -> {
                        updateHistoryFragmentUI()
                    }
                    R.id.menu_my_page -> {
                        updateMyPageFragmentUI()
                    }
                }
                true
            }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}