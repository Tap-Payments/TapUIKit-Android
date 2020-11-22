package company.tap.tapuilibrary.uikit.organisms

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.os.CountDownTimer
import android.provider.Settings.Global.getString
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import com.google.android.gms.common.SignInButton
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.fontskit.enums.TapFont
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.themekit.theme.TextViewTheme
import company.tap.tapuilibrary.uikit.atoms.TapTextView
import company.tap.tapuilibrary.uikit.interfaces.OpenOTPInterface
import company.tap.tapuilibrary.uikit.interfaces.OtpButtonConfirmationInterface
import company.tap.tapuilibrary.uikit.utils.BlurBuilder
import company.tap.tapuilibrary.uikit.views.TabAnimatedActionButton
import company.tap.tapuilibrary.uikit.views.TapOTPView
import jp.wasabeef.blurry.Blurry


/**
 *
 * Created by OlaMonir on 21/10/20
 * Copyright © 2020 Tap Payments. All rights reserved.
 *
 */

class OTPView : LinearLayout, OpenOTPInterface {

    lateinit var attrs: AttributeSet

    val otpMainView by lazy { findViewById<LinearLayout>(R.id.otpMainView) }
    val otpLinearLayout by lazy { findViewById<LinearLayout>(R.id.otpLinearLayout) }
    val otpViewInput by lazy { findViewById<TapOTPView>(R.id.otpViewInput) }
    val otpSentText by lazy { findViewById<TapTextView>(R.id.otpSentText) }
    val mobileNumberText by lazy { findViewById<TapTextView>(R.id.mobileNumberText) }
    val otpHintText by lazy { findViewById<TapTextView>(R.id.otpHintText) }
    val timerText by lazy { findViewById<TapTextView>(R.id.timerText) }
    val changePhone by lazy { findViewById<TapTextView>(R.id.changePhone) }
    val otpViewActionButton by lazy { findViewById<TabAnimatedActionButton>(R.id.otpViewActionButton) }
    val changePhoneCardView by lazy { findViewById<CardView>(R.id.changePhoneCardView) }

    //    private var goPayLoginInput: GoPayLoginInput? = null
    private var openOTPInterface: OpenOTPInterface? = null
    private var otpButtonConfirmationInterface: OtpButtonConfirmationInterface? = null
    var isValidOTP: Boolean = false


    fun setOTPInterface(openOTPInterface: OpenOTPInterface) {
        this.openOTPInterface = openOTPInterface
    }

    fun setOtpButtonConfirmationInterface(otpButtonConfirmationInterface: OtpButtonConfirmationInterface) {
        this.otpButtonConfirmationInterface = otpButtonConfirmationInterface
    }

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
        inflate(context, R.layout.otp_view, this)
        startCountdown()
        prepareTextViews()
        initOTPConfirmationButton()
        initChange()
        initTheme()
        setFonts()
    }


    fun initTheme() {
        otpLinearLayout.setBackgroundColor(Color.parseColor(ThemeManager.getValue("TapOtpView.backgroundColor")))
        changePhoneCardView.setCardBackgroundColor(Color.parseColor(ThemeManager.getValue("GlobalValues.Colors.whiteTwo")))

        val timerTextTheme = TextViewTheme()
        timerTextTheme.textColor =
            (Color.parseColor(ThemeManager.getValue("TapOtpView.Timer.textColor")))
        timerTextTheme.textSize = ThemeManager.getFontSize("TapOtpView.Timer.textFont")
        timerText.setTheme(timerTextTheme)

        val mobileNumberTextTextTheme = TextViewTheme()
        mobileNumberTextTextTheme.textColor =
            (Color.parseColor(ThemeManager.getValue("TapOtpView.OtpController.textColor")))
        mobileNumberTextTextTheme.textSize =
            ThemeManager.getFontSize("TapOtpView.OtpController.textFont")
        mobileNumberText.setTheme(mobileNumberTextTextTheme)
        otpSentText.setTheme(mobileNumberTextTextTheme)
        otpViewInput.setTextColor(Color.parseColor(ThemeManager.getValue("TapOtpView.OtpController.textColor")))




        val originalBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.color.black_27 )
        val blurredBitmap: Bitmap? = BlurBuilder.blur(context, originalBitmap)
        otpLinearLayout.setBackground(BitmapDrawable(resources, blurredBitmap))


    }

    fun setFonts() {

        otpViewInput.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoLight
            )
        )

        mobileNumberText.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoLight
            )
        )
        timerText.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoLight
            )
        )

        if (LocalizationManager.getLocale(context).language == "en") {
            otpSentText.typeface = Typeface.createFromAsset(
                context?.assets, TapFont.tapFontType(
                    TapFont.RobotoLight
                )
            )
            changePhone.typeface = Typeface.createFromAsset(
                context?.assets, TapFont.tapFontType(
                    TapFont.RobotoRegular
                )
            )
        } else {
            otpSentText.typeface = Typeface.createFromAsset(
                context?.assets, TapFont.tapFontType(
                    TapFont.TajawalLight
                )
            )
            changePhone.typeface = Typeface.createFromAsset(
                context?.assets, TapFont.tapFontType(
                    TapFont.TajawalRegular
                )
            )
        }
    }

    fun setHintExpiredTheme() {
        val otpHintTextTheme = TextViewTheme()

        otpHintTextTheme.textColor =
            (Color.parseColor(ThemeManager.getValue("TapOtpView.Expired.Message.title")))
        otpHintTextTheme.textSize =
            ThemeManager.getFontSize("TapOtpView.Expired.Message.textFont")

        otpHintText.setTheme(otpHintTextTheme)
    }

    fun setHintInValidOtp() {
        val otpHintTextTheme = TextViewTheme()
        otpHintTextTheme.textColor =
            (Color.parseColor(ThemeManager.getValue("TapOtpView.Invalid.Message.title")))
        otpHintTextTheme.textSize =
            ThemeManager.getFontSize("TapOtpView.Invalid.Message.textFont")
        otpHintText.setTheme(otpHintTextTheme)
    }

    private fun initChange() {
        changePhone.setOnClickListener {
            openOTPInterface?.onChangePhoneClicked()
        }
    }


    private fun startCountdown() {
        object : CountDownTimer(60 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val second = millisUntilFinished / 1000 % 60
                val minutes = millisUntilFinished / (1000 * 60) % 60
                timerText.text = ("$minutes:$second")
                timerText.text = (String.format("%02d", minutes) ) +":"+ (String.format("%02d", second))
            }

            override fun onFinish() {
                timerText.text = ("RESEND")
//                otpHintText.visibility - View.VISIBLE
//                otpHintText.text = "OTP Timer Expired! "
                setHintExpiredTheme()
            }
        }.start()

    }

    private fun prepareTextViews() {
        otpSentText.text = LocalizationManager.getValue("Message", "TapOtpView", "Ready")
    }

    @SuppressLint("SetTextI18n")
    override fun getPhoneNumber(phoneNumber: String, countryCode: String, maskedValue: String) {
        mobileNumberText.text = "${countryCode} $maskedValue"
    }

    override fun onChangePhoneClicked() {
        otpMainView.visibility = View.GONE
    }

    private fun initOTPConfirmationButton() {

        otpViewActionButton.setButtonDataSource(
            false, context?.let { LocalizationManager.getLocale(it).language },
            "Confirm",
            Color.parseColor(ThemeManager.getValue("actionButton.Invalid.goLoginBackgroundColor")),
            Color.parseColor(ThemeManager.getValue("actionButton.Invalid.titleLabelColor"))
        )
        otpViewInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

                if (charSequence.length != otpViewInput.itemCount) {
                    otpViewActionButton.isEnabled = false
                    otpViewActionButton.setButtonDataSource(
                        false, context?.let { LocalizationManager.getLocale(it).language },
                        "Confirm",
                        Color.parseColor(ThemeManager.getValue("actionButton.Invalid.goLoginBackgroundColor")),
                        Color.parseColor(ThemeManager.getValue("actionButton.Invalid.titleLabelColor"))
                    )
                } else {
                    otpViewActionButton.isEnabled = true
                    otpViewActionButton.setButtonDataSource(
                        true, context?.let { LocalizationManager.getLocale(it).language },
                        "Confirm",
                        Color.parseColor(ThemeManager.getValue("actionButton.Valid.goLoginBackgroundColor")),
                        Color.parseColor(ThemeManager.getValue("actionButton.Valid.titleLabelColor"))
                    )
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })



        otpViewActionButton.setOnClickListener {
            if (otpViewActionButton.isEnabled) {
                isValidOTP = otpButtonConfirmationInterface?.onOtpButtonConfirmationClick(otpNumber = otpViewInput.text.toString() ) == true

                if (!isValidOTP) {
                    otpHintText.visibility = View.VISIBLE
                    otpHintText.text = "Invalid OTP number! "
                    setHintInValidOtp()
                }
                Log.d("isValidOTP", isValidOTP.toString())
            }
        }
    }

}
