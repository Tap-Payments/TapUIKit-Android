package company.tap.tapuilibrary.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.atoms.TapButton
import company.tap.tapuilibrary.atoms.TapTextView
import company.tap.tapuilibrary.datasource.AmountViewDataSource


/**
 * Created by AhlaamK on 6/11/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/

class TapAmountSectionView: LinearLayout {
     var selectedCurrency:TapTextView
     var currentCurrency:TapTextView
     var itemCount:TapButton
  private var amountViewDataSource: AmountViewDataSource? = null
    /**
     * Simple constructor to use when creating a TapAmountSectionView from code.
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
        inflate(
            context,
            R.layout.tap_main_amount, this
        )
        selectedCurrency = findViewById(R.id.textview_selectedcurrency)
        currentCurrency  = findViewById(R.id.textView_currentcurrency)
        itemCount = findViewById(R.id.textView_itemcount)
        itemCount.elevation = 0F
    }

    /**
     * @param amountViewDataSource is set via the consumer app for selectedCurrency,
     * currentCurrency and itemCount.
     **/
    fun setAmountViewDataSource(amountViewDataSource: AmountViewDataSource) {
        this.amountViewDataSource = amountViewDataSource
        amountViewDataSource.selectedCurr?.let {
            selectedCurrency.text = it
        }
        amountViewDataSource.currentCurr?.let {
            currentCurrency.text = it
        }
        amountViewDataSource.itemCount?.let {
            itemCount.text = it
        }

        itemCount.elevation = 0F

    }


}