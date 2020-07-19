package company.tap.tapuisample.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import company.tap.cardscanner.TapCountDownTimer
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
    private lateinit var otpView:TapOTPView
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

        prepareTextViews(view)
        startCountdown(view)
        otpView = view.findViewById(R.id.otp_view)
        return view
    }

    private fun startCountdown(view: View) {
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

    private fun prepareTextViews(view: View) {
        otpSent = view.findViewById(R.id.otp_sent)
        otpSent.text = "OTP has been sent to"
        otpMobile = view.findViewById(R.id.mobile_textview)
        otpMobile.text= "+965 6••••111"
        timerText = view.findViewById(R.id.timer_textview)

    }

}