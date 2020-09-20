package company.tap.tapuilibrary.uikit.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.LinearLayout
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.fontskit.enums.TapFont
import company.tap.tapuilibrary.uikit.atoms.TapTextView

/**
 * Created  on 8/10/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
class TapAlertView : LinearLayout {

    val alertMessage by lazy { findViewById<TapTextView>(R.id.textViewAlertMessage) }
    val tapAlertLinear by lazy { findViewById<LinearLayout>(R.id.tapAlertLinear) }


    /**
     * Simple constructor to use when creating a TapAlertView from code.
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
            R.layout.tap_alert_view, this
        )
        if (context?.let { LocalizationManager.getLocale(it).language } == "en") {
            setFontsEnglish()
        } else {
            setFontsArabic()
        }


    }

    fun setFontsEnglish() {
        alertMessage?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoLight
            )
        )
    }

    fun setFontsArabic() {
        alertMessage?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.TajawalLight
            )
        )
    }


}