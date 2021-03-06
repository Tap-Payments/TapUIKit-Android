package company.tap.tapuilibrary.organisms

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Patterns
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.atoms.TapTextView
import company.tap.tapuilibrary.datasource.ActionButtonDataSource
import company.tap.tapuilibrary.datasource.GoPayLoginDataSource
import company.tap.tapuilibrary.enums.GoPayLoginMethod.EMAIL
import company.tap.tapuilibrary.enums.GoPayLoginMethod.PHONE
import company.tap.tapuilibrary.interfaces.GoPayLoginInterface
import company.tap.tapuilibrary.utils.FakeThemeManager
import company.tap.tapuilibrary.views.TabAnimatedActionButton

/**
 *
 * Created by Mario Gamal on 7/14/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class GoPayLoginInput(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

    private val loginTabLayout: TabLayout
    private val textInput: TextInputEditText
    private val textInputLayout: TextInputLayout
    private val loginMethodImage: ImageView
    private val actionButton: TabAnimatedActionButton
    private var dataSource: GoPayLoginDataSource? = null
    private var loginInterface: GoPayLoginInterface? = null
    private var inputType = EMAIL

    init {
        inflate(context, R.layout.gopay_login_input, this)
        loginTabLayout = findViewById(R.id.login_type)
        textInput = findViewById(R.id.gopay_text_input)
        textInputLayout = findViewById(R.id.text_input_layout)
        loginMethodImage = findViewById(R.id.login_method_icon)
        actionButton = findViewById(R.id.gopay_button)
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
            getButtonDataSource(
                Color.parseColor("#d7d7d7")
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
        textInput.doAfterTextChanged {
            if (isValidInput(it.toString()))
                enableNext()
            else
                disableNext()
        }
    }

    private fun isValidInput(text: String): Boolean {
        return when (inputType) {
            EMAIL -> isValidEmail(text)
            PHONE -> isValidPhone(text)
        }
    }

    private fun disableNext() {
        loginTabLayout.setSelectedTabIndicatorColor(FakeThemeManager.getGoPayUnValidatedColor())
        actionButton.setButtonDataSource(getButtonDataSource(FakeThemeManager.getGoPayUnValidatedColor()))
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
        actionButton.setButtonDataSource(getButtonDataSource(FakeThemeManager.getGoPayValidatedColor()))
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
                loginMethodImage.setImageResource(R.drawable.gopay_mail)
            }
            PHONE -> {
                textInput.hint = dataSource?.phoneInputHint ?: "00000000"
                loginMethodImage.setImageResource(R.drawable.gopay_mobile)
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

}