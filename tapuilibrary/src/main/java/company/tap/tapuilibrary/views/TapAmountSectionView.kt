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
    private var selectedCurrency:TapTextView
    private var currentCurrency:TapTextView
    private var itemCount:TapButton
    private var amountViewDataSource: AmountViewDataSource? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
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
    }
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

    }


}