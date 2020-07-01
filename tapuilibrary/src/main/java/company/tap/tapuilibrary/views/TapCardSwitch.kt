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

/**
 * TapCardSwitch is a molecule element for setting saveMobile ,saveMerchantCheckout and
 *  savegoPayCheckout for Merchant
 **/
class TapCardSwitch: LinearLayout {
    private var switchSaveMobile: TapSwitch
    private var saveTextView:TapTextView
    private var switchSaveMerchant: TapSwitch
    private var switchgoPayCheckout: TapSwitch
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
        switchSaveMobile = findViewById(R.id.switch_save_mobile)
        switchSaveMerchant = findViewById(R.id.switch_merchant_checkout)
        switchgoPayCheckout = findViewById(R.id.switch_gopay_checkout)
        savegoPay = findViewById(R.id.save_goPay)
        alertgoPaySignup = findViewById(R.id.alert_gopay_signup)
        saveTextView = findViewById(R.id.text_save_mobile)
    }

    /**
     * @param tapSwitchDataSource is set via the consumer app for saveMobile,
     * saveMerchantCheckout and savegoPayCheckout.
     **/
    fun setSwitchDataSource(tapSwitchDataSource: TapSwitchDataSource) {
        this.tapSwitchDataSource = tapSwitchDataSource
        tapSwitchDataSource.switchSaveMobile?.let {
            saveTextView.text = it
        }
        tapSwitchDataSource.switchSaveMerchantCheckout?.let {
            switchSaveMerchant.text=it
        }
        tapSwitchDataSource.switchSavegoPayCheckout?.let {
            switchgoPayCheckout.text = it
        }
        tapSwitchDataSource.alertgoPaySignup?.let {
            alertgoPaySignup.text = it
        }
        tapSwitchDataSource.savegoPayText?.let {
            savegoPay.text = it
        }

    }
}