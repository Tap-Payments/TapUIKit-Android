package company.tap.tapuilibrary.uikit.fragment


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cards.pay.paycardsrecognizer.sdk.Card
import cards.pay.paycardsrecognizer.sdk.FrameManager
import cards.pay.paycardsrecognizer.sdk.ui.InlineViewCallback
import cards.pay.paycardsrecognizer.sdk.ui.InlineViewFragment
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

/**
 * Created by AhlaamK on 7/6/20.
 *Copyright (c) 2020    Tap Payments.
 *All rights reserved.
 **/
class CardScannerFragment : Fragment(), TapTextRecognitionCallBack, InlineViewCallback {
    private var textRecognitionML: TapTextRecognitionML? = null
    private var cardScanText: TapTextView? = null

    // var blurLayout: BlurLayout? = null
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

        //  bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        cardScanText = view.findViewById(R.id.cardscan_ready)
        cardScanText?.text = LocalizationManager.getValue("Default", "Hints", "scan")
        //headerViewInit(view)
        // amountViewInit(view)
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
        FrameManager.getInstance().frameColor = Color.GREEN

        if (childFragmentManager.findFragmentById(R.id.inline_container) != null)
            childFragmentManager
                .beginTransaction()
                .replace(R.id.inline_container, this)
                .commit()

    }

    override fun onScanCardFailed(e: Exception?) {
        childFragmentManager.beginTransaction()
            .remove(this)
            .commit()
    }


}