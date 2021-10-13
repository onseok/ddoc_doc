package com.wonseok.ddoc_doc.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wonseok.ddoc_doc.R
import com.wonseok.ddoc_doc.data.Item
import com.wonseok.ddoc_doc.data.ListLayout


class HospitalAdapter(val itemList: ArrayList<ListLayout>): RecyclerView.Adapter<HospitalAdapter.HospitalItemViewHolder>() {

    inner class HospitalItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hospitalName: TextView = itemView.findViewById(R.id.hospitalName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalAdapter.HospitalItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_list, parent, false)
        return HospitalItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: HospitalItemViewHolder, position: Int) {
        holder.hospitalName.text = itemList[position].yadmNm

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