package company.tap.tapuilibrary.uikit.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.themekit.theme.ChipTheme
import company.tap.tapuilibrary.themekit.theme.TextViewTheme
import company.tap.tapuilibrary.uikit.model.CurrencyModel


import kotlinx.android.synthetic.main.item_currency_row.view.*

/**
 * Created by AhlaamK on 6/20/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/

var selectedPosition = -1
var context: Context? = null

class CurrencyAdapter(private val photos: ArrayList<CurrencyModel>) :
    RecyclerView.Adapter<CurrencyAdapter.CurrencyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_currency_row, parent, false)
        context = parent.context
        return CurrencyHolder(
            v
        )
    }

    override fun getItemCount() = photos.size


    class CurrencyHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        private var photo: CurrencyModel? = null


        fun bindPhoto(photo: CurrencyModel) {
            this.photo = photo
            Picasso.with(view.context).load(photo.imageUrl).into(view.imageView_currency)
            view.textView_currency.text = photo.currencyCode

            setTheme()
        }
        fun setTheme(){
            val totalQuantityTextViewTheme = TextViewTheme()
            totalQuantityTextViewTheme.textColor = Color.parseColor(ThemeManager.getValue("horizontalList.chips.currencyChip.labelTextColor"))
            totalQuantityTextViewTheme.textSize = ThemeManager.getFontSize("horizontalList.chips.currencyChip.labelTextFont")
            totalQuantityTextViewTheme.font = ThemeManager.getFontName("horizontalList.chips.currencyChip.labelTextFont")
            view.textView_currency.setTheme(totalQuantityTextViewTheme)

            val chipTheme = ChipTheme()
            chipTheme.backgroundColor= Color.parseColor(ThemeManager.getValue("horizontalList.chips.currencyChip.backgroundColor"))
            chipTheme.outlineSpotShadowColor=  Color.parseColor( ThemeManager.getValue("horizontalList.chips.currencyChip.selected.shadow.color"))
            view.tapcard_Chip.setTheme(chipTheme)
            view.setBackgroundColor(Color.parseColor(ThemeManager.getValue("horizontalList.backgroundColor")))
        }
    }


    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
        holder.bindPhoto(photos[position])
        if (selectedPosition == position) {
            holder.itemView.setBackgroundResource(R.drawable.border_currency)
        } else

            holder.itemView.setBackgroundResource(R.drawable.border_unclick)
        holder.itemView.setOnClickListener {
            selectedPosition = position
            Toast.makeText(
                context,
                "You click ${holder.itemView.textView_currency.text}",
                Toast.LENGTH_SHORT
            ).show()
            notifyDataSetChanged()
        }

    }




}

