package company.tap.tapuisample.adapters


import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import company.tap.tapuisample.R
import company.tap.tapuisample.interfaces.OnCardSelectedActionListener
import kotlinx.android.synthetic.main.item_gopay.view.*
import kotlinx.android.synthetic.main.item_knet.view.*
import kotlinx.android.synthetic.main.item_saved_card.view.*


/**
 * Created by AhlaamK on 6/18/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/


class CardTypeAdapter (private val arrayList: ArrayList<Int>,private val onCardSelectedActionListener: OnCardSelectedActionListener? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_SAVEDCARD = 1
    private val TYPE_REDIRECT = 2
    private val TYPE_GOPAY = 3
    private var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return if (viewType === TYPE_SAVEDCARD) {
            view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_saved_card, parent, false)
            SavedViewHolder(
                view
            )
        } else if (viewType === TYPE_REDIRECT) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_knet, parent, false)
            SingleViewHolder(
                view
            )
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_gopay, parent, false)
            GoPayViewHolder(
                view
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (arrayList[position] == 1 || arrayList[position] == 3 || arrayList[position] == 5) {
            TYPE_REDIRECT
        } else if (arrayList[position] == 2) {
            TYPE_GOPAY
        } else {
            TYPE_SAVEDCARD
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        println("position printed: $position")
        if (position == 0){
            val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
            params.leftMargin = 28
            holder.itemView.layoutParams = params
        }
        if (getItemViewType(position) === TYPE_SAVEDCARD) {
            if (selectedPosition == position) {
                holder.itemView.setBackgroundResource(R.drawable.border_shadow)
            } else
                holder.itemView.setBackgroundResource(R.drawable.border_unclick)
            (holder as SavedViewHolder)
            holder.itemView.setOnClickListener {
                selectedPosition = position
                notifyDataSetChanged()
            }
        } else if (getItemViewType(position) === TYPE_REDIRECT) {
            if (selectedPosition == position)
                holder.itemView.setBackgroundResource(R.drawable.border_shadow)
            else
                holder.itemView.setBackgroundResource(R.drawable.border_unclick)
            (holder as SingleViewHolder)
            holder.itemView.setOnClickListener {
                onCardSelectedActionListener?.onCardSelectedAction(true)
                selectedPosition = position
                notifyDataSetChanged()
            }

        } else {
            if (selectedPosition == position)
                holder.itemView.setBackgroundResource(R.drawable.border_gopay)
            else
                holder.itemView.setBackgroundResource(R.drawable.border_gopay_unclick)
            (holder as GoPayViewHolder)
            holder.itemView.setOnClickListener {
                selectedPosition = position
                onCardSelectedActionListener?.onCardSelectedAction(true)
                notifyDataSetChanged()
            }

        }
        holder.itemView.deleteImageView1?.setOnClickListener {
           // it.startAnimation(null)
            it.animation=null
            it.startAnimation(null)
        }
        holder.itemView.deleteImageView2?.setOnClickListener {
           // it.startAnimation(null)
            it.animation=null
        }
        holder.itemView.deleteImageView3?.setOnClickListener {
           // it.startAnimation(null)
            it.animation=null
        }


    }

    internal class SavedViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
    }

    internal class SingleViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

    }

    internal class GoPayViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

    }


}