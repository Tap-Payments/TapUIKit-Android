package company.tap.tapuisample.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cards.pay.paycardsrecognizer.sdk.Card
import cards.pay.paycardsrecognizer.sdk.ui.InlineViewCallback
import cards.pay.paycardsrecognizer.sdk.ui.InlineViewFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
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
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
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
       // Toast.makeText(context,card.toString(), Toast.LENGTH_SHORT).show()

        /*fragmentManager?.findFragmentById(R.id.inline_container)?.let {
            fragmentManager?.beginTransaction()
                ?.remove(it)
                ?.commit()}*/
        if (childFragmentManager.findFragmentById(R.id.inline_container) != null)
            childFragmentManager
                .beginTransaction()
                .replace(R.id.inline_container,this)
                .commit()
           /* getSupportFragmentManager().beginTransaction()
            .remove(getSupportFragmentManager().findFragmentById(R.id.inline_container))
            .commit()
        childFragmentManager
            .beginTransaction()
            .remove(this)
            .commit()*/
       /* val fragmentTransaction: FragmentTransaction? = fragmentManager?.beginTransaction()
        fragmentManager?.findFragmentById(R.id.inline_container)?.let {
            fragmentTransaction
                ?.  remove(it)*/
        }
       // fragmentTransaction?.remove(this)?.commit()

   // }

    override fun onScanCardFailed(e: Exception?) {
        childFragmentManager.beginTransaction()
            .remove(this)
            .commit()
    }
}