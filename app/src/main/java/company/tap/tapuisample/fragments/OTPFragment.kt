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
import company.tap.tapuilibrary.themekit.theme.ImageViewTheme
import company.tap.tapuilibrary.uikit.atoms.TapTextViewNew
import company.tap.tapuilibrary.uikit.datasource.HeaderDataSource
import company.tap.tapuilibrary.uikit.ktx.setTopBorders
import company.tap.tapuilibrary.uikit.views.TapBottomSheetDialog
import company.tap.tapuilibrary.uikit.views.TapBrandView
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
    private lateinit var otpSent: TapTextViewNew
    private lateinit var otpMobile: TapTextViewNew
    private lateinit var timerText: TapTextViewNew
    private lateinit var tapBrandView: TapBrandView
    private lateinit var tapHeaderSectionView: company.tap.tapuilibrary.uikit.views.TapHeaderSectionView



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
      //  tapBrandView= view.findViewById(R.id.tab_brand)
        val imagetheme = ImageViewTheme()
        imagetheme.imageResource = (R.drawable.merchant_logo)

        tapHeaderSectionView = view.findViewById(R.id.tapHeaderSectionView)
        tapHeaderSectionView.setHeaderDataSource(getHeaderDataSource())
        tapHeaderSectionView.businessIcon.setTheme(imagetheme)
        tapHeaderSectionView.businessIcon.scaleType = ImageView.ScaleType.CENTER_CROP
     //todo check aslm   tapHeaderSectionView.tapChipIcon.cardElevation =0f
       // ( tapBrandView.backgroundHeader.parent as View).applyBluryToView()
        val rootView = (activity?.window?.decorView as ViewGroup?)
        return view
    }



    private fun getHeaderDataSource(): HeaderDataSource {
        return HeaderDataSource(
            businessName = "welcome",
            businessFor = LocalizationManager.getValue("paymentFor", "TapMerchantSection")
        )
    }


    fun View.applyBluryToView(
        radiusNeeded: Int = 8,
        sampling: Int = 2,
        animationDuration: Int = 1000,
        showOriginalView: Boolean = false
    ) {
        Blurry.with(context).radius(radiusNeeded).sampling(sampling).animate(animationDuration)
            .onto(this as ViewGroup).apply {
                when (showOriginalView) {
                    true -> this@applyBluryToView.getChildAt(0).visibility = View.VISIBLE
                    false -> this@applyBluryToView.getChildAt(0).visibility = View.GONE
                }

            }


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