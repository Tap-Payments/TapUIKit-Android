package company.tap.tapuisample.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import company.tap.cardscanner.TapCard
import company.tap.cardscanner.TapTextRecognitionCallBack
import company.tap.cardscanner.TapTextRecognitionML
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.uikit.atoms.TapButton
import company.tap.tapuilibrary.uikit.atoms.TapImageView
import company.tap.tapuilibrary.uikit.atoms.TapTextViewNew

import company.tap.tapuilibrary.uikit.views.TapAmountSectionView
import company.tap.tapuilibrary.uikit.views.TapBottomSheetDialog
import company.tap.tapuilibrary.uikit.views.TapHeaderSectionView
import company.tap.tapuisample.R

import jp.wasabeef.blurry.Blurry


/**
 * Created by AhlaamK on 7/6/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
class CardScannerSampleFragment : TapBottomSheetDialog(),TapTextRecognitionCallBack {
    private var textRecognitionML: TapTextRecognitionML? = null
    private var cardScanText: TapTextViewNew? = null
    private lateinit var tapHeaderSectionView: TapHeaderSectionView
    private var imageUrl: String = "https://avatars3.githubusercontent.com/u/19837565?s=200&v=4"
    private var businessName: String? = null
    private var businessInitial: String? = null
    private lateinit var businessIcon: TapImageView
    private lateinit var businessPlaceholder: TapTextViewNew
    private lateinit var amountSectionView: TapAmountSectionView
    private lateinit var selectedCurrency: TapTextViewNew
    private lateinit var currentCurrency: TapTextViewNew
    private lateinit var itemCount: TapButton
    // var blurLayout: BlurLayout? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.custom_card_view_example, container, false)
        val rootView = (activity?.window?.decorView as ViewGroup?)

        //  Blurry.with(context).radius(25).sampling(2).onto(rootView)

       /* childFragmentManager
            .beginTransaction()
            .add(R.id.inline_container, InlineViewFragment())
            .commit()*/
        //  bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        cardScanText = view.findViewById(R.id.cardscan_ready)
        cardScanText?.text = LocalizationManager.getValue("Default","Hints","scan")


        return view

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        textRecognitionML = TapTextRecognitionML(this)
    }

    override fun onRecognitionSuccess(card: TapCard?) {

    }

    override fun onRecognitionFailure(error: String?) {

    }
/*
    override fun onScanCardFinished(card: Card?, cardImage: ByteArray?) {
        FrameManager.getInstance().frameColor = Color.GREEN

        if (childFragmentManager.findFragmentById(R.id.inline_container) != null)
            childFragmentManager
                .beginTransaction()
                .replace(R.id.inline_container,this)
                .commit()

    }

    override fun onScanCardFailed(e: Exception?) {
        childFragmentManager.beginTransaction()
            .remove(this)
            .commit()
    }*/


}