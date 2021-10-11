package com.wonseok.ddoc_doc

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.wonseok.ddoc_doc.databinding.ActivityPermissionBinding

class PermissionActivity : BaseActivity() {

    val binding by lazy { ActivityPermissionBinding.inflate(layoutInflater) }

    private val permissions: Array<String> by lazy {
        // 위치, 전화 권한 배열
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CALL_PHONE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 모든 권한이 설정되었는지 확인하는 변수
        val isAllPermissionGranted = permissions.all {
            checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED
        }
        
        // 권한 설정이 안되었을 때에만 권한 액티비티가 보이도록
        if(!isAllPermissionGranted) {
            binding.checkPermissionButton.setOnClickListener {
                requirePermissions(
                    permissions, PERMISSION_REQUEST_CODE
                )
            }
        }
        
        // 권한 설정이 최초에 한 번 완료되었다면, 인트로 액티비티에서 권한 액티비티가 아니라 메인 액티비티로 바로 이동  
        else {
            requirePermissions(
                permissions, PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun permissionGranted(requestCode: Int) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            val intent = Intent(this@PermissionActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun permissionDenied(requestCode: Int) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            val intent = Intent(this@PermissionActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }


    companion object {
        const val PERMISSION_REQUEST_CODE = 99
    }
}