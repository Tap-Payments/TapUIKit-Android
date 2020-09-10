package company.tap.tapuisample.adapters


import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Color.*
import android.graphics.ColorMatrixColorFilter
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.uikit.adapters.context
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
    private var selectedPosition = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
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
            /////////////////////////SAVED CARDS //////////////////////////////////////////////
            getItemViewType(position) == TYPE_SAVED_CARD -> {
                if (selectedPosition == position) {
//                    checkClicked(holder,position)

                    /**
                     * Method to draw bordered view
                     * setBorderedView ( view: View, cornerRadius:Float,strokeWidth: Float, strokeColor: Int,tintColor: Int )
                     */
                    setBorderedView(holder.itemView,
                        (ThemeManager.getValue("horizontalList.chips.radius")as Int).toFloat(),
                        7.0f,parseColor(ThemeManager.getValue("horizontalList.chips.savedCardChip.selected.shadow.color")),
                        parseColor(ThemeManager.getValue("horizontalList.chips.savedCardChip.backgroundColor")))

                } else {

                    setBorderedView(holder.itemView,
                        (ThemeManager.getValue("horizontalList.chips.radius")as Int).toFloat(),
                        0.0f,parseColor(ThemeManager.getValue("horizontalList.chips.savedCardChip.selected.shadow.color")),
                        parseColor(ThemeManager.getValue("horizontalList.chips.savedCardChip.backgroundColor")))
//                    checkUnclicked(holder,position)
                }
                    (holder as SavedViewHolder)
                holder.itemView.setOnClickListener {
                    selectedPosition = position
                    notifyDataSetChanged()
                }
            }

            //////////////////////////////KNET///////////////////////////////////////////////////
            getItemViewType(position) == TYPE_REDIRECT -> {

                if (selectedPosition == position) {

                    setBorderedView(holder.itemView,
                        (ThemeManager.getValue("horizontalList.chips.radius")as Int).toFloat(),
                        7.0f,parseColor(ThemeManager.getValue("horizontalList.chips.savedCardChip.selected.shadow.color")),
                        parseColor(ThemeManager.getValue("horizontalList.chips.savedCardChip.backgroundColor")))
//                    checkClickedKnet(holder,position)
                }
                else{

                    setBorderedView(holder.itemView,
                        (ThemeManager.getValue("horizontalList.chips.radius")as Int).toFloat(),
                        0.0f,parseColor(ThemeManager.getValue("horizontalList.chips.savedCardChip.selected.shadow.color")),
                        parseColor(ThemeManager.getValue("horizontalList.chips.savedCardChip.backgroundColor")))
//                    checkUnclickedKnet(holder,position)
                }
                    (holder as SingleViewHolder)
                holder.itemView.setOnClickListener {
                    onCardSelectedActionListener?.onCardSelectedAction(true)
                    selectedPosition = position
                    notifyDataSetChanged()
                }

            }

            ////////////////////////////Gopay///////////////////////////////////////
            else -> {

                if (selectedPosition == position) {
                    setBorderedView(holder.itemView,
                        (ThemeManager.getValue("horizontalList.chips.radius")as Int).toFloat(),
                        7.0f,parseColor(ThemeManager.getValue("horizontalList.chips.savedCardChip.selected.shadow.color")),
                        parseColor(ThemeManager.getValue("horizontalList.chips.savedCardChip.backgroundColor")))
//                    checkClickedGoPay(holder,position)
                }
                else{
                    setBorderedView(holder.itemView,
                        (ThemeManager.getValue("horizontalList.chips.radius")as Int).toFloat(),
                        0.0f,parseColor(ThemeManager.getValue("horizontalList.chips.savedCardChip.selected.shadow.color")),
                        parseColor(ThemeManager.getValue("horizontalList.chips.savedCardChip.backgroundColor")))
//                    checkUnclickedGoPay(holder,position)
                }
                    (holder as GoPayViewHolder)
                holder.itemView.setOnClickListener {
                    selectedPosition = position
                    onCardSelectedActionListener?.onCardSelectedAction(true)
                    notifyDataSetChanged()
                }
            }
        }
    }

    //// set layout of saved cards when click
    @SuppressLint("ResourceType")
    fun checkClicked(holder: RecyclerView.ViewHolder, position: Int){
        val shapeAppearanceModel = ShapeAppearanceModel().toBuilder().setAllCorners(CornerFamily.ROUNDED, 30f).build()
        val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
        holder.itemView.let { ViewCompat.setBackground(it, shapeDrawable) }
        shapeDrawable.setStroke((ThemeManager.getValue("horizontalList.chips.radius")as Int).toFloat(), parseColor(ThemeManager.getValue("horizontalList.chips.savedCardChip.selected.shadow.color")))
//        shapeDrawable.fillColor = context?.let { ContextCompat.getColorStateList(it,parseColor("#ff1c1c1e")) }
        shapeDrawable.setTint(Color.parseColor(ThemeManager.getValue("horizontalList.chips.savedCardChip.backgroundColor")))

        val tapCardChip2 = holder.itemView.findViewById<TapChip>(R.id.tapCardChip2)
        tapCardChip2.minimumHeight = 10
    }

    //// set layout of saved cards when unclick
    @SuppressLint("ResourceType")
    fun checkUnclicked(holder: RecyclerView.ViewHolder, position: Int){
        val shapeAppearanceModel = ShapeAppearanceModel().toBuilder().setAllCorners(CornerFamily.ROUNDED, 30f).build()
        val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
        holder.itemView.let { ViewCompat.setBackground(it, shapeDrawable) }
//        shapeDrawable.fillColor = context?.let { ContextCompat.getColorStateList(it,parseColor(ThemeManager.getValue("horizontalList.chips.savedCardChip.backgroundColor"))) }
        shapeDrawable.setTint(Color.parseColor(ThemeManager.getValue("horizontalList.chips.savedCardChip.backgroundColor")))

        //        someLayout.getBackground().setColorFilter(Color.parseColor("#ff8800"), PorterDuff.Mode.SRC_ATOP);
    }
    //// set layout of saved cards when click
    @SuppressLint("ResourceType")
    fun checkClickedKnet(holder: RecyclerView.ViewHolder, position: Int){
        val shapeAppearanceModel = ShapeAppearanceModel().toBuilder().setAllCorners(CornerFamily.ROUNDED, 30f).build()
        val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
        holder.itemView.let { ViewCompat.setBackground(it, shapeDrawable) }
        shapeDrawable.setStroke(7.0f, GREEN)
        shapeDrawable.fillColor = context?.let { ContextCompat.getColorStateList(it, R.color.bg_gray) }
        val tapCardChip3 = holder.itemView.findViewById<TapChip>(R.id.tapCardChip3)
        tapCardChip3.minimumHeight = 10
    }

    //// set layout of saved cards when unclick
    @SuppressLint("ResourceType")
    fun checkUnclickedKnet(holder: RecyclerView.ViewHolder, position: Int){
        val shapeAppearanceModel = ShapeAppearanceModel().toBuilder().setAllCorners(CornerFamily.ROUNDED, 30f).build()
        val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
        holder.itemView.let { ViewCompat.setBackground(it, shapeDrawable) }
        shapeDrawable.setStroke(0.0f, GREEN)
        shapeDrawable.setTint(Color.parseColor(ThemeManager.getValue("horizontalList.chips.savedCardChip.backgroundColor")))

    }



    @SuppressLint("ResourceType")
    fun checkClickedGoPay(holder: RecyclerView.ViewHolder, position: Int){
        val shapeAppearanceModel = ShapeAppearanceModel().toBuilder().setAllCorners(CornerFamily.ROUNDED, 30f).build()
        val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
        holder.itemView.let { ViewCompat.setBackground(it, shapeDrawable) }
        shapeDrawable.setStroke(7.0f, GREEN)
        shapeDrawable.fillColor = context?.let { ContextCompat.getColorStateList(it, R.color.bg_gray) }
        val tapCardChip1 = holder.itemView.findViewById<TapChip>(R.id.tapCardChip1)
        tapCardChip1.minimumHeight = 10
    }

    //// set layout of saved cards when unclick
    @SuppressLint("ResourceType")
    fun checkUnclickedGoPay(holder: RecyclerView.ViewHolder, position: Int){
        val shapeAppearanceModel = ShapeAppearanceModel().toBuilder().setAllCorners(CornerFamily.ROUNDED, 30f).build()
        val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
        holder.itemView.let { ViewCompat.setBackground(it, shapeDrawable) }
        shapeDrawable.setStroke(0.0f, GREEN)
        shapeDrawable.setTint(Color.parseColor(ThemeManager.getValue("horizontalList.chips.savedCardChip.backgroundColor")))
    }




    class SavedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    internal class SingleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    internal class GoPayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}