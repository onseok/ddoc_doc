package com.wonseok.ddoc_doc.utility

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "31fee878dfb93283630ca1e528dabf48")
    }
}