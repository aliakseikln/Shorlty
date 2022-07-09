package com.example.task1.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task1.HistoryTableItem
import com.example.task1.R
import com.example.task1.screens.HistoryActivity
import kotlinx.android.synthetic.main.recycler_view_item.view.*


class RecyclerViewAdapter(private val view: HistoryActivity) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var arrRVBody = mutableListOf<HistoryTableItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateRVAdapter(rViewItems: MutableList<HistoryTableItem>) {
        arrRVBody = rViewItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        setItemView(holder, position)

        holder.itemView.trashCanImageView.setOnClickListener {
            view.handleRViewDeleteButtonClick(arrRVBody[position])
        }

        holder.itemView.copyButton.setOnClickListener {
            val text = holder.itemView.shortenedLinkTV.text.toString()
            view.makeTextCopy(text)
            arrRVBody.forEach {
                it.copied = false
            }
            arrRVBody[position].copied = true
            view.handleRViewCopyButtonClick(arrRVBody[position])
            notifyDataSetChanged()
        }
    }

    private fun setItemView(holder: ViewHolder, position: Int) {
        holder.itemView.originalLinkTV.text = arrRVBody[position].originalLink
        holder.itemView.shortenedLinkTV.text = arrRVBody[position].shortlyLink

        if (arrRVBody[position].copied) {
            holder.itemView.copyButton.setBackgroundColor(view.getColor(R.color.main_purple))
            holder.itemView.copyButton.text = view.getString(R.string.text_copied)
        } else {
            holder.itemView.copyButton.setBackgroundColor(view.getColor(R.color.main_blue))
            holder.itemView.copyButton.text = "COPY"
        }
    }

    override fun getItemCount(): Int {
        return arrRVBody.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}