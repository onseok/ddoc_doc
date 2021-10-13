package com.wonseok.ddoc_doc

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.R
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import com.naver.maps.map.widget.LocationButtonView
import com.wonseok.ddoc_doc.databinding.ActivityDetailBinding
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity(), OnMapReadyCallback {

    val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }

    var phoneNum: String = "tel:"

    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private val mapView: MapView by lazy {
        findViewById(com.wonseok.ddoc_doc.R.id.mapView)
    }

    private val currentLocationButton: LocationButtonView by lazy {
        findViewById(com.wonseok.ddoc_doc.R.id.currentLocationButton)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)


        binding.hospitalDetailNameTextView.text = intent.getStringExtra("hospitalName")
        binding.hospitalDetailTitleTextView.text = intent.getStringExtra("hospitalName")
        binding.hospitalAddressTextView.text = intent.getStringExtra("addr")

        binding.hospitalDetailBackButton.setOnClickListener {
            onBackPressed()
        }

        // 전화걸기 기능
        binding.hospitalDetailCallButton.setOnClickListener {
            phoneNum += intent.getStringExtra("telno")
            var intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse(phoneNum)
            startActivity(intent)

            phoneNum = "tel:"
        }

        // 설립년도 세팅
        setBuiltYear()

        setDoctorNumber()
    }

    // 전문의 수와 총 의사 수를 구해주는 메서드
    private fun setDoctorNumber() {
        val totalNumber = intent.getStringExtra("drTotCnt")?.toInt()
        val professionalNumber = (intent.getStringExtra("mdeptSdrCnt")?.toInt()
            ?: 0) + (intent.getStringExtra("detySdrCnt")?.toInt()
            ?: 0) + (intent.getStringExtra("cmdcSdrCnt")?.toInt() ?: 0)

        binding.doctorProfessionalNumTextView.text = "전문의 ${professionalNumber}명"
        binding.doctorTotalNumTextView.text = "총 의사 ${totalNumber}명"
    }


    private fun setBuiltYear() {

        // 현재시간을 가져오기
        val long_now = System.currentTimeMillis()

        // 현재 시간을 Date 타입으로 변환
        val t_date = Date(long_now)

        // 날짜, 시간을 가져오고 싶은 형태 선언
        val t_dateFormat = SimpleDateFormat("yyyy", Locale("ko", "KR"))

        // 현재 시간을 dateFormat 에 선언한 형태의 String 으로 변환
        val str_date = t_dateFormat.format(t_date)

        val year =
            str_date.toInt() - (intent.getStringExtra("estbDd")?.slice(0..3)?.toInt() ?: 0) + 1

        binding.hospitalDetailYearTextView.text = "설립 ${year}년"
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map

        naverMap.maxZoom = 18.0
        naverMap.minZoom = 10.0

        val latitude = intent.getStringExtra("YPos")?.toDouble() ?: 0.0
        val longitude = intent.getStringExtra("XPos")?.toDouble() ?: 0.0

        // 초기 위치값 설정 (강남역 위,경도)
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(latitude, longitude))
        naverMap.moveCamera(cameraUpdate)

        // 현위치
        val uiSetting = naverMap.uiSettings
        uiSetting.isLocationButtonEnabled = false
        currentLocationButton.map = naverMap

        locationSource = FusedLocationSource(this@DetailActivity, LOCATION_PERMISSION_REQUEST_CODE)
        naverMap.locationSource = locationSource

        // 마커
        val marker = Marker()
        marker.position = LatLng(latitude, longitude)
        marker.map = naverMap
        marker.width = 150
        marker.height = 150
        marker.icon = OverlayImage.fromResource(com.wonseok.ddoc_doc.R.drawable.hospital_marker);
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return
        }

        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) {
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}