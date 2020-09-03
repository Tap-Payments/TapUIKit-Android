package company.tap.tapuilibrary.uikit.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.fontskit.enums.TapFont
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.themekit.theme.SeparatorViewTheme
import company.tap.tapuilibrary.themekit.theme.TextViewTheme
import company.tap.tapuilibrary.uikit.atoms.TapSeparatorView
import company.tap.tapuilibrary.uikit.atoms.TapTextView
import company.tap.tapuilibrary.uikit.datasource.ItemViewDataSource
import company.tap.tapuilibrary.uikit.views.TapListItemView


/**
Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
class ItemAdapter(private val itemList: ArrayList<Int>) :
    RecyclerView.Adapter<ItemAdapter.ItemHolder>() {
    private var previousExpandedPosition = -1
    private var mExpandedPosition = -1
    private lateinit var descriptionTextView: TapTextView
    private lateinit var descText: TapTextView
    private lateinit var itemViewAdapter: TapListItemView
    private lateinit var itemSeparator: TapSeparatorView
    private lateinit var discount: TapTextView
    private lateinit var totalAmount: TapTextView
    private lateinit var totalQuantity: TapTextView
    private lateinit var itemName: TapTextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_adapter, parent, false)
        context = parent.context
        itemViewAdapter = v.findViewById(R.id.amount_item_view)
        return ItemHolder(v)
    }

    override fun getItemCount() = itemList.size


    class ItemHolder(v: View) : RecyclerView.ViewHolder(v)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        initView(holder, position)
        setTheme()
        setFonts()
//        checkItemListPosition(position)
    }

    private fun initView(holder: ItemHolder, position: Int) {
        descriptionTextView = holder.itemView.findViewById(R.id.description_textView)
        descText = holder.itemView.findViewById(R.id.show_description)
        itemSeparator = holder.itemView.findViewById(R.id.itemseparator)
        totalQuantity = holder.itemView.findViewById(R.id.total_quantity)
        discount = holder.itemView.findViewById(R.id.discount_text)
        totalAmount = holder.itemView.findViewById(R.id.total_amount)
        itemName = holder.itemView.findViewById(R.id.item_title)

        val isExpanded = position == mExpandedPosition
        descriptionTextView.text =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex exercitation ullamco laboris nisi ut aliquip ex exercitation ullamco laboris nisi ut aliquip ex exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
        descriptionTextView.visibility = if (isExpanded) View.VISIBLE else View.GONE
        holder.itemView.isActivated = isExpanded

        holder.itemView.setOnClickListener {
            itemViewAdapter.visibility= View.VISIBLE
            mExpandedPosition = if (isExpanded) -1 else position
            notifyItemChanged(previousExpandedPosition)
            notifyItemChanged(position)
        }

        if (isExpanded) {
//            previousExpandedPosition = position
            descText.text = LocalizationManager.getValue("hideDesc", "ItemList")
        } else {
            descText.text = LocalizationManager.getValue("showDesc", "ItemList")
        }

    }

    private fun setFonts() {
        itemName.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoRegular
            )
        )
        totalAmount.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoRegular
            )
        )
        discount.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoLight
            )
        )
        descText.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoLight
            )
        )
        descriptionTextView.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoLight
            )
        )
        itemViewAdapter.setItemViewDataSource(getItemViewDataSource())
        totalQuantity.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoRegular
            )
        )
    }

//    private fun checkItemListPosition(position: Int) {
//        if (itemList[position] % 2 == 0) {
//            discount.visibility= View.VISIBLE
//            discount.text = LocalizationManager.getValue("Discount", "ItemList")
//            totalAmount.paintFlags = totalAmount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//            itemName.text = "ITEM TITLE " + itemList[position]
//        } else {
////            discount.visibility= View.INVISIBLE
////            totalAmount.paintFlags = totalAmount.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
//            itemName.text = "VERY LOOOONNGGGG ITEM TITLE ITEM TITLE TITLE ITEM TITLETITLE ITEM TITLETITLE ITEM TITLETITLE ITEM TITLETITLE ITEM TITLETITLE ITEM TITLE " + itemList[position]
//        }
//    }


    fun setTheme() {

        itemViewAdapter.setBackgroundColor(Color.parseColor(ThemeManager.getValue("itemsList.backgroundColor")))
        val descriptionTextViewTheme = TextViewTheme()
        descriptionTextViewTheme.textColor =
            Color.parseColor(ThemeManager.getValue("itemsList.item.descLabelColor"))
        descriptionTextViewTheme.textSize = ThemeManager.getFontSize("itemsList.item.descLabelFont")
        descriptionTextViewTheme.font = ThemeManager.getFontName("itemsList.item.descLabelFont")
        descriptionTextView.setTheme(descriptionTextViewTheme)
        descText.setTheme(descriptionTextViewTheme)
        discount.setTheme(descriptionTextViewTheme)
        descriptionTextView.setTheme(descriptionTextViewTheme)

        val totalQuantityTextViewTheme = TextViewTheme()
        totalQuantityTextViewTheme.textColor =
            Color.parseColor(ThemeManager.getValue("itemsList.backgroundColor"))
        totalQuantityTextViewTheme.textSize =
            ThemeManager.getFontSize("itemsList.item.count.countLabelFont")
        totalQuantityTextViewTheme.font =
            ThemeManager.getFontName("itemsList.item.count.countLabelFont")
        totalQuantity.setTheme(totalQuantityTextViewTheme)


        val totalAmountTextViewTheme = TextViewTheme()
        totalAmountTextViewTheme.textColor =
            Color.parseColor(ThemeManager.getValue("itemsList.item.calculatedPriceLabelColor"))
        totalAmountTextViewTheme.textSize =
            ThemeManager.getFontSize("itemsList.item.calculatedPriceLabelFont")
        totalAmountTextViewTheme.font =
            ThemeManager.getFontName("itemsList.item.calculatedPriceLabelFont")
        totalAmount.setTheme(totalAmountTextViewTheme)


        val itemTitleTextViewTheme = TextViewTheme()
        itemTitleTextViewTheme.textColor =
            Color.parseColor(ThemeManager.getValue("itemsList.item.titleLabelColor"))
        itemTitleTextViewTheme.textSize = ThemeManager.getFontSize("itemsList.item.titleLabelFont")
        itemTitleTextViewTheme.font = ThemeManager.getFontName("itemsList.item.titleLabelFont")
        itemName.setTheme(itemTitleTextViewTheme)


        val separatorViewTheme = SeparatorViewTheme()
        separatorViewTheme.strokeColor =
            Color.parseColor(ThemeManager.getValue("itemsList.separatorColor"))
        itemSeparator.setTheme(separatorViewTheme)

    }

    private fun getItemViewDataSource(): ItemViewDataSource {
        return ItemViewDataSource(
            itemAmount = "KD000,000.000",
            totalAmount = "KD000,000.000",
            totalQuantity = "2"
        )
    }




}
