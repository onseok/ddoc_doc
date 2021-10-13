package com.wonseok.ddoc_doc.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wonseok.ddoc_doc.R
import com.wonseok.ddoc_doc.data.PlaceGu


class ChoosePlaceAdapter(val itemList: ArrayList<PlaceGu>): RecyclerView.Adapter<ChoosePlaceAdapter.GuItemViewHolder>() {

    inner class GuItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val guName: TextView = itemView.findViewById(R.id.guTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoosePlaceAdapter.GuItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_list, parent, false)
        return GuItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: GuItemViewHolder, position: Int) {

        holder.guName.text = itemList[position].gu

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