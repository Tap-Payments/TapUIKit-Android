package company.tap.tapuilibrary.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.atoms.TapImageView
import company.tap.tapuilibrary.atoms.TapTextView
import company.tap.tapuilibrary.datasource.HeaderDataSource
import company.tap.tapuilibrary.utils.TapAsyncUtil


/**
 * Created by AhlaamK on 4/29/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/

/**
 * TapHeader is a molecule for setting Business Name and Business Logo for Merchant
 * **/
class TapHeader : LinearLayout {
    /**
     * @param businessIcon is for setting the business Logo
     * */
    private var businessIcon: TapImageView
    private var businessName: TapTextView
    private var paymentFor: TapTextView
    private var businessPlaceholder: TapTextView
    private var headerDataSource: HeaderDataSource? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        inflate(context, R.layout.tap_main_header, this)
        businessIcon = findViewById(R.id.business_icon)
        businessName = findViewById(R.id.business_name)
        paymentFor = findViewById(R.id.payment_for)
        businessPlaceholder = findViewById(R.id.placeholder_text)
    }


    fun setHeaderDataSource(headerDataSource: HeaderDataSource) {
        this.headerDataSource = headerDataSource
        headerDataSource.businessName?.let {
            businessName.text = it
        }
        headerDataSource.businessImageResources?.let {
            businessPlaceholder.visibility = View.VISIBLE
            TapAsyncUtil.DownLoadImageTask(businessIcon, businessPlaceholder).execute(it)
        }
        headerDataSource.businessFor?.let {
            paymentFor.text = it
        }
        headerDataSource.businessPlaceHolder?.let {
            businessPlaceholder.text = it
        }

    }


}



