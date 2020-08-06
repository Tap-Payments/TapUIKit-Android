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

/**
 * TapItemsView  is a molecule for setting Amount, Description and Quantity.
 */
class TapListItemView:LinearLayout {
     var itemTitle: TapTextView
     var totalAmount: TapTextView
     var totalQuantity: TapTextView
     var itemAmount: TapTextView
    private var itemViewDataSource: ItemViewDataSource? = null
    /**
     * Simple constructor to use when creating a TapItemsView from code.
     *  @param context The Context the view is running in, through which it can
     *  access the current theme, resources, etc.
     **/
    constructor(context: Context) : super(context)
    /**
     *  @param context The Context the view is running in, through which it can
     *  access the current theme, resources, etc.
     *  @param attrs The attributes of the XML Button tag being used to inflate the view.
     *
     */
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    /**
     *  @param context The Context the view is running in, through which it can
     *  access the current theme, resources, etc.
     *  @param attrs The attributes of the XML Button tag being used to inflate the view.
     * @param defStyleAttr The resource identifier of an attribute in the current theme
     * whose value is the the resource id of a style. The specified style’s
     * attribute values serve as default values for the button. Set this parameter
     * to 0 to avoid use of default values.
     */
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    init {
        inflate(context, R.layout.tap_items_view_, this)
        itemTitle = findViewById(R.id.item_title)
        itemAmount = findViewById(R.id.item_amount)
        totalAmount = findViewById(R.id.total_amount)
        totalQuantity = findViewById(R.id.total_quantity)
    }

    /**
     * @param itemViewDataSource is set via the consumer app for itemTitle,
     * itemAmount , totalAmount and totalQuantity .
     **/
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