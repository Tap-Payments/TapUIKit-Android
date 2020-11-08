package company.tap.tapuisample.adapters


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Color.parseColor
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.uikit.ktx.setBorderedView
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
class CardTypeAdapter(
    private val arrayList: ArrayList<Int>,
    private val onCardSelectedActionListener: OnCardSelectedActionListener? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), OnEditClick {
    private val TYPE_SAVED_CARD = 1
    private val TYPE_REDIRECT = 2
    private val TYPE_GO_PAY = 3
    private var selectedPosition = -1
    var context_: Context?= null

   var viewHolder:RecyclerView.ViewHolder ?= null

   var onEditClick:OnEditClick ? = null

    var isEditClicked :Boolean ? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
//        context_ = parent.context
        return when (viewType) {
            TYPE_SAVED_CARD -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_saved_card, parent, false)
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


    private fun setOnClickActions(holder: RecyclerView.ViewHolder) {

        holder.itemView.deleteImageView1?.setOnClickListener {
            onCardSelectedActionListener?.onDeleteIconClicked(true, holder.itemView.id)
        }
        holder.itemView.deleteImageView2?.setOnClickListener {
            onCardSelectedActionListener?.onDeleteIconClicked(true, holder.itemView.id)
        }
        holder.itemView.deleteImageView3?.setOnClickListener {
            onCardSelectedActionListener?.onDeleteIconClicked(true, holder.itemView.id)
            holder.itemView.clearAnimation()
            it.animate().cancel()
            it.clearAnimation()
        }
    }

    private  var parent: RecyclerView ? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        parent = recyclerView
    }


//    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
//        super.onDetachedFromRecyclerView(recyclerView)
//        parent = null
//    }
    fun startShakingAnimation(context__ : Context){
        for (item in arrayList){
            val holder: RecyclerView.ViewHolder? =
                parent?.getChildAt(item)?.let { parent?.getChildViewHolder(it) }
            context_ = context__
            Log.d("Context" , context__.toString())
            val animShake: Animation = AnimationUtils.loadAnimation(context__, R.anim.shake)
            holder?.itemView?.startAnimation(animShake)
        }
    }


    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        println("position printed: $position")
        if (position == 0) {
            val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
            params.leftMargin = 28
            holder.itemView.layoutParams = params
        }
        if (getItemViewType(position) === TYPE_SAVED_CARD) {
            if (selectedPosition == position) {
                holder.itemView.setBackgroundResource(R.drawable.border_shadow_)

                setBorderedView(holder.itemView.tapCardChip2Constraints,
                    (ThemeManager.getValue("horizontalList.chips.radius")as Int).toFloat(),// corner raduis
                    0.0f,parseColor(ThemeManager.getValue("horizontalList.chips.goPayChip.selected.shadow.color")),// stroke color
                    Color.parseColor(ThemeManager.getValue("horizontalList.chips.savedCardChip.backgroundColor")),// tint color
                    parseColor(ThemeManager.getValue("horizontalList.chips.goPayChip.unSelected.shadow.color")))// shadow color

            } else {
                holder.itemView.setBackgroundResource(R.drawable.border_unclick)

                setBorderedView(holder.itemView.tapCardChip2Constraints,
                    (ThemeManager.getValue("horizontalList.chips.radius")as Int).toFloat(),// corner raduis
                    0.0f,parseColor(ThemeManager.getValue("horizontalList.chips.goPayChip.selected.shadow.color")),// stroke color
                    Color.parseColor(ThemeManager.getValue("horizontalList.chips.savedCardChip.backgroundColor")),// tint color
                    parseColor(ThemeManager.getValue("horizontalList.chips.goPayChip.unSelected.shadow.color")))// shadow color

            }
            (holder as SavedViewHolder)
            holder.itemView.setOnClickListener {
                selectedPosition = position
                notifyDataSetChanged()
            }
        } else if (getItemViewType(position) === TYPE_REDIRECT) {
            if (selectedPosition == position) {
//                holder.itemView.tapCardChip3Linear.setBackgroundColor(Color.WHITE)
                holder.itemView.setBackgroundResource(R.drawable.border_shadow_)

                setBorderedView(holder.itemView.tapCardChip3Linear,
                    (ThemeManager.getValue("horizontalList.chips.radius")as Int).toFloat(),// corner raduis
                    0.0f,parseColor(ThemeManager.getValue("horizontalList.chips.goPayChip.selected.shadow.color")),// stroke color
                    Color.parseColor(ThemeManager.getValue("horizontalList.chips.gatewayChip.backgroundColor")),// tint color
                    parseColor(ThemeManager.getValue("horizontalList.chips.goPayChip.unSelected.shadow.color")))// shadow color

            }
            else {
                holder.itemView.setBackgroundResource(R.drawable.border_unclick)

                setBorderedView(holder.itemView.tapCardChip3Linear,
                    (ThemeManager.getValue("horizontalList.chips.radius")as Int).toFloat(),// corner raduis
                    0.0f,parseColor(ThemeManager.getValue("horizontalList.chips.goPayChip.selected.shadow.color")),// stroke color
                    Color.parseColor(ThemeManager.getValue("horizontalList.chips.gatewayChip.backgroundColor")),// tint color
                    parseColor(ThemeManager.getValue("horizontalList.chips.goPayChip.unSelected.shadow.color")))// shadow color

            }
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
                onCardSelectedActionListener?.onCardSelectedAction(false)
                notifyDataSetChanged()
            }
        }

    }



    internal class SavedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    internal class SingleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    internal class GoPayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onEditActionClicked(isShaking: Boolean) {

    }

    fun initAdapter(onEditClick:OnEditClick){
        this.onEditClick = onEditClick
    }

}

interface OnEditClick{
    fun onEditActionClicked(isShaking: Boolean)
}



