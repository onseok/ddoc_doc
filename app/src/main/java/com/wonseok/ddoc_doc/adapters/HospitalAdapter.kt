package com.wonseok.ddoc_doc.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wonseok.ddoc_doc.R
import com.wonseok.ddoc_doc.data.Item


class HospitalAdapter(val itemList: ArrayList<Item>): RecyclerView.Adapter<HospitalAdapter.HospitalItemViewHolder>() {

    inner class HospitalItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hospitalName: TextView = itemView.findViewById(R.id.hospitalName)
        val XPos: TextView = itemView.findViewById(R.id.XPos)
        val YPos: TextView = itemView.findViewById(R.id.YPos)
        val addr: TextView = itemView.findViewById(R.id.addr)
        val clCd: TextView = itemView.findViewById(R.id.clCd)
        val clCdNm: TextView = itemView.findViewById(R.id.clCdNm)
        val cmdcGdrCnt: TextView = itemView.findViewById(R.id.cmdcGdrCnt)
        val cmdcIntnCnt: TextView = itemView.findViewById(R.id.cmdcIntnCnt)
        val cmdcResdntCnt: TextView = itemView.findViewById(R.id.cmdcResdntCnt)
        val cmdcSdrCnt: TextView = itemView.findViewById(R.id.cmdcSdrCnt)
        val detyGdrCnt: TextView = itemView.findViewById(R.id.detyGdrCnt)
        val detyIntnCnt: TextView = itemView.findViewById(R.id.detyIntnCnt)
        val detyResdntCnt: TextView = itemView.findViewById(R.id.detyResdntCnt)
        val detySdrCnt: TextView = itemView.findViewById(R.id.detySdrCnt)
        val drTotCnt: TextView = itemView.findViewById(R.id.drTotCnt)
        val emdongNm: TextView = itemView.findViewById(R.id.emdongNm)
        val estbDd: TextView = itemView.findViewById(R.id.estbDd)
        val hospUrl: TextView = itemView.findViewById(R.id.hospUrl)
        val mdeptGdrCnt: TextView = itemView.findViewById(R.id.mdeptGdrCnt)
        val mdeptIntnCnt: TextView = itemView.findViewById(R.id.mdeptIntnCnt)
        val mdeptResdntCnt: TextView = itemView.findViewById(R.id.mdeptResdntCnt)
        val mdeptSdrCnt: TextView = itemView.findViewById(R.id.mdeptSdrCnt)
        val postNo: TextView = itemView.findViewById(R.id.postNo)
        val sgguCd: TextView = itemView.findViewById(R.id.sgguCd)
        val sgguCdNm: TextView = itemView.findViewById(R.id.sgguCdNm)
        val sidoCd: TextView = itemView.findViewById(R.id.sidoCd)
        val sidoCdNm: TextView = itemView.findViewById(R.id.sidoCdNm)
        val telno: TextView = itemView.findViewById(R.id.telno)
        val yadmNm: TextView = itemView.findViewById(R.id.yadmNm)
        val ykiho: TextView = itemView.findViewById(R.id.ykiho)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalAdapter.HospitalItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_list, parent, false)
        return HospitalItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: HospitalItemViewHolder, position: Int) {
        holder.hospitalName.text = itemList[position].yadmNm
        holder.XPos.text = itemList[position].XPos.toString()
        holder.YPos.text = itemList[position].YPos.toString()
        holder.addr.text = itemList[position].addr
        holder.clCd.text = itemList[position].clCd.toString()
        holder.clCdNm.text = itemList[position].clCdNm
        holder.cmdcGdrCnt.text = itemList[position].cmdcGdrCnt.toString()
        holder.cmdcIntnCnt.text = itemList[position].cmdcIntnCnt.toString()
        holder.cmdcResdntCnt.text = itemList[position].cmdcResdntCnt.toString()
        holder.cmdcSdrCnt.text = itemList[position].cmdcSdrCnt.toString()
        holder.detyGdrCnt.text = itemList[position].detyGdrCnt.toString()
        holder.detyIntnCnt.text = itemList[position].detyIntnCnt.toString()
        holder.detyResdntCnt.text = itemList[position].detyResdntCnt.toString()
        holder.detySdrCnt.text = itemList[position].detySdrCnt.toString()
        holder.drTotCnt.text = itemList[position].drTotCnt.toString()
        holder.emdongNm.text = itemList[position].emdongNm
        holder.estbDd.text = itemList[position].estbDd.toString()
        holder.hospUrl.text = itemList[position].hospUrl
        holder.mdeptGdrCnt.text = itemList[position].mdeptGdrCnt.toString()
        holder.mdeptIntnCnt.text = itemList[position].mdeptIntnCnt.toString()
        holder.mdeptResdntCnt.text = itemList[position].mdeptResdntCnt.toString()
        holder.mdeptSdrCnt.text = itemList[position].mdeptSdrCnt.toString()
        holder.postNo.text = itemList[position].postNo.toString()
        holder.sgguCd.text = itemList[position].sgguCd.toString()
        holder.sgguCdNm.text = itemList[position].sgguCdNm
        holder.sidoCd.text = itemList[position].sidoCd.toString()
        holder.sidoCdNm.text = itemList[position].sidoCdNm
        holder.telno.text = itemList[position].telno
        holder.ykiho.text = itemList[position].ykiho


        // 아이템 클릭 이벤트
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener
}