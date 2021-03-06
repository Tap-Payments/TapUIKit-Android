package company.tap.tapuilibrary.adapters

import android.annotation.SuppressLint
import android.graphics.Paint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tap.tapfontskit.enums.TapFont
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.R

import company.tap.tapuilibrary.atoms.TapSeparatorView
import company.tap.tapuilibrary.atoms.TapTextView
import company.tap.tapuilibrary.datasource.ItemViewDataSource
import company.tap.tapuilibrary.views.TapListItemView
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by AhlaamK on 6/22/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
class ItemAdapter (private val itemList: ArrayList<Int>) :
    RecyclerView.Adapter<ItemAdapter.ItemHolder>() {
   var previousExpandedPosition = -1
   var mExpandedPosition = -1
    lateinit var descriptioextView:TapTextView
    lateinit var descText:TapTextView
    lateinit var itemViewadapter:TapListItemView
    lateinit var itemSeparator:TapSeparatorView
    lateinit var totalQuantity:TapTextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_adapter, parent, false)
        context = parent.context
        itemViewadapter = v.findViewById(R.id.amount_item_view)
        return ItemHolder(v)
    }

    override fun getItemCount() = itemList.size


    class ItemHolder(v: View) : RecyclerView.ViewHolder(v)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        initView(holder,position)
    }

    private fun initView(
        holder: ItemHolder,
        position: Int
    ) {
        descriptioextView  = holder.itemView.findViewById(R.id.description_textView)
        descText = holder.itemView.findViewById(R.id.show_description)
        itemSeparator = holder.itemView.findViewById(R.id.itemseparator)
        totalQuantity = holder.itemView.findViewById(R.id.total_quantity)
        val discount  = holder.itemView.findViewById(R.id.discount_text) as TextView
        val totalAmount  = holder.itemView.findViewById(R.id.total_amount) as TextView
        val itemName = holder.itemView.findViewById(R.id.item_title) as TextView
        itemName.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.robotoRegular))
        totalAmount.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.robotoRegular))
        discount.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.robotoLight))
        descText.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.robotoLight))
        descriptioextView.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.robotoLight))
        itemViewadapter.setItemViewDataSource(getItemViewDataSource())
        totalQuantity.typeface=Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.robotoRegular))
        if(itemList[position]%2==0){
            discount.text = LocalizationManager.getValue("Discount","ItemList")
            totalAmount.paintFlags =
                totalAmount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            itemName.text = "ITEM TITLE "+itemList[position]

        }else
        {
            discount.visibility= View.INVISIBLE
            totalAmount.paintFlags =
                totalAmount.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            itemName.text = "VERY LOOOONNGGGG ITEM TITLE ITEM TITLE "+itemList[position]
        }
        val isExpanded = position === mExpandedPosition
        descriptioextView.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
        descriptioextView.visibility = if (isExpanded) View.VISIBLE else View.GONE
        holder.itemView.isActivated = isExpanded

        if (isExpanded) previousExpandedPosition = position
        holder.itemView.setOnClickListener { view ->
            mExpandedPosition = if (isExpanded) -1 else position
            notifyItemChanged(previousExpandedPosition)
            notifyItemChanged(position)

        }
        if(isExpanded){
            descText.text = LocalizationManager.getValue("hideDesc","ItemList")
        }else{
            descText.text =  LocalizationManager.getValue("showDesc","ItemList")
        }
    }

    private fun getItemViewDataSource(): ItemViewDataSource {
        return ItemViewDataSource(
            itemAmount = "KD000,000.000",
            totalAmount = "KD000,000.000",
            totalQuantity = "2"

        )
    }

}
