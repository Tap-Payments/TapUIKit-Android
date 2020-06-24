package company.tap.tapuisample.adapters

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import company.tap.tapuisample.R

/**
 * Created by AhlaamK on 6/22/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
class ItemAdapter (private val itemList: ArrayList<Int>) :
    RecyclerView.Adapter<ItemAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_adapter, parent, false)
        context = parent.context
        return ItemHolder(v)
    }

    override fun getItemCount() = itemList.size


    class ItemHolder(v: View) : RecyclerView.ViewHolder(v) {

        @SuppressLint("SetTextI18n")
        fun bindItems(itemList: Int) {
            val itemName = itemView.findViewById(R.id.item_title) as TextView
            val itemAmount  = itemView.findViewById(R.id.item_amount) as TextView
            val descText  = itemView.findViewById(R.id.show_description) as TextView
            val discount  = itemView.findViewById(R.id.discount_text) as TextView
            val totalAmount  = itemView.findViewById(R.id.total_amount) as TextView
            val totalQuantity  = itemView.findViewById(R.id.total_quantity) as TextView

            if(itemList%2==0){
                discount.text = "Discount"
                totalAmount.paintFlags =
                    totalAmount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    itemName.text = "ITEM TITLE $itemList"

            }else
            {
                discount.visibility= View.INVISIBLE
                totalAmount.paintFlags =
                    totalAmount.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                itemName.text = "VERY LOOOONNGGGG ITEM TITLE ITEM TITLE $itemList"
            }
            //itemName.text = "ITEM TITLE VERY VERY LOOONNGGGG DESCRIPTIONNNNNN $itemList"
            itemAmount.text = "KD000,000.000"
            descText.text = "Show Description"
            totalAmount.text = "KD000,000.000"
            totalQuantity.text = "1"

        }


    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bindItems(itemList[position])


    }
}
