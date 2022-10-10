package company.tap.tapuilibrary.uikit.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import company.tap.cardscanner.TapCard
import company.tap.cardscanner.TapTextRecognitionCallBack
import company.tap.cardscanner.TapTextRecognitionML
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.uikit.atoms.TapButton
import company.tap.tapuilibrary.uikit.atoms.TapImageView
import company.tap.tapuilibrary.uikit.atoms.TapTextView
import company.tap.tapuilibrary.uikit.views.TapAmountSectionView
import company.tap.tapuilibrary.uikit.views.TapHeaderSectionView
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.custom_card_view.*

/**
 * Created by AhlaamK on 7/6/20.
Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
class CardScannerFragment : Fragment(),TapTextRecognitionCallBack {
    private var textRecognitionML: TapTextRecognitionML? = null
    private var cardScanText: TapTextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.custom_card_view, container, false)

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






}