package company.tap.tapuilibrary.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.atoms.TapImageView
import company.tap.tapuilibrary.atoms.TapTextView
import company.tap.tapuilibrary.datasource.ItemViewDataSource

/**
 * Created by AhlaamK on 6/15/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
/***
 * TapItemsView  is a molecule for setting Amount, Description and Quantity.
 */
class TapItemsView:LinearLayout {
    private var itemTitle: TapTextView
    private var totalAmount: TapTextView
    private var totalQuantity: TapTextView
    private var itemAmount: TapTextView
    private var itemViewDataSource: ItemViewDataSource? = null
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    init {
        inflate(context, R.layout.tap_items_view, this)
        itemTitle = findViewById(R.id.item_title)
        itemAmount = findViewById(R.id.item_amount)
        totalAmount = findViewById(R.id.total_amount)
        totalQuantity = findViewById(R.id.total_quantity)
    }
    fun setItemViewDataSource(itemViewDataSource: ItemViewDataSource) {
        this.itemViewDataSource = itemViewDataSource
        itemViewDataSource.itemTitle?.let {
            itemTitle.text = it
        }
        itemViewDataSource.itemAmount?.let {
            itemAmount.text = it
        }
        itemViewDataSource.totalAmount?.let {
            totalAmount.text = it
        }
        itemViewDataSource.totalQuantity?.let {
            totalQuantity.text = it
        }

    }
}