package company.tap.tapuisample.adapters


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.themekit.theme.ChipTheme
import company.tap.tapuilibrary.uikit.atoms.TapChip
import company.tap.tapuisample.R
import company.tap.tapuisample.interfaces.OnCardSelectedActionListener
import kotlinx.android.synthetic.main.item_gopay.view.*
import kotlinx.android.synthetic.main.item_knet.view.*
import kotlinx.android.synthetic.main.item_saved_card.view.*

/**
Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/

@Suppress("PrivatePropertyName")
class CardTypeAdapter (private val arrayList: ArrayList<Int>, private val onCardSelectedActionListener: OnCardSelectedActionListener? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_SAVED_CARD = 1
    private val TYPE_REDIRECT = 2
    private val TYPE_GO_PAY = 3
    private var selectedPosition = -1
    private lateinit var  tapCardChip2 :TapChip
    private lateinit var  tapCardChip3 :TapChip
    private lateinit var  tapCardChip1 :TapChip
    private var tapChipgrp :TapChip?=null




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return when (viewType) {
            TYPE_SAVED_CARD -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_saved_card, parent, false)
                tapCardChip2= view.findViewById(R.id.tapCardChip2)
                SavedViewHolder(view)
            }
            TYPE_REDIRECT -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_knet, parent, false)
                tapCardChip3= view.findViewById(R.id.tapCardChip3)
                SingleViewHolder(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_gopay, parent, false)
                tapCardChip1= view.findViewById(R.id.tapCardChip1)
                GoPayViewHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (arrayList[position] == 1 || arrayList[position] == 3 || arrayList[position] == 5) {
            TYPE_REDIRECT
        } else if (arrayList[position] == 2) {
            TYPE_GO_PAY
        } else {
            TYPE_SAVED_CARD
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




        if (holder.itemViewType == TYPE_SAVED_CARD){
            tapChipgrp = holder.itemView.findViewById(R.id.tapCardChip2)
            val chipTheme = ChipTheme()
            chipTheme.backgroundColor = Color.parseColor(ThemeManager.getValue("merchantHeaderView.backgroundColor"))
            chipTheme.chipHeight = 100.0
            tapChipgrp?.setTheme(chipTheme)
        }

        checkPosition(holder,position)
        setOnClickActions(holder)

    }

    private fun setOnClickActions(holder: RecyclerView.ViewHolder){

        holder.itemView.deleteImageView1?.setOnClickListener {
            onCardSelectedActionListener?.onDeleteIconClicked(true,holder.itemView.id)
        }
        holder.itemView.deleteImageView2?.setOnClickListener {
            onCardSelectedActionListener?.onDeleteIconClicked(true,holder.itemView.id)
        }
        holder.itemView.deleteImageView3?.setOnClickListener {
            onCardSelectedActionListener?.onDeleteIconClicked(true, holder.itemView.id)
            holder.itemView.clearAnimation()
            it.animate().cancel()
            it.clearAnimation()
        }
    }


    private fun checkPosition(holder: RecyclerView.ViewHolder, position: Int){
        when {
            getItemViewType(position) == TYPE_SAVED_CARD -> {
                if (selectedPosition == position) {
                    holder.itemView.setBackgroundResource(R.drawable.border_shadow)
                } else
                    holder.itemView.setBackgroundResource(R.drawable.border_unclick)
                (holder as SavedViewHolder)
                holder.itemView.setOnClickListener {
                    selectedPosition = position
                    notifyDataSetChanged()
                }
            }
            getItemViewType(position) == TYPE_REDIRECT -> {
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

            }
            else -> {
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
        }

    }

    fun setTheme(){

        val chipTheme = ChipTheme()
        chipTheme.backgroundColor = Color.parseColor(ThemeManager.getValue("merchantHeaderView.backgroundColor"))
        chipTheme.chipHeight = 100.0



//        tapCardChip2?.view?.backgroundColor(Color.parseColor(ThemeManager.getValue("merchantHeaderView.backgroundColor")))
//        tapCardChip3?.view?.setBackgroundColor(Color.parseColor(ThemeManager.getValue("merchantHeaderView.backgroundColor")))
//        tapCardChip1?.setTheme(chipTheme)
//        tapCardChip2?.setTheme(chipTheme)
//        tapCardChip3?.setTheme(chipTheme)
    }

    internal class SavedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    internal class SingleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    internal class GoPayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}


}