package com.wonseok.ddoc_doc

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class IntroActivity : BaseActivity() {

    private val permissions: Array<String> by lazy {
        // 모든 권한 배열
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CALL_PHONE
            // TODO 나머지 권한도 넣어주기
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        supportActionBar?.hide()

        val isAllPermissionGranted = permissions.all {
            checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED
        }

        // 모든 권한이 설정되어있을 경우에
        if(isAllPermissionGranted) {
            Handler(Looper.getMainLooper()).postDelayed({
                requirePermissions(
                    permissions, PermissionActivity.PERMISSION_REQUEST_CODE
                )
            }, 2500) // 인트로 화면 로딩 2.5초 후 곧바로 메인액티비티로 전환
        }

        // 모든 권한이 설정되어있지 않을 경우에
        else {
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this@IntroActivity, PermissionActivity::class.java)
                startActivity(intent)
            }, 2500) // 인트로 화면 로딩 2.5초 후 퍼미션 액티비티로 전환
        }
    }

    override fun permissionGranted(requestCode: Int) {
        if (requestCode == PermissionActivity.PERMISSION_REQUEST_CODE) {
            val intent = Intent(this@IntroActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun permissionDenied(requestCode: Int) {
        if (requestCode == PermissionActivity.PERMISSION_REQUEST_CODE) {
            val intent = Intent(this@IntroActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}