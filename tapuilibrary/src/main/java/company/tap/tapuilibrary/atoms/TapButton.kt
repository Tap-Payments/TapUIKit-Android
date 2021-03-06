package company.tap.tapuilibrary.atoms

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import company.tap.tapuilibrary.interfaces.TapView
import company.tap.thememanager.theme.ButtonTheme

/**
 * Created by AhlaamK on 4/15/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/

    /**
     * TapButton is a user interface element the user can tap or click to perform an action.
     * Simple constructor to use when creating a button from code.
     *  @param context The Context the view is running in, through which it can
     *  access the current theme, resources, etc.
     *  @param attributeSet The attributes of the XML Button tag being used to inflate the view.
     *
     */

open class TapButton(context: Context, attributeSet: AttributeSet) :
    AppCompatButton(context, attributeSet),
    TapView<ButtonTheme> {

    /**
     * Set the configure the current theme. If null is provided then the default Theme is returned
     * on the next call
     * @param theme Theme to consume in the wrapper, a value of null resets the theme to the default
     */
    override fun setTheme(theme: ButtonTheme) {
        theme.textColor?.let { setTextColor(it) }
        theme.textSize?.let { textSize = it }
        theme.letterSpacing?.let { letterSpacing = it }
        invalidate()
    }
}