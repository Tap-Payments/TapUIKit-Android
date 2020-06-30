package company.tap.tapuilibrary.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.atoms.TapSwitch
import company.tap.tapuilibrary.atoms.TapTextView
import company.tap.tapuilibrary.datasource.TapSwitchDataSource

/**
 * Created by AhlaamK on 6/30/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/


class TapCardSwitch: LinearLayout {
    private var switchSave: TapSwitch
    private var saveTextView:TapTextView
    private var switchSaveMerchant: TapSwitch
    private var switchgoPay: TapSwitch
    private var savegoPay: TapTextView
    private var alertgoPaySignup: TapTextView
    private var tapSwitchDataSource: TapSwitchDataSource? = null

    /**
     * Simple constructor to use when creating a TapPayCardSwitch from code.
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
        inflate(context, R.layout.tap_card_switch, this)
        switchSave = findViewById(R.id.switch_save)
        switchSaveMerchant = findViewById(R.id.switch_merchant_checkout)
        switchgoPay = findViewById(R.id.switch_gopay_mobile)
        savegoPay = findViewById(R.id.save_goPay)
        alertgoPaySignup = findViewById(R.id.alert_gopay_signup)
        saveTextView = findViewById(R.id.text_save)
    }

    /**
     * @param tapSwitchDataSource is set via the consumer app for businessName,
     * businessIcon and businessPlaceHolder.
     **/
    fun setSwitchDataSource(tapSwitchDataSource: TapSwitchDataSource) {
        this.tapSwitchDataSource = tapSwitchDataSource
        tapSwitchDataSource.switchSave?.let {
            saveTextView.text = it
        }
        tapSwitchDataSource.switchMerchantCheckout?.let {
            switchSaveMerchant.text=it
        }
        tapSwitchDataSource.switchgoPayMobile?.let {
            switchgoPay.text = it
        }
        tapSwitchDataSource.alertgoPaySignup?.let {
            alertgoPaySignup.text = it
        }
        tapSwitchDataSource.savegoPayText?.let {
            savegoPay.text = it
        }

    }
}