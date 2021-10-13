package com.wonseok.ddoc_doc.api

import com.wonseok.ddoc_doc.data.Hospitals
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

class HospitalOpenApi {
    companion object {
        val DOMAIN = "http://apis.data.go.kr/B551182/hospInfoService1/"
    }
}

interface HospitalOpenService {
    @GET("getHospBasisList1?_type=json")
    fun getHospitals(
        @Query("ServiceKey") serviceKey: String,
        @Query("pageNo") pageNumber: Int,
        @Query("numOfRows") numberOfRows: Int,
        @Query("emdongNm") emdongName: String,
        @Query("radius") radius: Int
    ): Call<Hospitals>
}

