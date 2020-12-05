/*
package company.tap.tapuilibrary.uikit.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Color.parseColor
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.webkit.URLUtil
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import company.tap.cardbusinesskit.testmodels.Payment_methods
import company.tap.checkout.internal.dummygener.SavedCards
import company.tap.tapuilibrary.R


import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.uikit.interfaces.OnCardSelectedActionListener
import company.tap.tapuilibrary.uikit.ktx.setBorderedView
import company.tap.tapuilibrary.uikit.ktx.setImage
import kotlinx.android.synthetic.main.item_gopay.view.*
import kotlinx.android.synthetic.main.item_knet.view.*
import kotlinx.android.synthetic.main.item_saved_card.view.*
import java.net.URL


*/
/**
Copyright (c) 2020    Tap Payments.
All rights reserved.
 **//*


@Suppress("PrivatePropertyName")
class CardTypeAdapter(
     val arrayList1: ArrayList<SavedCards>,
    private val onCardSelectedActionListener: OnCardSelectedActionListener?,
    var isShaking: Boolean = false
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_SAVED_CARD = 1
    private val TYPE_REDIRECT = 2
    private val TYPE_GO_PAY = 3
    private var selectedPosition = -1
    private var lastPosition = -1
    var context_: Context? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        context_ = parent.context
        return when (viewType) {
            TYPE_SAVED_CARD -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_saved_card, parent, false)
                SavedViewHolder(view)
            }
            TYPE_REDIRECT -> {
                view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_knet, parent, false)
                SingleViewHolder(view)
            }
            else -> {
                view =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_gopay, parent, false)
                GoPayViewHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        println("array icon pos"+arrayList1[position].chip1.icon)
        return if (arrayList1[position].chipType == 1

        ) {
            TYPE_REDIRECT
        } else if (arrayList1[position].chipType == 5) {
            TYPE_SAVED_CARD
        } else {
            TYPE_GO_PAY
        }
    }

    override fun getItemCount(): Int {
        return arrayList1.size
    }


    private fun setOnClickActions(holder: RecyclerView.ViewHolder) {
        val arrayList = ArrayList(arrayList1)
        holder.itemView.deleteImageView2?.visibility = View.VISIBLE

        holder.itemView.deleteImageView2?.setOnClickListener {
            onCardSelectedActionListener?.onDeleteIconClicked(true, holder.itemView.id)
            arrayList1.removeAt(holder.itemView.id)
            holder.itemView.clearAnimation()
            it.animate().cancel()
            it.clearAnimation()
        }

    }


    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        println("position printed: $position")

        when {
            */
/**
             * Saved Cards Type
             *//*

            getItemViewType(position) === TYPE_SAVED_CARD -> {
                typeSavedCard(holder, position)
            }
            */
/**
             * Knet Type
             *//*

            getItemViewType(position) === TYPE_REDIRECT -> {
                if (isShaking) {
                    holder.itemView.alpha = 0.4f
                }
                typeRedirect(holder, position)
            }
            */
/**
             * GoPay Type
             *//*

            else -> {
                if (isShaking) {
                    holder.itemView.alpha = 0.4f
                }
                if (selectedPosition == position)
                    holder.itemView.setBackgroundResource(R.drawable.border_gopay)
                else
                    holder.itemView.setBackgroundResource(R.drawable.border_gopay_unclick)
                (holder as GoPayViewHolder)

                if (!isShaking) {
                    holder.itemView.setOnClickListener {
                        selectedPosition = position
                        onCardSelectedActionListener?.onCardSelectedAction(false)
                        notifyDataSetChanged()
                    }
                }


            }
        }

    }

    private fun typeSavedCard(holder: RecyclerView.ViewHolder, position: Int) {
        if (isShaking) {
            val animShake: Animation = AnimationUtils.loadAnimation(context_, R.anim.shake)
            holder.itemView.startAnimation(animShake)
            setOnClickActions(holder)
        }
        if (selectedPosition == position) {
            if (ThemeManager.currentTheme.isNotEmpty() && ThemeManager.currentTheme.contains("dark")) {
                holder.itemView.tapCardChip2.setBackgroundResource(R.drawable.border_shadow_black)
            } else {
                holder.itemView.tapCardChip2.setBackgroundResource(R.drawable.border_shadow_)
            }
            setBorderedView(
                holder.itemView.tapCardChip2Constraints,
                (ThemeManager.getValue("horizontalList.chips.radius") as Int).toFloat(),// corner raduis
                0.0f,
                parseColor(ThemeManager.getValue("horizontalList.chips.goPayChip.selected.shadow.color")),// stroke color
                Color.parseColor(ThemeManager.getValue("horizontalList.chips.savedCardChip.backgroundColor")),// tint color
                parseColor(ThemeManager.getValue("horizontalList.chips.goPayChip.unSelected.shadow.color"))
            )// shadow color

        } else {
            if (ThemeManager.currentTheme.isNotEmpty() && ThemeManager.currentTheme.contains("dark")) {
                holder.itemView.tapCardChip2.setBackgroundResource(R.drawable.border_unclick_black)
            } else {
                holder.itemView.tapCardChip2.setBackgroundResource(R.drawable.border_unclick)
            }


            setBorderedView(
                holder.itemView.tapCardChip2Constraints,
                (ThemeManager.getValue("horizontalList.chips.radius") as Int).toFloat(),// corner raduis
                0.0f,
                parseColor(ThemeManager.getValue("horizontalList.chips.goPayChip.selected.shadow.color")),// stroke color
                Color.parseColor(ThemeManager.getValue("horizontalList.chips.savedCardChip.backgroundColor")),// tint color
                parseColor(ThemeManager.getValue("horizontalList.chips.goPayChip.unSelected.shadow.color"))
            )// shadow color

        }
        (holder as SavedViewHolder)
        if (!isShaking) {
            holder.itemView.setOnClickListener {
                selectedPosition = position
                notifyDataSetChanged()
            }
        }
        for (i in 2 until arrayList1.size) {
            val imageViewCard = holder.itemView.findViewById<ImageView>(R.id.imageView_amex)
            val url = URL(arrayList1[i].chip1.icon)
            if ( URLUtil.isValidUrl(url.toString()) ) {
                val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())

                imageViewCard.setImageBitmap(bmp)
            }
        }

    }


    private fun typeRedirect(holder: RecyclerView.ViewHolder, position: Int) {
        if (selectedPosition == position) {
//                holder.itemView.tapCardChip3Linear.setBackgroundColor(Color.WHITE)
            if (ThemeManager.currentTheme.isNotEmpty() && ThemeManager.currentTheme.contains("dark")) {
                holder.itemView.setBackgroundResource(R.drawable.border_shadow_black)
            } else {
                holder.itemView.setBackgroundResource(R.drawable.border_shadow_)
            }


            setBorderedView(
                holder.itemView.tapCardChip3Linear,
                (ThemeManager.getValue("horizontalList.chips.radius") as Int).toFloat(),// corner raduis
                0.0f,
                parseColor(ThemeManager.getValue("horizontalList.chips.goPayChip.selected.shadow.color")),// stroke color
                parseColor(ThemeManager.getValue("horizontalList.chips.gatewayChip.backgroundColor")),// tint color
                parseColor(ThemeManager.getValue("horizontalList.chips.goPayChip.unSelected.shadow.color"))
            )// shadow color

        } else {
            if (ThemeManager.currentTheme.isNotEmpty() && ThemeManager.currentTheme.contains("dark")) {
                holder.itemView.setBackgroundResource(R.drawable.border_unclick_black)

            } else {
                holder.itemView.setBackgroundResource(R.drawable.border_unclick)

            }

            setBorderedView(
                holder.itemView.tapCardChip3Linear,
                (ThemeManager.getValue("horizontalList.chips.radius") as Int).toFloat(),// corner raduis
                0.0f,
                parseColor(ThemeManager.getValue("horizontalList.chips.goPayChip.selected.shadow.color")),// stroke color
                parseColor(ThemeManager.getValue("horizontalList.chips.gatewayChip.backgroundColor")),// tint color
                parseColor(ThemeManager.getValue("horizontalList.chips.goPayChip.unSelected.shadow.color"))
            )// shadow color

        }
        (holder as SingleViewHolder)
        if (!isShaking) {
            holder.itemView.setOnClickListener {
                onCardSelectedActionListener?.onCardSelectedAction(true)
                selectedPosition = position
                notifyDataSetChanged()
            }
        }
        for (i in 2 until arrayList1.size) {
            val imageViewCard = holder.itemView.findViewById<ImageView>(R.id.imageView_knet)
            val url = URL(arrayList1[i].chip1.icon)
            if ( URLUtil.isValidUrl(url.toString()) ) {
                val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                imageViewCard.setImageBitmap(bmp)
            }
        }


    }


    internal class SavedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    internal class SingleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    internal class GoPayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}

*/
