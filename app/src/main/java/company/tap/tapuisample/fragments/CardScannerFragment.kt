package company.tap.tapuisample.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import cards.pay.paycardsrecognizer.sdk.Card
import cards.pay.paycardsrecognizer.sdk.FrameManager
import cards.pay.paycardsrecognizer.sdk.ui.InlineViewCallback
import cards.pay.paycardsrecognizer.sdk.ui.InlineViewFragment
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import company.tap.cardscanner.TapCard
import company.tap.cardscanner.TapTextRecognitionCallBack
import company.tap.cardscanner.TapTextRecognitionML
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.atoms.TapButton
import company.tap.tapuilibrary.atoms.TapImageView
import company.tap.tapuilibrary.atoms.TapTextView
import company.tap.tapuilibrary.datasource.AmountViewDataSource
import company.tap.tapuilibrary.datasource.HeaderDataSource
import company.tap.tapuilibrary.views.TapAmountSectionView
import company.tap.tapuilibrary.views.TapBottomSheetDialog
import company.tap.tapuilibrary.views.TapHeaderSectionView
import company.tap.tapuisample.R
import company.tap.tapuisample.TextDrawable
import kotlinx.android.synthetic.main.custom_bottom_sheet.*

/**
 * Created by AhlaamK on 7/6/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
class CardScannerFragment : TapBottomSheetDialog(),TapTextRecognitionCallBack , InlineViewCallback{
    private var textRecognitionML: TapTextRecognitionML? = null
    private var cardScanText: TapTextView? = null
    private lateinit var tapHeaderSectionView: TapHeaderSectionView
    private var imageUrl: String = "https://avatars3.githubusercontent.com/u/19837565?s=200&v=4"
    private var businessName: String? = null
    private var businessInitial: String? = null
    private lateinit var businessIcon: TapImageView
    private lateinit var businessPlaceholder: TapTextView
    private lateinit var amountSectionView: TapAmountSectionView
    private lateinit var selectedCurrency: TapTextView
    private lateinit var currentCurrency: TapTextView
    private lateinit var itemCount: TapButton
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
        headerViewInit(view)
        amountViewInit(view)
        FrameManager.getInstance().setFrameColor(Color.WHITE)
        return view

    }

    private fun amountViewInit(view: View) {
        amountSectionView = view.findViewById(R.id.amount_section_card)
        amountSectionView.setAmountViewDataSource(getAmountViewDataSOurce())
        currentCurrency = amountSectionView.findViewById(R.id.textView_currentcurrency)
        selectedCurrency = amountSectionView.findViewById(R.id.textview_selectedcurrency)
        itemCount = amountSectionView.findViewById(R.id.textView_itemcount)
        currentCurrency.visibility =View.GONE
    }


    @SuppressLint("ResourceType")
    private fun headerViewInit(view: View) {

        tapHeaderSectionView = view.findViewById(R.id.headerView_card)
        businessName = getString(R.string.tap_payments)
        tapHeaderSectionView.setHeaderDataSource(getHeaderDataSource())

        businessIcon = tapHeaderSectionView.findViewById(R.id.business_icon)

        businessPlaceholder = tapHeaderSectionView.findViewById(R.id.placeholder_text)
        businessInitial = businessName?.get(0).toString()
        businessPlaceholder.text = businessInitial

        Glide.with(this)
            .load(imageUrl)
            .placeholder(TextDrawable(businessInitial.toString()))
            .into(businessIcon)
    }

    private fun getHeaderDataSource(): HeaderDataSource {
        return HeaderDataSource(
            businessName = businessName,
            businessFor = LocalizationManager.getValue("paymentFor", "TapMerchantSection"),
            businessImageResources = imageUrl,
            businessPlaceHolder = businessName?.get(0).toString()
        )
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
        FrameManager.getInstance().setFrameColor(Color.GREEN)

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
    }
    private fun getAmountViewDataSOurce(): AmountViewDataSource {
        return AmountViewDataSource(
            selectedCurr = "KD1000,000.000",
            itemCount = "CLOSE"
        )
    }
}