package com.wonseok.ddoc_doc.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import com.wonseok.ddoc_doc.MainActivity
import com.wonseok.ddoc_doc.R
import com.wonseok.ddoc_doc.SecondActivity
import com.wonseok.ddoc_doc.databinding.FragmentMyPageBinding
import com.wonseok.ddoc_doc.databinding.FragmentSearchBinding
import com.wonseok.ddoc_doc.databinding.LoginBottomSheetDialogBinding
import kotlinx.android.synthetic.main.login_bottom_sheet_dialog.*

class LoginBottomSheet : BottomSheetDialogFragment() {

    // 바인딩 객체 타입에 ?를 붙여서 null을 허용 해줘야한다. ( onDestroy 될 때 완벽하게 제거를 하기위해 )
    private var mBinding: LoginBottomSheetDialogBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = LoginBottomSheetDialogBinding.inflate(inflater, container, false)


        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(requireActivity(), "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(requireActivity(), "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(requireActivity(), "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT)
                            .show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(requireActivity(), "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(requireActivity(), "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(requireActivity(), "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT)
                            .show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(requireActivity(), "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(requireActivity(), "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(requireActivity(), "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            } else if (token != null) {
                Toast.makeText(requireActivity(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireActivity(), SecondActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }
        }


        binding.kakaoLogoImageView.setOnClickListener {
            if (LoginClient.instance.isKakaoTalkLoginAvailable(requireActivity())) {
                LoginClient.instance.loginWithKakaoTalk(requireActivity(), callback = callback)
            } else {
                LoginClient.instance.loginWithKakaoAccount(requireActivity(), callback = callback)
            }
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        view?.findViewById<Button>(R.id.button_bottom_sheet)?.setOnClickListener {
//            dismiss()
//        }

    }
    
    // LoginBottomSheet 둥글게
    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }

// 프래그먼트가 destroy (파괴) 될때..
    override fun onDestroyView() {
    // onDestroyView 에서 binding class 인스턴스 참조를 정리해주어야 한다.
    mBinding = null
    super.onDestroyView()
    }
}


