package com.wonseok.ddoc_doc.data

data class Item(
    val XPos: Double,
    val YPos: Any,
    val addr: String?,
    val clCd: Int,
    val clCdNm: String?,
    val cmdcGdrCnt: Int,
    val cmdcIntnCnt: Int,
    val cmdcResdntCnt: Int,
    val cmdcSdrCnt: Int,
    val detyGdrCnt: Int,
    val detyIntnCnt: Int,
    val detyResdntCnt: Int,
    val detySdrCnt: Int,
    val drTotCnt: Int,
    val emdongNm: String?,
    val estbDd: Int,
    val hospUrl: String?,
    val mdeptGdrCnt: Int,
    val mdeptIntnCnt: Int,
    val mdeptResdntCnt: Int,
    val mdeptSdrCnt: Int,
    val postNo: Any,
    val sgguCd: Int,
    val sgguCdNm: String?,
    val sidoCd: Int,
    val sidoCdNm: String?,
    val telno: String?,
    val yadmNm: String?,
    val ykiho: String?
)