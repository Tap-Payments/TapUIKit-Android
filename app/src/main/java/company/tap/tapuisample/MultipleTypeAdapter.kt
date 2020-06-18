package company.tap.tapuisample


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by AhlaamK on 6/18/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
class MultipleTypeAdapter(private val arrayList: ArrayList<Int>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_SAVEDCARD = 1
    private val TYPE_SINGLE = 2
    private val TYPE_GOPAY = 3
    private val context: Context? = null
    var selectedPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return if (viewType === TYPE_SAVEDCARD) {
            view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_saved_card, parent, false)
            SavedViewHolder(view)
        } else if (viewType === TYPE_SINGLE) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_knet, parent, false)
            SingleViewHolder(view)
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_gopay, parent, false)
            SingleViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (arrayList[position] == 1 || arrayList[position] == 3 || arrayList[position] == 5) {
            TYPE_SINGLE
        } else if (arrayList[position] == 2) {
            TYPE_GOPAY
        } else {
            TYPE_SAVEDCARD
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) === TYPE_SAVEDCARD) {
            if (selectedPosition == position)
                holder.itemView.setBackgroundResource(R.drawable.custom_bg)
            else
                holder.itemView.setBackgroundResource(R.drawable.custom_bg_unclick)
            (holder as SavedViewHolder)
            holder.itemView.setOnClickListener {
                selectedPosition = position
                notifyDataSetChanged()
            }
        } else {

            if (selectedPosition == position)
                holder.itemView.setBackgroundResource(R.drawable.custom_bg)
            else
                holder.itemView.setBackgroundResource(R.drawable.custom_bg_unclick)
            (holder as SingleViewHolder)
            holder.itemView.setOnClickListener {
                selectedPosition = position
                notifyDataSetChanged()
            }

        }
    }

    internal class SavedViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
    }

    internal class SingleViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

    }
}