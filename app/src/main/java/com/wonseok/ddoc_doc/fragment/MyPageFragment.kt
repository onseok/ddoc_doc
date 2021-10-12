package com.wonseok.ddoc_doc.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wonseok.ddoc_doc.EditActivity
import com.wonseok.ddoc_doc.databinding.FragmentMyPageBinding

class MyPageFragment : Fragment() {

    private var binding: FragmentMyPageBinding? = null
    private val bottomSheet = LoginBottomSheet()

    // 뷰가 생성되었을 때 - 프레그먼트와 레이아웃을 연결시켜주는 부분
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(
            inflater,
            container, false
        )

        binding!!.myPageEditTextView.setOnClickListener {
            val intent = Intent(getActivity(), EditActivity::class.java)
            startActivity(intent)
        }

        binding!!.myPageLoginTextView.setOnClickListener {
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }

        return binding!!.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(): MyPageFragment {
            return MyPageFragment()
        }
    }
}