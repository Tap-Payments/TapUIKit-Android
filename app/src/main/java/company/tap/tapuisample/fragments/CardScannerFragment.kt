package company.tap.tapuisample.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction
import cards.pay.paycardsrecognizer.sdk.Card
import cards.pay.paycardsrecognizer.sdk.ui.InlineViewCallback
import cards.pay.paycardsrecognizer.sdk.ui.InlineViewFragment
import company.tap.cardscanner.TapCard
import company.tap.cardscanner.TapTextRecognitionCallBack
import company.tap.cardscanner.TapTextRecognitionML
import company.tap.tapuilibrary.atoms.TapTextView
import company.tap.tapuilibrary.views.TapBottomSheetDialog
import company.tap.tapuisample.R

/**
 * Created by AhlaamK on 7/6/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
class CardScannerFragment : TapBottomSheetDialog(),TapTextRecognitionCallBack , InlineViewCallback{
    private var textRecognitionML: TapTextRecognitionML? = null
    private var cardScanText: TapTextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.custom_card_view, container, false)
        childFragmentManager
            .beginTransaction()
            .add(R.id.inline_container, InlineViewFragment())
            .commit()
        cardScanText = view.findViewById(R.id.cardscan_ready)
        cardScanText?.text = "Ready to scan."
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

    override fun onScanCardFinished(card: Card?, cardImage: ByteArray?) {

    }

    override fun onScanCardFailed(e: Exception?) {
    }
}