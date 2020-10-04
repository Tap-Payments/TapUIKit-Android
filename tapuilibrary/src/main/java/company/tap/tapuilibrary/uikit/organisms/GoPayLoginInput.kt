package company.tap.tapuilibrary.uikit.organisms

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.util.AttributeSet
import android.util.Patterns
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.fontskit.enums.TapFont
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.themekit.theme.EditTextTheme
import company.tap.tapuilibrary.uikit.atoms.TapTextView
import company.tap.tapuilibrary.uikit.datasource.ActionButtonDataSource
import company.tap.tapuilibrary.uikit.datasource.GoPayLoginDataSource
import company.tap.tapuilibrary.uikit.enums.GoPayLoginMethod.EMAIL
import company.tap.tapuilibrary.uikit.enums.GoPayLoginMethod.PHONE
import company.tap.tapuilibrary.uikit.interfaces.GoPayLoginInterface
import company.tap.tapuilibrary.uikit.interfaces.TapView
import company.tap.tapuilibrary.uikit.utils.FakeThemeManager
import company.tap.tapuilibrary.uikit.views.TabAnimatedActionButton

/**
 *
 * Created by Mario Gamal on 7/14/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class GoPayLoginInput(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs),
    TapView<EditTextTheme> {

    var loginTabLayout: TabLayout
    val textInput by lazy { findViewById<TextInputEditText>(R.id.gopay_text_input) }
    var textInputLayout: TextInputLayout
    var loginMethodImage: ImageView
    var actionButton: TabAnimatedActionButton
    var dataSource: GoPayLoginDataSource? = null
    private var loginInterface: GoPayLoginInterface? = null
    private var inputType = EMAIL

    init {
        inflate(context, R.layout.gopay_login_input, this)
        loginTabLayout = findViewById(R.id.login_type)
        textInputLayout = findViewById(R.id.text_input_layout)
        loginMethodImage = findViewById(R.id.login_method_icon)
        actionButton = findViewById(R.id.gopay_button)
        if (context?.let { LocalizationManager.getLocale(it).language } == "en") setFontsEnglish() else setFontsArabic()
    }

    fun changeDataSource(dataSource: GoPayLoginDataSource) {
        this.dataSource = dataSource
        initTabLayout()
        initTextInput()
        initButton()
    }

    fun setLoginInterface(loginInterface: GoPayLoginInterface) {
        this.loginInterface = loginInterface
    }

    private fun initButton() {
        actionButton.isEnabled = false
        actionButton.setButtonDataSource(
            false,
            context?.let { LocalizationManager.getLocale(it).language },
            LocalizationManager.getValue(
                "pay",
                "ActionButton"
            )
        )
        actionButton.setOnClickListener {
            when (inputType) {
                EMAIL -> loginInterface?.onEmailValidated()
                PHONE -> loginInterface?.onPhoneValidated()
            }
        }
    }

    private fun getButtonDataSource(color: Int): ActionButtonDataSource {
        return ActionButtonDataSource(
            text = "Next",
            textSize = 16f,
            textColor = Color.WHITE,
            cornerRadius = 100f,
            backgroundColor = color
        )
    }

    private fun initTextInput() {


        when (inputType) {
            EMAIL -> {
                textInput.doAfterTextChanged {
                    if (isValidInput(it.toString()))
                        enableNext()
                    else
                        disableNext()
                }
            }
            PHONE -> {
                textInput.addTextChangedListener(object : PhoneNumberFormattingTextWatcher() {
                    //we need to know if the user is erasing or inputing some new character
                    private var backspacingFlag: Boolean = false

                    //we need to block the :afterTextChanges method to be called again after we just replaced the EditText text
                    private var editedFlag: Boolean = false

                    //we need to mark the cursor position and restore it after the edition
                    private var cursorComplement: Int = 0

                    override fun beforeTextChanged(
                        s: CharSequence,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                        //we store the cursor local relative to the end of the string in the EditText before the edition
                        cursorComplement = s.length - textInput.getSelectionStart()
                        //we check if the user ir inputing or erasing a character
                        backspacingFlag = if (count > after) {
                            true
                        } else {
                            false
                        }
                    }


                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        // nothing to do here =D
                    }

                    override fun afterTextChanged(s: Editable?) {
                        val string = s.toString()
                        //what matters are the phone digits beneath the mask, so we always work with a raw string with only digits
                        //what matters are the phone digits beneath the mask, so we always work with a raw string with only digits
                        val phone = string.replace("[^\\d]".toRegex(), "")
//if the text was just edited, :afterTextChanged is called another time... so we need to verify the flag of edition
                        //if the flag is false, this is a original user-typed entry. so we go on and do some magic
                        //if the text was just edited, :afterTextChanged is called another time... so we need to verify the flag of edition
                        //if the flag is false, this is a original user-typed entry. so we go on and do some magic
                        if (!editedFlag) {

                            //we start verifying the worst case, many characters mask need to be added
                            //example: 999999999 <- 6+ digits already typed
                            // masked: (999) 999-999
                            if (phone.length >= 6 && !backspacingFlag) {
                                //we will edit. next call on this textWatcher will be ignored
                                editedFlag = true
                                //here is the core. we substring the raw digits and add the mask as convenient
                                val ans = "(" + phone.substring(0, 3) + ") " + phone.substring(
                                    3,
                                    6
                                ) + "-" + phone.substring(6)
                                textInput.setText(ans)
                                //we deliver the cursor to its original position relative to the end of the string
                                textInput.getText()?.length?.minus(
                                    cursorComplement
                                )?.let { textInput.setSelection(it) }

                                //we end at the most simple case, when just one character mask is needed
                                //example: 99999 <- 3+ digits already typed
                                // masked: (999) 99
                            } else if (phone.length >= 3 && !backspacingFlag) {
                                editedFlag = true
                                val ans = "(" + phone.substring(0, 3) + ") " + phone.substring(3)
                                textInput.setText(ans)
                                textInput.getText()?.length?.minus(
                                    cursorComplement
                                )?.let { textInput.setSelection(it) }
                            }
                            // We just edited the field, ignoring this cicle of the watcher and getting ready for the next
                        } else {
                            editedFlag = false
                        }
                    }
                })
            }
        }
    }

    private fun isValidInput(text: String): Boolean {
        return when (inputType) {
            EMAIL -> isValidEmail(text)
            PHONE -> isValidPhone(text)
        }
    }

    private fun disableNext() {
        actionButton.setButtonDataSource(
            false,
            context?.let { LocalizationManager.getLocale(it).language },
            LocalizationManager.getValue(
                "next",
                "Common"
            )
        )
        loginTabLayout.setSelectedTabIndicatorColor(FakeThemeManager.getGoPayUnValidatedColor())
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS
            .matcher(email)
            .matches()
    }

    private fun isValidPhone(phone: String): Boolean {
        return phone.length > 7
    }

    private fun enableNext() {
        loginTabLayout.setSelectedTabIndicatorColor(FakeThemeManager.getGoPayValidatedColor())
        actionButton.setButtonDataSource(
            true,
            context?.let { LocalizationManager.getLocale(it).language },
            LocalizationManager.getValue(
                "next",
                "Common"
            )
        )
        actionButton.isEnabled = true
    }

    private fun initTabLayout() {
        loginTabLayout.removeAllTabs()
        loginTabLayout.addTab(
            loginTabLayout.newTab().setCustomView(
                getThemedTabText(dataSource?.emailTabText ?: "EMAIL", true)
            )
        )

        loginTabLayout.addTab(
            loginTabLayout.newTab().setCustomView(
                getThemedTabText(dataSource?.phoneTabText ?: "PHONE", false)
            )
        )

        loginTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabText = tab?.customView as TapTextView
                tabText.setTextColor(FakeThemeManager.getGoPayUnSelectedTabColor())
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabText = tab?.customView as TapTextView
                tabText.setTextColor(FakeThemeManager.getGoPaySelectedTabColor())
                inputType = if (tab.position == 0) EMAIL else PHONE
                changeInputType()
            }
        })
    }

    private fun changeInputType() {
        textInput.text = null
        when (inputType) {
            EMAIL -> {
                textInput.hint = dataSource?.emailInputHint ?: "mail@mail.com"
                textInput.setTextColor(Color.parseColor(ThemeManager.getValue("emailCard.textFields.textColor")))
                loginMethodImage.setImageResource(R.drawable.ic_mail)
            }
            PHONE -> {
                textInput.hint = dataSource?.phoneInputHint ?: "00000000"
                textInput.setTextColor(Color.parseColor(ThemeManager.getValue("phoneCard.textFields.textColor")))
                loginMethodImage.setImageResource(R.drawable.ic_mobile)
            }
        }
    }

    private fun getThemedTabText(text: String, isSelected: Boolean): TapTextView {
        val tabText = TapTextView(context, null)
        tabText.setTheme(FakeThemeManager.getGoPayTabLayoutTextTheme(isSelected))
        tabText.text = text
        tabText.typeface = Typeface.create("sans-serif-light", Typeface.NORMAL);
        return tabText
    }

    override fun setTheme(theme: EditTextTheme) {
        theme.maxLines?.let { it }
        theme.textColor?.let { it }
        theme.textSize?.let { it }
        theme.letterSpacing?.let { it }
        theme.textColorHint?.let { }
        theme.backgroundTint?.let { backgroundTintList = ColorStateList.valueOf(it) }
    }

    fun setFontsEnglish() {
        textInput?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoLight
            )
        )
    }

    fun setFontsArabic() {
        textInput?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.TajawalLight
            )
        )

    }

}