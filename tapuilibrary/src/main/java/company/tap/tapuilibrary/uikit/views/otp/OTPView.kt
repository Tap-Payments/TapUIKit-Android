package company.tap.tapuilibrary.uikit.views.otp

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.uikit.atoms.TapSwitch
import company.tap.tapuilibrary.uikit.atoms.TapTextView
import company.tap.tapuilibrary.uikit.interfaces.OpenOTPInterface
import company.tap.tapuilibrary.uikit.organisms.GoPayLoginInput
import company.tap.tapuilibrary.uikit.views.TapOTPView

class OTPView : LinearLayout, OpenOTPInterface {

    lateinit var attrs: AttributeSet

    val otpMainView by lazy { findViewById<LinearLayout>(R.id.otpMainView) }
    val otpViewInput by lazy { findViewById<TapOTPView>(R.id.otpViewInput) }
    val otpSentText by lazy { findViewById<TapTextView>(R.id.otpSentText) }
    val mobileNumberText by lazy { findViewById<TapTextView>(R.id.mobileNumberText) }
    val timerText by lazy { findViewById<TapTextView>(R.id.timerText) }
    private var goPayLoginInput: GoPayLoginInput? = null




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
        goPayLoginInput = GoPayLoginInput(context, attrs)
        goPayLoginInput?.setOpenOTPInterface(this)
//        if (context?.let { LocalizationManager.getLocale(it).language } == "en") setFontsEnglish() else setFontsArabic()

    }



    private fun startCountdown() {
        object : CountDownTimer(60 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val second = millisUntilFinished / 1000 % 60
                val minutes = millisUntilFinished / (1000 * 60) % 60
                timerText.text=("$minutes:$second")
            }
            override fun onFinish() {
                timerText.text=("00:00")
            }
        }.start()

    }

    private fun prepareTextViews() {
        otpSentText.text = LocalizationManager.getValue("Message","TapOtpView","Ready")
    }

    override fun getPhoneNumber(phoneNumber: String) {
        mobileNumberText.text= phoneNumber
    }


}
