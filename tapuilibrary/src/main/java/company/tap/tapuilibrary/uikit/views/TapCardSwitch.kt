package company.tap.tapuilibrary.uikit.views

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.widget.SwitchCompat
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.fontskit.enums.TapFont
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.themekit.theme.SeparatorViewTheme
import company.tap.tapuilibrary.themekit.theme.SwitchTheme
import company.tap.tapuilibrary.themekit.theme.TextViewTheme
import company.tap.tapuilibrary.uikit.atoms.TapChip
import company.tap.tapuilibrary.uikit.atoms.TapSeparatorView
import company.tap.tapuilibrary.uikit.atoms.TapSwitch
import company.tap.tapuilibrary.uikit.atoms.TapTextView
import company.tap.tapuilibrary.uikit.datasource.TapSwitchDataSource
import jp.wasabeef.blurry.Blurry


/**
Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/

/**
 * TapCardSwitch is a molecule element for setting saveMobile ,saveMerchantCheckout and
 *  saveGoPayCheckout for Merchant
 **/
class TapCardSwitch : LinearLayout {
    val switchSaveMobile by lazy { findViewById<TapSwitch>(R.id.switch_save_mobile) }
    val saveTextView by lazy { findViewById<TapTextView>(R.id.text_save) }
    val saveGoPay by lazy { findViewById<TapTextView>(R.id.save_goPay) }
    val alertGoPaySignUp by lazy { findViewById<TapTextView>(R.id.alert_gopay_signup) }
    val switchSaveMerchant by lazy { findViewById<TapSwitch>(R.id.switch_merchant_checkout) }
    val switchGoPayCheckout by lazy { findViewById<TapSwitch>(R.id.switch_gopay_checkout) }
    val tapCardSwitchLinear by lazy { findViewById<LinearLayout>(R.id.tapCardSwitchLinear) }
    val saveSwitchLinear by lazy { findViewById<LinearLayout>(R.id.save_switch_linear) }
    val switchesLayout by lazy { findViewById<LinearLayout>(R.id.switches_layout) }
    val saveSwitchChip by lazy { findViewById<TapChip>(R.id.saveSwitchChip) }
    val switchSeparator by lazy { findViewById<TapSeparatorView>(R.id.switch_separator) }
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
        setTheme()
        if (context?.let { LocalizationManager.getLocale(it).language } == "en") setFontsEnglish() else setFontsArabic()
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
            switchSaveMerchant.text = it
        }
        tapSwitchDataSource.switchSavegoPayCheckout?.let {
            switchGoPayCheckout.text = it
        }
        tapSwitchDataSource.alertgoPaySignup?.let {
            alertGoPaySignUp.text = it
        }
        tapSwitchDataSource.savegoPayText?.let {
            saveGoPay.text = it
        }
    }


    fun setTheme() {
//        tapCardSwitchLinear.setBackgroundColor(Color.parseColor(ThemeManager.getValue("TapSwitchView.backgroundColor")))
//        saveSwitchLinear.setBackgroundColor(Color.parseColor(ThemeManager.getValue("TapSwitchView.backgroundColor")))
        saveSwitchChip.setBackgroundColor(Color.parseColor(ThemeManager.getValue("TapSwitchView.backgroundColor")))
//        switchesLayout.setBackgroundColor(Color.parseColor(ThemeManager.getValue("TapSwitchView.main.backgroundColor")))
//        Blurry.with(context).radius(5).sampling(1).onto(switchesLayout)

//        switchSaveMerchant.setBackgroundColor(Color.parseColor(ThemeManager.getValue("TapSwitchView.main.backgroundColor")))
//        switchGoPayCheckout.setBackgroundColor(Color.parseColor(ThemeManager.getValue("TapSwitchView.goPay.backgroundColor")))

        // Main switch
        switchSaveMobile.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Log.d("true", "true")
                var switchSaveMobileSwitchThemeEnable = SwitchTheme()
                switchSaveMobileSwitchThemeEnable.thumbTint =
                    Color.parseColor(ThemeManager.getValue("TapSwitchView.goPay.SwitchOnColor"))
                switchSaveMobileSwitchThemeEnable.trackTint =
                    Color.parseColor(ThemeManager.getValue("TapSwitchView.goPay.SwitchOnColor"))
                switchSaveMobile.setTheme(switchSaveMobileSwitchThemeEnable)

            } else {
                Log.d("false", "false")
                var switchSaveMobileSwitchThemeDisable = SwitchTheme()
                switchSaveMobileSwitchThemeDisable.thumbTint =
                    Color.parseColor(ThemeManager.getValue("TapSwitchView.main.backgroundColor"))
                switchSaveMobileSwitchThemeDisable.trackTint =
                    Color.parseColor(ThemeManager.getValue("TapSwitchView.main.backgroundColor"))
                switchSaveMobile.setTheme(switchSaveMobileSwitchThemeDisable)
            }
        }

        // Merchant
        switchSaveMobile.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Log.d("true", "true")
                var switchSaveMerchantSwitchThemeEnable = SwitchTheme()
                switchSaveMerchantSwitchThemeEnable.thumbTint =
                    Color.parseColor(ThemeManager.getValue("TapSwitchView.merchant.SwitchOnColor"))
                switchSaveMerchantSwitchThemeEnable.trackTint =
                    Color.parseColor(ThemeManager.getValue("TapSwitchView.merchant.SwitchOnColor"))
                switchSaveMobile.setTheme(switchSaveMerchantSwitchThemeEnable)
            } else {
                Log.d("false", "false")
                var switchSaveMerchantSwitchThemeDisable = SwitchTheme()
                switchSaveMerchantSwitchThemeDisable.thumbTint =
                    Color.parseColor(ThemeManager.getValue("TapSwitchView.main.backgroundColor"))
                switchSaveMerchantSwitchThemeDisable.trackTint =
                    Color.parseColor(ThemeManager.getValue("TapSwitchView.main.backgroundColor"))
                switchSaveMobile.setTheme(switchSaveMerchantSwitchThemeDisable)

            }
        }


        // Go Pay
        switchSaveMobile.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Log.d("true", "true")
                var switchGoPayCheckoutSwitchThemeEnable = SwitchTheme()
                switchGoPayCheckoutSwitchThemeEnable.thumbTint =
                    Color.parseColor(ThemeManager.getValue("TapSwitchView.goPay.SwitchOnColor"))
                switchGoPayCheckoutSwitchThemeEnable.trackTint =
                    Color.parseColor(ThemeManager.getValue("TapSwitchView.goPay.SwitchOnColor"))
                switchSaveMobile.setTheme(switchGoPayCheckoutSwitchThemeEnable)

            } else {
                Log.d("false", "false")
                var switchGoPayCheckoutSwitchThemeDisable = SwitchTheme()
                switchGoPayCheckoutSwitchThemeDisable.thumbTint =
                    Color.parseColor(ThemeManager.getValue("TapSwitchView.main.backgroundColor"))
                switchGoPayCheckoutSwitchThemeDisable.trackTint =
                    Color.parseColor(ThemeManager.getValue("TapSwitchView.main.backgroundColor"))
                switchSaveMobile.setTheme(switchGoPayCheckoutSwitchThemeDisable)
            }
        }


        // main save
        var saveTextViewTextViewTheme = TextViewTheme()
        saveTextViewTextViewTheme.textColor =
            Color.parseColor(ThemeManager.getValue("TapSwitchView.main.title.textColor"))
        saveTextViewTextViewTheme.textSize =
            ThemeManager.getFontSize("TapSwitchView.main.title.textFont")
        saveTextViewTextViewTheme.font =
            ThemeManager.getFontName("TapSwitchView.main.title.textFont")
        saveTextView.setTheme(saveTextViewTextViewTheme)

        // Go Pay Text
        var saveGoPayTextViewTheme = TextViewTheme()
        saveGoPayTextViewTheme.textColor =
            Color.parseColor(ThemeManager.getValue("TapSwitchView.goPay.title.textColor"))
        saveGoPayTextViewTheme.textSize =
            ThemeManager.getFontSize("TapSwitchView.goPay.title.textFont")
        saveGoPayTextViewTheme.font = ThemeManager.getFontName("TapSwitchView.goPay.title.textFont")
        saveGoPay.setTheme(saveGoPayTextViewTheme)

        var alertGoPaySignUpTextViewTheme = TextViewTheme()
        alertGoPaySignUpTextViewTheme.textColor =
            Color.parseColor(ThemeManager.getValue("TapSwitchView.merchant.notes.textColor"))
        alertGoPaySignUpTextViewTheme.textSize =
            ThemeManager.getFontSize("TapSwitchView.merchant.notes.textFont")
        alertGoPaySignUpTextViewTheme.font =
            ThemeManager.getFontName("TapSwitchView.merchant.notes.textFont")
        alertGoPaySignUp.setTheme(alertGoPaySignUpTextViewTheme)


        var separatorViewTheme = SeparatorViewTheme()
        separatorViewTheme.strokeColor =
            Color.parseColor(ThemeManager.getValue("TapSwitchView.CurvedSeparator.BackgroundColor"))
        switchSeparator.setTheme(separatorViewTheme)
    }


    fun setFontsEnglish() {
        saveTextView?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoLight
            )
        )

        saveGoPay?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoLight
            )
        )

        alertGoPaySignUp?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoLight
            )
        )

    }

    fun setFontsArabic() {
        saveTextView?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.TajawalLight
            )
        )

        saveGoPay?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.TajawalLight
            )
        )

        alertGoPaySignUp?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.TajawalLight
            )
        )

    }


}