package company.tap.tapuisample


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import company.tap.tapuilibrary.atoms.TapImageView
import company.tap.tapuilibrary.atoms.TapTextView


/**
 * Created by AhlaamK on 6/18/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
class MultipleTypeAdapter(private val arrayList: ArrayList<Int>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_SAVEDCARD = 1
    private val TYPE_SINGLE = 2
    private val TYPE_GOPAY = 3
    private val context: Context? = null
    var selectedPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return if (viewType === TYPE_SAVEDCARD) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_saved_card, parent, false)
           SavedViewHolder(view)
        } else if (viewType === TYPE_SINGLE) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_knet, parent, false)
            SingleViewHolder(view)
        }else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_gopay, parent, false)
            SingleViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (arrayList[position]==1||arrayList[position]==3||arrayList[position]==5) {
            TYPE_SINGLE
        } else if(arrayList[position]==2) {
            TYPE_GOPAY
        }else{
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
            (holder as SavedViewHolder).bindItems(arrayList[position])
            holder.itemView.setOnClickListener {
                selectedPosition = position
                notifyDataSetChanged()
            }
        } else {

            if (selectedPosition == position)
                holder.itemView.setBackgroundResource(R.drawable.custom_bg)
            else
                holder.itemView.setBackgroundResource(R.drawable.custom_bg_unclick)
            (holder as SingleViewHolder).bindItems(arrayList[position])
            holder.itemView.setOnClickListener {
                selectedPosition = position
                notifyDataSetChanged()
            }

        }
    }

    internal class SavedViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bindItems(aray: Int) {
            val textViewcard = itemView.findViewById<TapTextView>(R.id.textView_card)
            val imageViewmaster = itemView.findViewById<TapImageView>(R.id.imageView_master)
            val imageViewvisa = itemView.findViewById<TapImageView>(R.id.imageView_visa)
            val imageViewamex = itemView.findViewById<TapImageView>(R.id.imageView_amex)
            itemView.setOnClickListener {
                textViewcard.visibility = View.GONE
                imageViewamex.visibility = View.VISIBLE
                var pos = adapterPosition
                println("pos is $pos")
            }

        }
    }

    internal class SingleViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bindItems(aray: Int) {
            val imageViewKnet = itemView.findViewById<TapImageView>(R.id.imageView_knet)
            val imageViewBenefit = itemView.findViewById<TapImageView>(R.id.imageView_benefit)

        }
    }
}