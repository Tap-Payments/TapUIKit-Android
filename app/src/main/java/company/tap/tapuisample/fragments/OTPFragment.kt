package company.tap.tapuisample.fragments

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.uikit.atoms.TapTextView
import company.tap.tapuilibrary.uikit.views.TapBottomSheetDialog
import company.tap.tapuilibrary.uikit.views.TapOTPView
import company.tap.tapuisample.R
import jp.wasabeef.blurry.Blurry
import jp.wasabeef.glide.transformations.BlurTransformation


/**
 * Created on 7/12/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
class OTPFragment : TapBottomSheetDialog() {
    private lateinit var otpView: TapOTPView
    private lateinit var otpSent: TapTextView
    private lateinit var otpMobile: TapTextView
    private lateinit var timerText: TapTextView
    private lateinit var blurryView: ImageView


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setStyle(
//            STYLE_NO_FRAME,
//            R.style.Theme_MaterialComponents_Light_NoActionBar_Bridge
//        )
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.fragment_otpscreen, container, true)
        val insertPoint: ViewGroup = activity?.findViewById(android.R.id.content) as ViewGroup
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetDialog.behavior.skipCollapsed = true
        prepareTextViews(view)
        startCountdown(view)
        otpView = view.findViewById(R.id.otp_view_input)
        backgroundColor = Color.WHITE
        blurryView = view.findViewById(R.id.view_blurry)

        val rootView = (activity?.window?.decorView as ViewGroup?)
        blurryView.post {
            val bitmap = Blurry.with(context)
                .radius(25)
                .sampling(1)
                .capture(blurryView).get()
            (blurryView).setImageDrawable(BitmapDrawable(resources, bitmap))
        }

//
//        Glide.with(requireContext())
//            .asBitmap()
//            .load(R.drawable.border_black) // or url
//            .transform(BlurTransformation(25, 100))
//            .into(blurryView)
        // Configure background
//            Blurry.with(context)
//                .radius(15)
//                .sampling(2)
//                .async()
//                .color(Color.argb(155, 0, 0, 0))
//                .onto(blurryView.parent as ViewGroup)

        // insertPoint.removeView(otp_linearlayout)
        /* insertPoint.addView(
             otp_linearlayout,
             0,
             ViewGroup.LayoutParams(
                 ViewGroup.LayoutParams.MATCH_PARENT,
                 ViewGroup.LayoutParams.MATCH_PARENT
             )
         )
         otp_linearlayout.bringToFront()*/



        return view
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        return BottomSheetDialog(requireContext(), R.style.MyTransparentBottomSheetDialogTheme)

    }


    private fun startCountdown(view: View) {
        object : CountDownTimer(60 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val second = millisUntilFinished / 1000 % 60
                val minutes = millisUntilFinished / (1000 * 60) % 60
                timerText.text = ("$minutes:$second")
            }

            override fun onFinish() {
                timerText.text = ("00:00")
            }
        }.start()

    }

    private fun prepareTextViews(view: View) {
        otpSent = view.findViewById(R.id.otp_sent)
        otpSent.text = LocalizationManager.getValue("Message", "TapOtpView", "Ready")
        otpMobile = view.findViewById(R.id.mobile_textview)
        otpMobile.text = "+965 6••••111"
        timerText = view.findViewById(R.id.timer_textview)
    }


}