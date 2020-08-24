package company.tap.tapuilibrary.organisms

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.atoms.TapTextView
import company.tap.tapuilibrary.datasource.ActionButtonDataSource
import company.tap.tapuilibrary.interfaces.GoPayLoginInterface
import company.tap.tapuilibrary.utils.FakeThemeManager
import company.tap.tapuilibrary.views.TabAnimatedActionButton

/**
 *
 * Created by Mario Gamal on 7/16/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */
class GoPayPasswordInput(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {

     var textInputLayout: TextInputLayout
     var passwordTextInput: TextInputEditText
     var signInButton: TabAnimatedActionButton
     var changeEmail: TapTextView
    private var loginInterface: GoPayLoginInterface? = null

    init {
        inflate(context, R.layout.gopay_password_input, this)
        textInputLayout = findViewById(R.id.text_input_layout)
        passwordTextInput = findViewById(R.id.gopay_password_input)
        signInButton = findViewById(R.id.sigin_button)
        changeEmail = findViewById(R.id.change_email)
        initButton()
        initChange()
        initPasswordInput()
    }

    fun setLoginInterface(loginInterface: GoPayLoginInterface) {
        this.loginInterface = loginInterface
    }

    private fun initButton() {
        signInButton.isEnabled = false
        signInButton.setButtonDataSource(
            getButtonDataSource(
                FakeThemeManager.getGoPaySignInButtonColor()
            )
        )
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
        signInButton.setButtonDataSource(
            getButtonDataSource(
                if (isEnabled)
                    FakeThemeManager.getGoPayValidatedColor()
                else
                    FakeThemeManager.getGoPaySignInButtonColor()
            )
        )
    }

    private fun getButtonDataSource(color: Int): ActionButtonDataSource {
        return ActionButtonDataSource(
            text = "Signin",
            textSize = 16f,
            textColor = Color.WHITE,
            cornerRadius = 100f,
            backgroundColor = color
        )
    }
}