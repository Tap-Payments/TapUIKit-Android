package company.tap.tapuilibrary.uikit.organisms

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.view.get
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.fontskit.enums.TapFont
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.themekit.theme.EditTextTheme
import company.tap.tapuilibrary.uikit.atoms.TapTextView
import company.tap.tapuilibrary.uikit.atoms.TextInputEditText
import company.tap.tapuilibrary.uikit.datasource.ActionButtonDataSource
import company.tap.tapuilibrary.uikit.interfaces.GoPayLoginInterface
import company.tap.tapuilibrary.uikit.interfaces.TapView
import company.tap.tapuilibrary.uikit.utils.FakeThemeManager
import company.tap.tapuilibrary.uikit.views.TabAnimatedActionButton

/**
 *
 * Created by Mario Gamal on 7/16/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class GoPayPasswordInput(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs),
    TapView<EditTextTheme> {

    val passwordTextInput by lazy { findViewById<TextInputEditText>(R.id.gopay_password_input) }
    val changeEmail by lazy { findViewById<TapTextView>(R.id.change_email) }
    val textInputLayout by lazy { findViewById<TextInputLayout>(R.id.text_input_layout) }
    val gopayPasswordInput by lazy { findViewById<TextInputEditText>(R.id.gopay_password_input) }
    val signInButton by lazy { findViewById<TabAnimatedActionButton>(R.id.sigin_button) }
    val passwordemailText by lazy { findViewById<TapTextView>(R.id.gopay_password_text) }

    private var loginInterface: GoPayLoginInterface? = null

    init {
        inflate(context, R.layout.gopay_password_input, this)
        initButton()
        initChange()
        initPasswordInput()
        setPasswordValidation()
        if (context?.let { LocalizationManager.getLocale(it).language } == "en") setFontsEnglish() else setFontsArabic()
    }
    fun setPasswordValidation(){
        if (gopayPasswordInput.text?.length!! > 7){
            changeButtonStatus(true)
            signInButton.initActionButtonDataSource(true, context?.let { LocalizationManager.getLocale(it).language },
                Color.parseColor(ThemeManager.getValue("actionButton.Valid.goLoginBackgroundColor")),
                Color.parseColor(ThemeManager.getValue("actionButton.Valid.titleLabelColor")),
                LocalizationManager.getValue("signin","ActionButton") )
        }else{
            changeButtonStatus(false)
            signInButton.initActionButtonDataSource(false, context?.let { LocalizationManager.getLocale(it).language },
                Color.parseColor(ThemeManager.getValue("actionButton.Valid.goLoginBackgroundColor")),
                Color.parseColor(ThemeManager.getValue("actionButton.Valid.titleLabelColor")),
                LocalizationManager.getValue("signin","ActionButton") )
        }
        signInButton.isEnabled = gopayPasswordInput.text?.length!! > 7
    }



    fun setLoginInterface(loginInterface: GoPayLoginInterface, emailText:String) {
        this.loginInterface = loginInterface
        passwordemailText.text = emailText
    }

    private fun initButton() {
        signInButton.isEnabled = false
        signInButton.initActionButtonDataSource(false, context?.let { LocalizationManager.getLocale(it).language },
            Color.parseColor(ThemeManager.getValue("actionButton.Valid.goLoginBackgroundColor")),
            Color.parseColor(ThemeManager.getValue("actionButton.Valid.titleLabelColor")), LocalizationManager.getValue("signin","ActionButton") )
        signInButton.setOnClickListener {
            textInputLayout.error = "Incorrect Password"
        }
    }

    private fun initChange() {
        changeEmail.setOnClickListener {
            loginInterface?.onChangeClicked()
        }
    }

    private fun initPasswordInput() {
        passwordTextInput.doAfterTextChanged {
            changeButtonStatus(it.toString().length > 7)
        }
    }

    private fun changeButtonStatus(isEnabled: Boolean) {
        signInButton.isEnabled = isEnabled

        if (isEnabled) {
            signInButton.initActionButtonDataSource(true, context?.let { LocalizationManager.getLocale(it).language },
                Color.parseColor(ThemeManager.getValue("actionButton.Valid.goLoginBackgroundColor")),
                Color.parseColor(ThemeManager.getValue("actionButton.Valid.titleLabelColor")), "next" )
        } else {
            signInButton.initActionButtonDataSource(false, context?.let { LocalizationManager.getLocale(it).language },
                Color.parseColor(ThemeManager.getValue("actionButton.Invalid.backgroundColor")),
                Color.parseColor(ThemeManager.getValue("actionButton.Invalid.titleLabelColor")), "next" )
        }

    }


    /**
     * Set the configure the current theme. If null is provided then the default Theme is returned
     * on the next call
     * @param theme Theme to consume in the wrapper, a value of null resets the theme to the default
     **/
    override fun setTheme(theme: EditTextTheme) {
        theme.maxLines?.let { it }
        theme.textColor?.let { it }
        theme.textSize?.let { it }
        theme.letterSpacing?.let { it }
        theme.textColorHint?.let { }
        theme.backgroundTint?.let { backgroundTintList = ColorStateList.valueOf(it) }
    }


    fun setFontsEnglish() {
        passwordTextInput?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoLight
            )
        )
        changeEmail?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoLight
            )
        )

    }

    fun setFontsArabic() {
        passwordTextInput?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.TajawalLight
            )
        )

        changeEmail?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.TajawalLight
            )
        )
    }
}