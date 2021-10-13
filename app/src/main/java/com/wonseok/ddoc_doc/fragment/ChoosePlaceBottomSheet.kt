package com.wonseok.ddoc_doc.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wonseok.ddoc_doc.R
import com.wonseok.ddoc_doc.databinding.FragmentChoosePlaceBottomSheetBinding


class ChoosePlaceBottomSheet : BottomSheetDialogFragment() {

    // 바인딩 객체 타입에 ?를 붙여서 null을 허용 해줘야한다. ( onDestroy 될 때 완벽하게 제거를 하기위해 )
    private var mBinding: FragmentChoosePlaceBottomSheetBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentChoosePlaceBottomSheetBinding.inflate(inflater, container, false)


        binding.choosePlaceExitButton.setOnClickListener {
            dismiss()
        }


        binding.choosePlaceSelectButton.setOnClickListener {
            dismiss()
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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