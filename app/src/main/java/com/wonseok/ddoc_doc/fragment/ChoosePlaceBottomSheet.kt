package com.wonseok.ddoc_doc.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wonseok.ddoc_doc.R
import com.wonseok.ddoc_doc.adapters.ChoosePlaceAdapter
import com.wonseok.ddoc_doc.data.PlaceGu
import com.wonseok.ddoc_doc.databinding.FragmentChoosePlaceBottomSheetBinding


class ChoosePlaceBottomSheet : BottomSheetDialogFragment() {

    // 바인딩 객체 타입에 ?를 붙여서 null을 허용 해줘야한다. ( onDestroy 될 때 완벽하게 제거를 하기위해 )
    private var mBinding: FragmentChoosePlaceBottomSheetBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!



    @SuppressLint("NotifyDataSetChanged")
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

    private fun loadData(): MutableList<PlaceGu> {

        val data: MutableList<PlaceGu> = mutableListOf()

        data.add(PlaceGu("전체"))
        data.add(PlaceGu("강남구"))
        data.add(PlaceGu("강동구"))
        data.add(PlaceGu("강북구"))
        data.add(PlaceGu("강서구"))
        data.add(PlaceGu("관악구"))
        data.add(PlaceGu("광진구"))
        data.add(PlaceGu("구로구"))
        data.add(PlaceGu("금천구"))
        data.add(PlaceGu("노원구"))
        data.add(PlaceGu("도봉구"))
        data.add(PlaceGu("동대문구"))
        data.add(PlaceGu("동작구"))
        data.add(PlaceGu("마포구"))
        data.add(PlaceGu("서대문구"))
        data.add(PlaceGu("서초구"))
        data.add(PlaceGu("성동구"))
        data.add(PlaceGu("성북구"))
        data.add(PlaceGu("송파구"))
        data.add(PlaceGu("양천구"))
        data.add(PlaceGu("영등포구"))
        data.add(PlaceGu("용산구"))
        data.add(PlaceGu("은평구"))
        data.add(PlaceGu("종로구"))
        data.add(PlaceGu("중구"))
        data.add(PlaceGu("중랑구"))

        return data
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data: MutableList<PlaceGu> = loadData()
        var adapter = ChoosePlaceAdapter()

        adapter.listData = data
        binding.choosePlaceRecyclerView.adapter = adapter
        binding.choosePlaceRecyclerView.layoutManager = GridLayoutManager(activity, 3)

        adapter.notifyDataSetChanged()
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