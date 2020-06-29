package company.tap.tapuilibrary.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.bumptech.glide.Glide
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
 * TapHeader is a molecule element for setting businessName ,businessIcon and
 *  businessPlaceHodler for Merchant
 **/
class TapHeaderSectionView : LinearLayout {
    private var businessIcon: TapImageView
    private var businessName: TapTextView
    private var paymentFor: TapTextView
    private var businessPlaceholder: TapTextView
    private var headerDataSource: HeaderDataSource? = null
    /**
     * Simple constructor to use when creating a TapHeader from code.
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
        inflate(context, R.layout.tap_main_header, this)
        businessIcon = findViewById(R.id.business_icon)
        businessName = findViewById(R.id.business_name)
        paymentFor = findViewById(R.id.payment_for)
        businessPlaceholder = findViewById(R.id.placeholder_text)
    }

    /**
     * @param headerDataSource is set via the consumer app for businessName,
     * businessIcon and businessPlaceHolder.
     **/
    fun setHeaderDataSource(headerDataSource: HeaderDataSource) {
        this.headerDataSource = headerDataSource
        headerDataSource.businessName?.let {
            businessName.text = it
        }
        headerDataSource.businessImageResources?.let {

          //  businessPlaceholder.visibility = View.VISIBLE
           /* Glide.with(this)  //2
                .load(it) //3
                .placeholder(businessPlaceholder)
                .into(businessIcon)
            businessIcon.visibility = View.VISIBLE*/
           // businessPlaceholder.visibility = View.INVISIBLE
            // TapAsyncUtil.DownLoadImageTask(businessIcon, businessPlaceholder).execute(it)
        }
        headerDataSource.businessFor?.let {
            paymentFor.text = it
        }
        headerDataSource.businessPlaceHolder?.let {
            businessPlaceholder.text = it
        }

    }


}

/*
fun Any.into(businessIcon: TapImageView): Any {
    return businessIcon

}

private fun Any.placeholder(businessPlaceholder: TapTextView): Any {
    return businessPlaceholder

}
*/



