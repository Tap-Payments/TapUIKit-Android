package company.tap.tapuilibrary.uikit.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.themekit.theme.SwitchTheme
import company.tap.tapuilibrary.themekit.theme.TextViewTheme
import company.tap.tapuilibrary.uikit.atoms.TapSwitch
import company.tap.tapuilibrary.uikit.atoms.TapTextView
import company.tap.tapuilibrary.uikit.datasource.TapSwitchDataSource

/**
Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/

/**
 * TapCardSwitch is a molecule element for setting saveMobile ,saveMerchantCheckout and
 *  savegoPayCheckout for Merchant
 **/
class TapCardSwitch: LinearLayout {
     private var switchSaveMobile: TapSwitch
     private var saveTextView: TapTextView
     private var switchSaveMerchant: TapSwitch
     private var switchgoPayCheckout: TapSwitch
     private var savegoPay: TapTextView
     private var alertgoPaySignup: TapTextView
     private var tapCardSwitchLinear: LinearLayout
     lateinit var attrs: AttributeSet
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
        tapCardSwitchLinear = findViewById(R.id.tapCardSwitchLinear)
        saveTextView = findViewById(R.id.text_save)
        setTheme()
    }

    /**
     * @param tapSwitchDataSource is set via the consumer app for saveMobile,
     * saveMerchantCheckout and savegoPayCheckout.
     **/
    fun setSwitchDataSource(tapSwitchDataSource: TapSwitchDataSource) {
        this.tapSwitchDataSource = tapSwitchDataSource
        tapSwitchDataSource.switchSave?.let {
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


    fun setTheme(){

        tapCardSwitchLinear.setBackgroundColor(Color.parseColor(ThemeManager.getValue("TapSwitchView.backgroundColor")))

        // Main switch
        var switchSaveMobileSwitchTheme = SwitchTheme()
        switchSaveMobileSwitchTheme.thumbTint = Color.parseColor(ThemeManager.getValue("TapSwitchView.main.whiteTwo"))
        switchSaveMobileSwitchTheme.trackTint = Color.parseColor(ThemeManager.getValue("TapSwitchView.main.whiteTwo"))
        switchSaveMobile.setTheme(switchSaveMobileSwitchTheme)

        // Merchant
        var switchSaveMerchantSwitchTheme = SwitchTheme()
        switchSaveMerchantSwitchTheme.thumbTint = Color.parseColor(ThemeManager.getValue("TapSwitchView.merchant.SwitchOnColor"))
        switchSaveMerchantSwitchTheme.trackTint = Color.parseColor(ThemeManager.getValue("TapSwitchView.merchant.SwitchOnColor"))
        switchSaveMerchant.setTheme(switchSaveMerchantSwitchTheme)

        // Go Pay
        var switchGoPayCheckoutSwitchTheme = SwitchTheme()
        switchGoPayCheckoutSwitchTheme.thumbTint = Color.parseColor(ThemeManager.getValue("TapSwitchView.goPay.SwitchOnColor"))
        switchGoPayCheckoutSwitchTheme.trackTint = Color.parseColor(ThemeManager.getValue("TapSwitchView.goPay.SwitchOnColor"))
        switchgoPayCheckout.setTheme(switchGoPayCheckoutSwitchTheme)

        // main save
        var saveTextViewTextViewTheme = TextViewTheme()
        saveTextViewTextViewTheme.textColor = Color.parseColor(ThemeManager.getValue("TapSwitchView.main.title.textColor"))
        saveTextViewTextViewTheme.textSize = ThemeManager.getFontSize("TapSwitchView.main.title.textFont")
        saveTextViewTextViewTheme.font = ThemeManager.getFontName("TapSwitchView.main.title.textFont")
        saveTextView.setTheme(saveTextViewTextViewTheme)

        // Go Pay Text
        var saveGoPayTextViewTheme = TextViewTheme()
        saveGoPayTextViewTheme.textColor = Color.parseColor(ThemeManager.getValue("TapSwitchView.goPay.title.textColor"))
        saveGoPayTextViewTheme.textSize = ThemeManager.getFontSize("TapSwitchView.goPay.title.textFont")
        saveGoPayTextViewTheme.font = ThemeManager.getFontName("TapSwitchView.goPay.title.textFont")
        savegoPay.setTheme(saveGoPayTextViewTheme)

        var alertGoPaySignUpTextViewTheme = TextViewTheme()
        alertGoPaySignUpTextViewTheme.textColor = Color.parseColor(ThemeManager.getValue("TapSwitchView.merchant.notes.textColor"))
        alertGoPaySignUpTextViewTheme.textSize = ThemeManager.getFontSize("TapSwitchView.merchant.notes.textFont")
        alertGoPaySignUpTextViewTheme.font = ThemeManager.getFontName("TapSwitchView.merchant.notes.textFont")
        alertgoPaySignup.setTheme(alertGoPaySignUpTextViewTheme)
    }


}