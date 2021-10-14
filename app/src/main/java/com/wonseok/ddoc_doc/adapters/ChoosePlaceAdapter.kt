package com.wonseok.ddoc_doc.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wonseok.ddoc_doc.R
import com.wonseok.ddoc_doc.data.PlaceGu
import com.wonseok.ddoc_doc.databinding.RecyclerGridItemListBinding


class ChoosePlaceAdapter: RecyclerView.Adapter<ChoosePlaceAdapter.GuItemViewHolder>() {

    var listData = mutableListOf<PlaceGu>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoosePlaceAdapter.GuItemViewHolder {
        val binding = RecyclerGridItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GuItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: GuItemViewHolder, position: Int) {

        val data = listData.get(position)

        holder.setData(data)

//        // 아이템 클릭 이벤트
//        holder.itemView.setOnClickListener {
//            itemClickListener.onClick(it, position)
//        }
    }

    inner class GuItemViewHolder(val binding: RecyclerGridItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setData(data: PlaceGu) {
            binding.guTextView.text = data.gu
        }
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener
}