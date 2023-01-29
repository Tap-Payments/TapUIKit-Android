package company.tap.tapuilibrary.uikit.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.widget.*
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.TooltipCompat
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.fontskit.enums.TapFont
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.themekit.theme.SwitchTheme
import company.tap.tapuilibrary.themekit.theme.TextViewTheme
import company.tap.tapuilibrary.uikit.atoms.TapImageView
import company.tap.tapuilibrary.uikit.atoms.TapSwitch
import company.tap.tapuilibrary.uikit.atoms.TapTextView
import company.tap.tapuilibrary.uikit.datasource.TapSwitchDataSource
import company.tap.tapuilibrary.uikit.interfaces.TapActionButtonInterface

/**
Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/

/**
 * TapCardSwitch is a molecule element for setting saveMobile ,saveMerchantCheckout and
 *  saveGoPayCheckout for Merchant
 **/
class TapInlineCardSwitch : LinearLayout {

    val switchSaveCard by lazy { findViewById<TapSwitch>(R.id.switchSaveCard) }


    val switchesLayout by lazy { findViewById<RelativeLayout>(R.id.switchesLayout) }
    val tapCardSwitchLinear by lazy { findViewById<LinearLayout>(R.id.tapCardSwitchLinear) }
    val payButton by lazy { findViewById<TabAnimatedActionButton>(R.id.payButton) }
    val brandingLayout by lazy { findViewById<LinearLayout>(R.id.brandingLayout) }
    val textViewPowered by lazy { findViewById<TapTextView>(R.id.textViewPowered) }
    val toolsTipImageView by lazy { findViewById<TapImageView>(R.id.toolsTipImageView) }
    val secondaryLayout by lazy { findViewById<LinearLayout>(R.id.secondary_Layout) }
    val saveForOtherCheckBox by lazy { findViewById<CheckBox>(R.id.saveForOtherCheckBox) }
    private var actionButtonInterface: TapActionButtonInterface? = null
    val tapLogoImage by lazy { findViewById<TapImageView>(R.id.tapLogoImage) }
    //val tapTextView by lazy { findViewById<TapTextView>(R.id.textTap_label) }

    @DrawableRes
    val logoIcon: Int =
        if (ThemeManager.currentTheme.isNotEmpty() && ThemeManager.currentTheme.contains("dark")){
            R.drawable.tap_logo_dark_horizontal
        } else if (ThemeManager.currentTheme.isNotEmpty() && ThemeManager.currentTheme.contains("light")) {
            R.drawable.tap_logo_light_horizontal
        }else R.drawable.tap_logo_light_horizontal

    @DrawableRes
    val toolsTipIcon: Int =
        if (ThemeManager.currentTheme.isNotEmpty() && ThemeManager.currentTheme.contains("dark")){
            R.drawable.toolstip_darkmode
        } else if (ThemeManager.currentTheme.isNotEmpty() && ThemeManager.currentTheme.contains("light")) {
            R.drawable.toolstip_lightmode
        }else R.drawable.toolstip_lightmode



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
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        inflate(context, R.layout.tap_inline_card_switch, this)
        setTheme()
        //  if (context?.let { LocalizationManager.getLocale(it).language } == "en") setFontsEnglish() else setFontsArabic()
        initActionButton()
        initViews()
        if (context?.let { LocalizationManager.getLocale(it).language } == "en") setFontsEnglish() else setFontsArabic()
    }

    private fun initViews() {
        val tootlsTipTextVal:String= LocalizationManager.getValue("cardSaveForTapInfo","TapCardInputKit")
        toolsTipImageView.setOnClickListener {
            TooltipCompat.setTooltipText(toolsTipImageView, tootlsTipTextVal)

        }
        TooltipCompat.setTooltipText(toolsTipImageView, tootlsTipTextVal)

        setThemeForView()

    }

    private fun setThemeForView() {

        saveForOtherCheckBox?.setTextColor(Color.parseColor(ThemeManager.getValue("inlineCard.saveCardForTapOption.labelTextColor")))
        saveForOtherCheckBox?.textSize=12.0f
        saveForOtherCheckBox.buttonTintList= ColorStateList.valueOf(Color.parseColor(ThemeManager.getValue("inlineCard.saveCardForTapOption.saveButtonActivatedTintColor")))

        switchSaveCard?.setTextColor(Color.parseColor(ThemeManager.getValue("inlineCard.saveCardOption.labelTextColor")))
        switchSaveCard?.textSize=14.0f
        switchSaveCard?.buttonTintList=ColorStateList.valueOf(Color.parseColor(ThemeManager.getValue("inlineCard.saveCardOption.switchThumbColor")))

        switchSaveCard?.trackTintList= ColorStateList.valueOf(Color.parseColor(ThemeManager.getValue("inlineCard.saveCardOption.switchTintColor")))
        switchSaveCard?.thumbTintList= ColorStateList.valueOf(Color.parseColor(ThemeManager.getValue("inlineCard.saveCardOption.switchThumbColor")))
        if(switchSaveCard.isChecked){
            switchSaveCard?.thumbTintList= ColorStateList.valueOf(Color.parseColor(ThemeManager.getValue("inlineCard.saveCardOption.switchOnThumbColor")))

        }
        saveForOtherCheckBox.text =  LocalizationManager.getValue("cardSaveForTapLabel","TapCardInputKit")
       
        tapLogoImage.setImageResource(logoIcon)
        toolsTipImageView.setImageResource(toolsTipIcon)
    }

    fun setSwitchInterface(actionButtonInterface: TapActionButtonInterface) {
        this.actionButtonInterface = actionButtonInterface
    }
    fun onEnterValidCardNumberActionListener(){
        payButton.setOnClickListener { actionButtonInterface?.onEnterValidCardNumberActionListener() }
    }
    fun onEnterValidPhoneNumberActionListener(){
        payButton.setOnClickListener { actionButtonInterface?.onEnterValidPhoneNumberActionListener() }
    }
    fun onSelectPaymentOptionActionListener(){
        payButton.setOnClickListener { actionButtonInterface?.onSelectPaymentOptionActionListener() }
    }

    private fun initActionButton() {
        payButton.setButtonDataSource(
            false,
            context?.let { LocalizationManager.getLocale(it).language },
            LocalizationManager.getValue("pay", "ActionButton"),
            Color.parseColor(ThemeManager.getValue("actionButton.Invalid.backgroundColor")),
            Color.parseColor(ThemeManager.getValue("actionButton.Invalid.titleLabelColor"))
        )
        payButton.isEnabled = true
        payButton.isClickable = true
    }

    fun showOnlyPayButton(){
        switchesLayout.visibility = View.GONE
        // payButton.visibility = View.VISIBLE
    }


    /**
     * @param tapSwitchDataSource is set via the consumer app for saveMobile,
     * saveMerchantCheckout and savegoPayCheckout.
     **/
    fun setSwitchDataSource(tapSwitchDataSource: TapSwitchDataSource) {
        this.tapSwitchDataSource = tapSwitchDataSource
        tapSwitchDataSource.switchSaveMerchantCheckout?.let {
            switchSaveCard.text = it
        }

    }


    fun setTheme() {

        // Merchant
        switchSaveCard.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                var switchSaveMerchantSwitchThemeEnable = SwitchTheme()
                switchSaveMerchantSwitchThemeEnable.thumbTint =
                    Color.parseColor(ThemeManager.getValue("TapSwitchView.merchant.SwitchOnColor"))
                // switchSaveMerchantSwitchThemeEnable.trackTint =
                //     Color.parseColor(ThemeManager.getValue("TapSwitchView.merchant.SwitchOnColor"))
                switchSaveCard.setTheme(switchSaveMerchantSwitchThemeEnable)
                payButton.setButtonDataSource(
                    true,
                    context?.let { LocalizationManager.getLocale(it).language },
                    LocalizationManager.getValue("pay", "ActionButton"),
                    Color.parseColor(ThemeManager.getValue("actionButton.Valid.paymentBackgroundColor")),
                    Color.parseColor(ThemeManager.getValue("actionButton.Valid.titleLabelColor"))
                )

                secondaryLayout.visibility = View.VISIBLE


            } else {
                tapCardSwitchLinear.setBackgroundColor(Color.parseColor(ThemeManager.getValue("TapSwitchView.main.backgroundColor")))
                val switchSaveMerchantSwitchThemeDisable = SwitchTheme()
                switchSaveMerchantSwitchThemeDisable.thumbTint =
                    Color.parseColor(ThemeManager.getValue("TapSwitchView.main.backgroundColor"))
                //  switchSaveMerchantSwitchThemeDisable.trackTint =
                //      Color.parseColor(ThemeManager.getValue("TapSwitchView.main.backgroundColor"))
                switchSaveCard.setTheme(switchSaveMerchantSwitchThemeDisable)
                payButton.setButtonDataSource(
                    false,
                    context?.let { LocalizationManager.getLocale(it).language },
                    LocalizationManager.getValue("pay", "ActionButton"),
                    Color.parseColor(ThemeManager.getValue("actionButton.Invalid.backgroundColor")),
                    Color.parseColor(ThemeManager.getValue("actionButton.Invalid.titleLabelColor"))
                )
                secondaryLayout.visibility = View.GONE

            }
        }





        var poweredByTextViewTheme = TextViewTheme()
        poweredByTextViewTheme.textColor = Color.parseColor(ThemeManager.getValue("poweredByTap.powerLabel.textColor"))
        poweredByTextViewTheme.textSize =
            ThemeManager.getFontSize("poweredByTap.powerLabel.font")
        poweredByTextViewTheme.font = ThemeManager.getFontName("poweredByTap.powerLabel.font")
        textViewPowered.setTheme(poweredByTextViewTheme)


        var tapTextViewTheme = TextViewTheme()
        tapTextViewTheme.textColor = Color.parseColor(ThemeManager.getValue("poweredByTap.powerLabel.textColor"))
        tapTextViewTheme.textSize =
            ThemeManager.getFontSize("poweredByTap.powerLabel.font")
        tapTextViewTheme.font = ThemeManager.getFontName("poweredByTap.powerLabel.font")
       // tapTextView.setTheme(tapTextViewTheme)
    }


    private fun activateButton(isActive: Boolean) {
        if (isActive) {
            payButton.setButtonDataSource(
                true,
                context?.let { LocalizationManager.getLocale(it).language },
                LocalizationManager.getValue("pay", "ActionButton"),
                Color.parseColor(ThemeManager.getValue("actionButton.Valid.paymentBackgroundColor")),
                Color.parseColor(ThemeManager.getValue("actionButton.Valid.titleLabelColor"))
            )
        } else {
            payButton.setButtonDataSource(
                false,
                context?.let { LocalizationManager.getLocale(it).language },
                LocalizationManager.getValue("pay", "ActionButton"),
                Color.parseColor(ThemeManager.getValue("actionButton.Invalid.backgroundColor")),
                Color.parseColor(ThemeManager.getValue("actionButton.Invalid.titleLabelColor"))
            )
        }
    }


    fun setFontsEnglish() {
        saveForOtherCheckBox?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoRegular
            )
        )
        switchSaveCard?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoRegular
            )
        )

    }

    fun setFontsArabic() {
        saveForOtherCheckBox?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.TajawalMedium
            )
        )

        switchSaveCard?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.TajawalMedium
            )
        )

    }


}