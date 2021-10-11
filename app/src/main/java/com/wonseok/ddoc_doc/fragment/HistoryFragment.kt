package com.wonseok.ddoc_doc.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import com.wonseok.ddoc_doc.R
import com.wonseok.ddoc_doc.databinding.FragmentHistoryBinding
import com.wonseok.ddoc_doc.databinding.FragmentSearchBinding

class HistoryFragment : Fragment() {

    private var binding: FragmentHistoryBinding? = null

    // 뷰가 생성되었을 때 - 프레그먼트와 레이아웃을 연결시켜주는 부분
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(
            inflater,
            container, false
        )


        loginDialog()

        return binding!!.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun loginDialog() {
        val dialog = context?.let { Dialog(it) }

        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // 백그라운드 컬러 투명 (이걸 해줘야 background가 설정해준 모양으로 변함)
        dialog?.setContentView(R.layout.login_dialog)
        dialog?.setCancelable(false)    // 다이얼로그외에 다른 화면을 눌렀을 때 나가는 것 방지

        var loginYesButton : Button? = dialog?.findViewById(R.id.loginYesButton) // 버튼을 dialog에 연결
        var loginNoButton : Button? = dialog?.findViewById(R.id.loginNoButton)

        loginYesButton?.setOnClickListener{
//            dialog.dismiss()
        }
        loginNoButton?.setOnClickListener{
//            dialog.dismiss()
        }

        dialog?.show()
    }

    companion object {
        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }
}