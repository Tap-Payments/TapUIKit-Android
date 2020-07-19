package company.tap.tapuisample.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import company.tap.tapuilibrary.atoms.TapTextView
import company.tap.tapuilibrary.views.TapOTPView
import company.tap.tapuisample.R
import java.util.*

/**
 * Created by AhlaamK on 7/12/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
class OTPFragment: DialogFragment() {
    var TAG = "OTPFullScreenDialog"
    private val TICK_LENGTH = 1000
    private val TIMER_STRING_FORMAT = "%02d:%02d"
    private val CONFIRMATION_CODE_LENGTH = 6
    private val timer: CountDownTimer? = null
    private var resendConfirmationCodeTimeout = 0

    private var timerTextView: TextView? = null
    private var phoneNumberTextView: TextView? = null
    private lateinit var otpView:TapOTPView

    private val otpCode = ""
    private val textViewsArray = ArrayList<TextView>()
    private lateinit var otpSent:TapTextView
    private lateinit var otpMobile:TapTextView
    private lateinit var timerText:TapTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            STYLE_NO_FRAME,
            R.style.Theme_MaterialComponents_Light_NoActionBar_Bridge
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View =
            inflater.inflate(R.layout.fragment_otpscreen, container, true)
        resendConfirmationCodeTimeout = 30
        prepareTextViews(view)
        //handleConfirmationCodeInputEditText(view)
       // startCountdown(view)
        otpView = view.findViewById(R.id.otp_view)
        return view
    }
    private fun prepareTextViews(view: View) {
      //  val inflater = LayoutInflater.from(view.context)
        otpSent = view.findViewById(R.id.otp_sent)
        otpSent.text = "OTP has been sent to"
        otpMobile = view.findViewById(R.id.mobile_textview)
        otpMobile.text= "+965 6••••111"
        timerText = view.findViewById(R.id.timer_textview)
        timerText.text="00:09"
      /*  val textViewsLayout = view.findViewById<LinearLayout>(R.id.textViewsLayout)
        val otpParentLayout =
            view.findViewById<RelativeLayout>(R.id.otpParentLayout)
        var index = 0
        while (index < CONFIRMATION_CODE_LENGTH) {
            val textViewParams = LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            textViewParams.weight = 1f
            textViewParams.setMargins(10, 10, 10, 10)
            val textView =
                inflater.inflate(R.layout.textview_cell, null) as TextView
            textViewsLayout.addView(textView, textViewParams)

            textViewsArray.add(textView)
            index++
        }*/
       // timerTextView = view.findViewById(R.id.timerTextView)



    }

}