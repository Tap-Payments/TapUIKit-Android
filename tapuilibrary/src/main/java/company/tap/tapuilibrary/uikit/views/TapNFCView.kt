package company.tap.tapuilibrary.uikit.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.uikit.atoms.TapImageView
import company.tap.tapuilibrary.uikit.atoms.TapSwitch
import company.tap.tapuilibrary.uikit.atoms.TapTextView

/**
 * Created by AhlaamK on 7/1/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/

/**
 * TapNFCView is a molecule UI element for showing user till NFC scanning
 * is running .
 **/
class TapNFCView : LinearLayout {
    private var gifNFC: TapImageView
    val topLinearNFC by lazy { findViewById<LinearLayout>(R.id.topLinearNFC) }
    val scanNfc by lazy { findViewById<TapTextView>(R.id.scan_nfc) }
    val mainLinearNFC by lazy { findViewById<LinearLayout>(R.id.mainLinearNFC) }
    val aboutNFC by lazy { findViewById<TapTextView>(R.id.aboutNFC) }


    /**
     * Simple constructor to use when creating a TapNFCView from code.
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
        inflate(context, R.layout.tap_nfc_view, this)
        gifNFC = findViewById(R.id.gif_nfc)
        Glide.with(context).load(R.drawable.nfcgif).into(gifNFC)

    }

    fun setTheme(){

    }


}