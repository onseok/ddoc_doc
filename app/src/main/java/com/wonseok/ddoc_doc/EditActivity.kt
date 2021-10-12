package com.wonseok.ddoc_doc

import android.app.Dialog
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.Toast
import com.bumptech.glide.Glide
import com.kakao.sdk.user.UserApiClient
import com.wonseok.ddoc_doc.databinding.ActivityEditBinding
import kotlinx.android.synthetic.main.fragment_history.*

class EditActivity : AppCompatActivity() {

    val binding by lazy { ActivityEditBinding.inflate(layoutInflater) }

    val mainActivity = MainActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.editProfileBackButton.setOnClickListener {
            onBackPressed()
        }

        binding.editProfileLogoutImageButton.setOnClickListener {
            logoutDialog()
        }

        updateProfile()
    }

    fun logoutDialog() {
        val dialog = Dialog(this)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // 백그라운드 컬러 투명 (이걸 해줘야 background가 설정해준 모양으로 변함)
        dialog.setContentView(R.layout.logout_dialog)
        dialog.setCancelable(false)    // 다이얼로그외에 다른 화면을 눌렀을 때 나가는 것 방지

        var logoutYesButton : Button? = dialog?.findViewById(R.id.logoutYesButton) // 버튼을 dialog에 연결
        var logoutNoButton : Button? = dialog?.findViewById(R.id.logoutNoButton)

        logoutYesButton?.setOnClickListener{
            dialog?.dismiss()
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Toast.makeText(this, "로그아웃 실패 $error", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(this, "로그아웃 성공", Toast.LENGTH_SHORT).show()
                }
                mainActivity.updateMyPageFragmentUI()
            }
        }
        logoutNoButton?.setOnClickListener{
            dialog?.dismiss()
        }

        dialog?.show()
    }

    fun updateProfile() {
        UserApiClient.instance.me { user, error ->
            binding.editProfileNickNameDetailTextView.text = "${user?.kakaoAccount?.profile?.nickname}"
            Glide.with(this).load("${user?.kakaoAccount?.profile?.profileImageUrl}").into(binding.editProfileImageView)
        }
    }
}