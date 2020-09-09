package company.tap.tapuisample.adapters


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
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
class CardTypeAdapter(
    private val arrayList: ArrayList<Int>,
    private val onCardSelectedActionListener: OnCardSelectedActionListener? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_SAVED_CARD = 1
    private val TYPE_REDIRECT = 2
    private val TYPE_GO_PAY = 3
    private var selectedPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
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
        if (position == 0) {
            val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
            params.leftMargin = 28
            holder.itemView.layoutParams = params
        }

        checkPosition(holder, position)
        setOnClickActions(holder)

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


    private fun checkPosition(holder: RecyclerView.ViewHolder, position: Int) {
        when {
            getItemViewType(position) == TYPE_SAVED_CARD -> {
                checkTYPE_SAVED_CARDClicked(holder,position)

                if (selectedPosition == position) {
                    checkTYPE_SAVED_CARDClicked(holder,position)

//                    checkTYPE_SAVED_CARDClicked(holder,position)

//                    holder.itemView.setBackgroundResource(R.drawable.border_shadow)
                } else {
//                    holder.itemView.setBackgroundResource(R.drawable.border_unclick)

                }
                    (holder as SavedViewHolder)
                holder.itemView.setOnClickListener {
                    checkTYPE_SAVED_CARDClicked(holder,position)

                    selectedPosition = position
                    notifyDataSetChanged()
                }
            }

            /////////////////////////////////////////////////////////////////////////////////
            getItemViewType(position) == TYPE_REDIRECT -> {

                if (selectedPosition == position) {
                    setTYPE_REDIRECT(holder,position)

//                    holder.itemView.setBackgroundResource(R.drawable.border_shadow)
                }
                else
//                    holder.itemView.setBackgroundResource(R.drawable.border_unclick)
                    (holder as SingleViewHolder)
                holder.itemView.setOnClickListener {
                    onCardSelectedActionListener?.onCardSelectedAction(true)
                    selectedPosition = position
                    notifyDataSetChanged()
                }

            }

            /////////////////////////////////////////////////////////////////////////////////

            else -> {

                if (selectedPosition == position) {
//                    checkTYPE_SAVED_CARD(holder,position)

//                    holder.itemView.setBackgroundResource(R.drawable.border_gopay)
                }
                else
//                    holder.itemView.setBackgroundResource(R.drawable.border_gopay_unclick)
                    (holder as GoPayViewHolder)
                holder.itemView.setOnClickListener {
                    selectedPosition = position
                    onCardSelectedActionListener?.onCardSelectedAction(true)
                    notifyDataSetChanged()
                }
            }
        }

    }

    fun checkTYPE_SAVED_CARDClicked(holder: RecyclerView.ViewHolder, position: Int){
        val shapeAppearanceModel = ShapeAppearanceModel()
            .toBuilder()
            .setAllCorners(
                CornerFamily.ROUNDED,
                30f
            )
            .build()
        val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
        holder.itemView.let { ViewCompat.setBackground(it, shapeDrawable) }
        val chipTheme = ChipTheme()
        chipTheme.chipHeight= 20.0
        chipTheme.cardElevation = 50
        chipTheme.backgroundColor = Color.WHITE
        val tapCardChip2 = holder.itemView.findViewById<TapChip>(R.id.tapCardChip2)
        tapCardChip2.setTheme(chipTheme)
    }


    fun checkTYPE_SAVED_CARDUnclicked(holder: RecyclerView.ViewHolder, position: Int){
        val shapeAppearanceModel = ShapeAppearanceModel()
            .toBuilder()
            .setAllCorners(
                CornerFamily.ROUNDED,
                (ThemeManager.getValue("horizontalList.chips.radius") as Int).toFloat()
            )
            .build()
        val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
        holder.itemView.let { ViewCompat.setBackground(it, shapeDrawable) }
        val chipTheme = ChipTheme()
//        chipTheme.chipHeight= ThemeManager.getValue("horizontalList.chips.savedCardChip.")
        chipTheme.backgroundColor = Color.BLUE
        val tapCardChip2 = holder.itemView.findViewById<TapChip>(R.id.tapCardChip2)
        tapCardChip2.setTheme(chipTheme)
    }




    fun setTYPE_REDIRECT(holder: RecyclerView.ViewHolder, position: Int){
        val shapeAppearanceModel = ShapeAppearanceModel().toBuilder().setAllCorners(CornerFamily.ROUNDED, (ThemeManager.getValue("horizontalList.chips.radius") as Int).toFloat()).build()
        val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
        holder.itemView.let { ViewCompat.setBackground(it, shapeDrawable) }
        val chipTheme = ChipTheme()
        chipTheme.backgroundColor = Color.WHITE
        val tapCardChip3 = holder.itemView.findViewById<TapChip>(R.id.tapCardChip3)
        tapCardChip3.setTheme(chipTheme)
    }

    class SavedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    internal class SingleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    internal class GoPayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}