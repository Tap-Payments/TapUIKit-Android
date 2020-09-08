package company.tap.tapuisample.fragments


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import company.tap.cardinputwidget.widget.inline.InlineCardInput
import company.tap.tapcardvalidator_android.CardBrand
import company.tap.tapcardvalidator_android.CardValidationState
import company.tap.tapcardvalidator_android.CardValidator
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.fontskit.FontChanger
import company.tap.tapuilibrary.fontskit.enums.TapFont
import company.tap.tapuilibrary.fontskit.enums.TapFont.Companion.tapFontType
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.themekit.theme.SeparatorViewTheme
import company.tap.tapuilibrary.uikit.animation.AnimationEngine
import company.tap.tapuilibrary.uikit.atoms.*
import company.tap.tapuilibrary.uikit.datasource.ActionButtonDataSource
import company.tap.tapuilibrary.uikit.datasource.AmountViewDataSource
import company.tap.tapuilibrary.uikit.datasource.HeaderDataSource
import company.tap.tapuilibrary.uikit.datasource.TapSwitchDataSource
import company.tap.tapuilibrary.uikit.enums.ActionButtonState
import company.tap.tapuilibrary.uikit.fragment.CurrencyViewFragment
import company.tap.tapuilibrary.uikit.interfaces.TapAmountSectionInterface
import company.tap.tapuilibrary.uikit.interfaces.TapSelectionTabLayoutInterface
import company.tap.tapuilibrary.uikit.models.SectionTabItem
import company.tap.tapuilibrary.uikit.views.*
import company.tap.tapuisample.R
import company.tap.tapuisample.adapters.CardTypeAdapter
import company.tap.tapuisample.interfaces.OnCardSelectedActionListener
import company.tap.tapuisample.webview.WebFragment
import company.tap.tapuisample.webview.WebViewContract
import kotlinx.android.synthetic.main.custom_bottom_sheet.*
import kotlin.collections.ArrayList


/**

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
open class BottomSheetDialog : TapBottomSheetDialog(),
    TapSelectionTabLayoutInterface,
    OnCardSelectedActionListener,
    WebViewContract {

    private lateinit var selectedCurrency: TapTextView
    private lateinit var currentCurrency: TapTextView
    private lateinit var itemCount: TapButton
    private var tapAmountSectionInterface: TapAmountSectionInterface? = null
    private lateinit var chipRecycler: RecyclerView
    private val paymentsList: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6)
    private var isFragmentAdded = false
    private var businessName: String? = null
    private var paymentFor: String? = null
    private var businessInitial: String? = null
    private lateinit var tapHeaderSectionView: TapHeaderSectionView
    private lateinit var amountSectionView: TapAmountSectionView
    private lateinit var businessIcon: TapImageView
    private lateinit var businessPlaceholder: TapTextView
    lateinit var tabLayout: TapSelectionTabLayout
//    private var imageUrl: String? = null

        private var imageUrl: String = "https://avatars3.githubusercontent.com/u/19837565?s=200&v=4"
    var fontChanger: FontChanger? = null
    private var selectedTab = 0
    private lateinit var tapCardInputView: InlineCardInput
    private lateinit var tapMobileInputView: TapMobilePaymentView
    private lateinit var paymentLayout: LinearLayout
    private lateinit var nfcScanBtn: TapButton
    private lateinit var switchDemo: TapCardSwitch
    private var switchSaveDemo: TapSwitch? = null
    private var switchLayout: LinearLayout? = null
    private var switchMerchantCheckout: TapSwitch? = null
    private var switchgoPayCheckout: TapSwitch? = null
    private var savegoPay: TapTextView? = null
    private var alertgoPay: TapTextView? = null
    private var saveCardorMobile: TapTextView? = null
    private var separatorView: TapSeparatorView? = null
    private var checkboxString: String? = null
    private var mainChipGroup: TapChipGroup? = null
    private var groupName: TapTextView? = null
    private var groupAction: TapTextView? = null
    private var cardScannerBtn: ImageView? = null
    private var nfcButton : ImageView? = null
    private var mobileNumberEditText : EditText? = null
    private var alertMessage : TapTextView? = null
    private var clearView : ImageView? = null
    private var linearLayoutPay : LinearLayout? = null
    private var tapSeparatorViewLinear : LinearLayout? = null
    private val cardFragment = CardScannerFragment()
    private var cardFragmentadded :Boolean = false
    private var delImageView1 :ImageView?=null
    private var delImageView2 :ImageView?=null
    private var delImageView3 :ImageView?=null
    private var tapChipgrp :TapChip?=null




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.custom_bottom_sheet, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            tapAmountSectionInterface = context as TapAmountSectionInterface
        } catch (ex: ClassCastException) {
            try {
                tapAmountSectionInterface = parentFragment as TapAmountSectionInterface
            } catch (ignore: Exception) {
            }
        }

        dialog?.window?.attributes?.windowAnimations  = R.anim.slide_up

    }

    override fun onViewCreated(
        view: View,
        @Nullable savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews(view)
        actionButton.setButtonDataSource(getSuccessDataSource(R.color.button_pay))
        actionButton.stateListAnimator = null
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
        tapChipgrp = view.findViewById(R.id.tapcard_Chip)
        setSeparatorTheme()
        mainView.setBackgroundColor(Color.parseColor(ThemeManager.getValue("merchantHeaderView.backgroundColor")))

       // checkboxString = getString(R.string.nfc_text)
        //checkboxString =  LocalizationManager.getValue("cardSaveLabel","TapCardInputKit" )

    }


    //    fun replaceFont(){
//        if (LocalizationManager.getLocale(context!!) == Locale("en")) {
////            fontChanger = FontChanger(context?.assets, TapFont.tapFontType(TapFont.robotoRegular))
////            fontChanger = FontChanger(context?.assets, TapFont.tapFontType(TapFont.robotoThin))
////            fontChanger = FontChanger(context?.assets, TapFont.tapFontType(TapFont.robotoLight))
//            fontChanger = FontChanger(context?.assets, TapFont.tapFontType(TapFont.robotoMedium))
//            fontChanger?.replaceFonts(headerView)
//        }else{
//            fontChanger = FontChanger(context?.assets, TapFont.tapFontType(TapFont.tajawalMedium))
//            fontChanger?.replaceFonts(headerView)
//        }
//    }

    private fun getSuccessDataSource(backgroundColor: Int): ActionButtonDataSource {
        actionButton.stateListAnimator = null
        return ActionButtonDataSource(
            text = getString(R.string.pay),
            textSize = 18f,
            textColor = Color.WHITE,
            cornerRadius = 100f,
            successImageResources = R.drawable.checkmark,
            backgroundColor = resources.getColor(backgroundColor)
        )
    }

    @SuppressLint("SetTextI18n")
    private fun initializeViews(view: View) {
        headerViewInit(view)
        amountViewInit(view)
        tabLayoutInit(view)
        setupChip(view)
        switchViewInit(view)
        initializeCardForm(view)
        addCardsTab()
        addMobileTab()
        setupBrandDetection()
        configureSwitch()

    }

    private fun initializeCardForm(view: View) {
        cardScannerBtn = view.findViewById(R.id.card_scanner_button)
        nfcButton = view.findViewById(R.id.nfc_button)
        mobileNumberEditText = view.findViewById(R.id.mobile_number)
        alertMessage = view.findViewById(R.id.textview_alert_message)
        clearView = view.findViewById(R.id.clear_text)
        linearLayoutPay = view.findViewById(R.id.linear_paylayout)
        tapSeparatorViewLinear = view.findViewById(R.id.tapSeparatorViewLinear)
        tapSeparatorViewLinear?.setBackgroundColor(Color.parseColor(ThemeManager.getValue("horizontalList.backgroundColor")))

        tapCardInputView.clearFocus()
        clearView?.setOnClickListener {
            tabLayout.resetBehaviour()

            tapMobileInputView.clearNumber()
           /* tapCardInputView.setCardNumber("")
            tapCardInputView.setCvcCode("")*/
            tapCardInputView.clear()
            alert_text.visibility= View.GONE
            nfcButton?.visibility =View.VISIBLE
            cardScannerBtn?.visibility =View.VISIBLE
            clearView?.visibility= View.GONE

            switchSaveDemo?.visibility = View.GONE
            switchLayout?.visibility = View.GONE
            switchMerchantCheckout?.visibility = View.GONE
          //  switchMerchantCheckout?.isChecked = false
          //  switchgoPayCheckout?.isChecked = false
            switchgoPayCheckout?.visibility = View.GONE
            savegoPay?.visibility = View.GONE
            alertgoPay?.visibility = View.GONE
            separatorView?.visibility = View.GONE



        }
       // alertMessage?.visibility = View.GONE
        nfcButton?.setOnClickListener {
            val nfcFragment = NFCFragment()
            tabLayout.visibility = View.GONE
            paymentLayout.visibility = View.GONE
            currentCurrency.visibility = View.GONE
            mainChipGroup?.visibility = View.GONE
            // nfcScanBtn.visibility= View.GONE
            mainChipGroup?.visibility = View.GONE
            tap_payment_input0.visibility = View.GONE
            switchDemo.visibility = View.GONE
            actionButton.visibility = View.GONE
            outer_layout.visibility = View.GONE
            itemCount.text = "CLOSE"
            childFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_nfc, nfcFragment)
                .commit()
        }
        cardScannerBtn?.setOnClickListener {
           // val cardFragment = CardScannerFragment()
            tabLayout.visibility = View.GONE
            paymentLayout.visibility = View.GONE
            currentCurrency.visibility = View.GONE
            mainChipGroup?.visibility = View.GONE
            // nfcScanBtn.visibility= View.GONE
            mainChipGroup?.visibility = View.GONE
            tap_payment_input0.visibility = View.GONE
            switchDemo.visibility = View.GONE
            actionButton.visibility = View.GONE
            outer_layout.visibility = View.GONE
            itemCount.text = "CLOSE"
            childFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_nfc, cardFragment)
                .commit()
            cardFragmentadded = true
        }
    }

    private fun switchViewInit(view: View) {

       /* if(selectedTab==0){
            checkboxString = getString(R.string.nfc_text)
        }else{
            checkboxString = getString(R.string.mobile_text)
        }*/
        switchDemo = view.findViewById(R.id.switch_pay_demo)
        switchDemo.setSwitchDataSource(getSwitchDataSource("ola"))
        switchSaveDemo = switchDemo.findViewById(R.id.switch_save_mobile)
        switchLayout = switchDemo.findViewById(R.id.switches_layout)
        separatorView = switchDemo.findViewById(R.id.switch_separator)
        switchMerchantCheckout = switchDemo.findViewById(R.id.switch_merchant_checkout)
        switchgoPayCheckout = switchDemo.findViewById(R.id.switch_gopay_checkout)
        saveCardorMobile = switchDemo.findViewById(R.id.text_save)
        savegoPay = switchDemo.findViewById(R.id.save_goPay)
        alertgoPay = switchDemo.findViewById(R.id.alert_gopay_signup)
        switchSaveDemo?.visibility = View.GONE
    }

    //Setting data to TapSwitchDataSource
    private fun getSwitchDataSource( switchText: String): TapSwitchDataSource {
        return TapSwitchDataSource(
            switchSave = switchText,
            switchSaveMerchantCheckout = "Save for [merchant_name] Checkouts",
            switchSavegoPayCheckout = "By enabling goPay, your mobile number will be saved with Tap Payments to get faster and more secure checkouts in multiple apps and websites.",
            savegoPayText = "Save for goPay Checkouts",
            alertgoPaySignup = "Please check your email or SMS’s in order to complete the goPay Checkout signup process."
        )
    }

    private fun tabLayoutInit(view: View) {
        tabLayout = view.findViewById(R.id.sections_tablayout)
        //    nfcScanBtn = view.findViewById(R.id.nfc_scan)
        val nfcFragment = NFCFragment()

        tabLayout.setTabLayoutInterface(this)
        tapMobileInputView =
            TapMobilePaymentView(context, null)
        if (context != null) {
            tapCardInputView = InlineCardInput(context!!)
            println("mobile view $tapCardInputView")
        }

        bottomSheetDialog.behavior.state = STATE_EXPANDED
        cardScannerBtn?.setOnClickListener {
            val cardScannerFragment = CardScannerFragment()
            childFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_nfc, cardScannerFragment)
                .commit()
            cardFragmentadded = true
        }

    }

    private fun setupFonts() {
        if(context?.let { LocalizationManager.getLocale(it).language } == "en") {
            fontChanger = FontChanger(
                activity?.assets,
                tapFontType(TapFont.RobotoMedium)
            )
            fontChanger!!.replaceFonts((activity?.findViewById(android.R.id.content) as ViewGroup?)!!)
        }else{
            fontChanger = FontChanger(
                activity?.assets,
                tapFontType(TapFont.TajawalMedium)
            )
            fontChanger!!.replaceFonts((activity?.findViewById(android.R.id.content) as ViewGroup?)!!)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun setupChip(view: View) {
        mainChipGroup = view.findViewById<TapChipGroup>(R.id.mainChipgroup)
        mainChipgroup.orientation = LinearLayout.HORIZONTAL
         groupName = view.findViewById<TapTextView>(R.id.group_name)
        groupName?.text = LocalizationManager.getValue("GatewayHeader","HorizontalHeaders", "leftTitle")
//        groupName?.text = getString(R.string.select)
//        groupName?.setTextColor(R.color.darker_gray)
         groupAction = view.findViewById<TapTextView>(R.id.group_action)
        groupAction?.text = LocalizationManager.getValue("GatewayHeader","HorizontalHeaders", "rightTitle")
//        groupAction?.text = getString(R.string.edit)
//        groupName?.setTextColor(R.color.darker_gray)
        chipRecycler = view.findViewById(R.id.chip_recycler)
        chipRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        chipRecycler.adapter = CardTypeAdapter(paymentsList, this)
        delImageView1 = tapChipgrp?.findViewById(R.id.deleteImageView1)
        delImageView2 = tapChipgrp?.findViewById(R.id.deleteImageView2)
        delImageView3 = tapChipgrp?.findViewById(R.id.deleteImageView3)
        groupAction?.setOnClickListener {
            Toast.makeText(context, "You clicked Edit", Toast.LENGTH_SHORT).show()
            shakingCards(chipRecycler)
            delImageView1?.visibility = View.VISIBLE
            delImageView2?.visibility = View.VISIBLE
            delImageView3?.visibility = View.VISIBLE

        }
        delImageView1?.setOnClickListener {
            Log.d("mmmmm", "mmmm" )
            stopShakingCards(chipRecycler)
        }
        delImageView2?.setOnClickListener {
            stopShakingCards(chipRecycler)
        }
        delImageView3?.setOnClickListener {
            stopShakingCards(chipRecycler)
        }

    }

    private fun shakingCards(chipsView: RecyclerView) {
        val animShake:Animation=  AnimationUtils.loadAnimation(context, R.anim.shake)
            chipsView.startAnimation(animShake)

    }

    private fun stopShakingCards(chipsView: RecyclerView){
        val animShake:Animation =  AnimationUtils.loadAnimation(context, R.anim.shake)
        chipsView.clearAnimation()
        chipsView.animation =  null
    }


    @SuppressLint("ResourceType")
    private fun headerViewInit(view: View) {
        setupFonts()
        tapHeaderSectionView = view.findViewById(R.id.headerView)
        businessName =
            if (context?.let { LocalizationManager.getLocale(it).language } == "en") getString(R.string.tap_payments)
            else "تاب"

        paymentFor = LocalizationManager.getValue("paymentFor", "TapMerchantSection")
        tapHeaderSectionView.setHeaderDataSource(getHeaderDataSource())

        businessIcon = view.findViewById(R.id.businessIcon)

        businessPlaceholder = view.findViewById(R.id.placeholderText)
        businessInitial = businessName?.get(0).toString()
        businessPlaceholder.text = businessInitial
        if (imageUrl == null ){
            businessIcon.setBackgroundColor(Color.BLACK)
        }else{
            Glide.with(this)
                .load(imageUrl)
                .placeholder(
                    TextDrawable(
                        businessInitial.toString()
                    )
                )
                .into(businessIcon)
        }



    }

    private fun getHeaderDataSource(): HeaderDataSource {
        return HeaderDataSource(
            businessName = businessName,
            businessFor = LocalizationManager.getValue("paymentFor", "TapMerchantSection"),
//            businessFor = paymentFor ,
            businessImageResources = imageUrl
//            businessPlaceHolder = businessName?.get(0).toString()
        )
    }

    @SuppressLint("SetTextI18n")
    private fun amountViewInit(view: View) {
        amountSectionView = view.findViewById(R.id.amount_section)
        amountSectionView.setAmountViewDataSource(getAmountViewDataSOurce())
        currentCurrency = view.findViewById(R.id.textView_currentcurrency)
        selectedCurrency = view.findViewById(R.id.textview_selectedcurrency)
        itemCount = view.findViewById(R.id.textView_itemcount)

        if (isFragmentAdded) {
            currentCurrency.visibility = View.VISIBLE
        }
         val itemListDummy: ArrayList<Int> =
            arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 20, 21, 22)

//        val currencyViewFragment = CurrencyViewFragment(itemListDummy)
        val currencyViewFragment = CurrencyViewFragment()
        itemCount.setOnClickListener {
            tapAmountSectionInterface?.didClickItems()
                if (isFragmentAdded) {

               /* childFragmentManager
                    .beginTransaction()
                    .remove(currencyViewFragment)
                    .commit()*/
               // dialog?.window?.attributes?.windowAnimations= R.anim.slide_down

             Handler().postDelayed({

                     childFragmentManager
                         .beginTransaction()
                         .remove(currencyViewFragment)
                         .commit()


                 }, 20)

               // bottomSheetDialog.behavior.state = STATE_HALF_EXPANDED
               /* bottomSheetLayout?.let { layout ->
                    val removeTransition: Transition =
                        TransitionInflater.from(context)
                            .inflateTransition(R.transition.add_fragment)
                    TransitionManager.beginDelayedTransition(layout, removeTransition)
                }
*/
                    mainChipGroup?.visibility = View.VISIBLE
                //  tabLayout.visibility = View.GONE
                //  paymentLayout.visibility = View.GONE
                tap_payment_input0.visibility = View.VISIBLE
                paymentLayout.removeAllViews()
                paymentLayout.addView(tapCardInputView)
              /*  Handler().postDelayed({


                    fragment_container.visibility = View.VISIBLE
                    //  tabLayout.visibility = View.GONE
                    //  paymentLayout.visibility = View.GONE
                    tap_payment_input0.visibility = View.VISIBLE
                    paymentLayout.removeAllViews()
                    paymentLayout.addView(tapCardInputView)


                }, 50)*/
                /* bottomSheetLayout?.let { layout ->
                     val removeTransition: Transition =
                         TransitionInflater.from(context)
                             .inflateTransition(R.transition.remove_fragment)
                     TransitionManager.beginDelayedTransition(layout, removeTransition)
                 }*/
                dialog?.window?.attributes?.windowAnimations= R.anim.fade_in

                selectedCurrency.text = "SR1000,000.000"
                itemCount.text = getString(R.string.items)
            } else {
                separator_.visibility = View.GONE
                separatorــ.visibility = View.GONE
//                indicatorSeparator.visibility = View.GONE
                Handler().postDelayed({

                    mainChipGroup?.visibility = View.GONE
                    tap_payment_input0.visibility = View.GONE


                }, 20)

                bottomSheetDialog.behavior.state = STATE_EXPANDED

                if(cardFragmentadded){

                    childFragmentManager
                        .beginTransaction()
                        .remove(cardFragment)
                        .commit()
                }else {
                    childFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container_nfc, currencyViewFragment)
                        .commit()
                }

                bottomSheetLayout?.let { layout ->
                    layout.post {
                        val addTransition: Transition =
                            TransitionInflater.from(context)
                                .inflateTransition(R.transition.add_fragment)
                        TransitionManager.beginDelayedTransition(layout, addTransition)
                    }
                }

                currentCurrency.visibility = View.VISIBLE
                selectedCurrency.text = "KD1000,000.000"
//                currentCurrency.visibility = View.GONE

                itemCount.text = LocalizationManager.getValue("close", "Common")
               /* bottomSheetDialog.behavior.state = STATE_EXPANDED

                fragment_container.visibility = View.GONE
                tap_payment_input0.visibility = View.GONE

                tabLayout.visibility = View.VISIBLE
                paymentLayout.visibility = View.VISIBLE*/


                Handler().postDelayed({




                    tabLayout.visibility = View.VISIBLE
                    paymentLayout.visibility = View.VISIBLE


                }, 50)
            }
            isFragmentAdded = !isFragmentAdded

        }
        paymentLayout = view.findViewById(R.id.payment_input_layout)
        println("bottom state ${bottomSheetDialog.behavior.state}")

    }

    private fun getAmountViewDataSOurce(): AmountViewDataSource {
        return AmountViewDataSource(
            selectedCurr = "SR1000,000.000",
            currentCurr = "KD1000,000.000",
            itemCount = if (context?.let { LocalizationManager.getLocale(it).language } == "en") getString(
                R.string.items
            ) else "22 عنصر"
        )
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun onTabSelected(position: Int?) {
        position?.let {
            selectedTab = it
            AnimationEngine.applyTransition(paymentLayout)
            paymentLayout.removeAllViews()
            if (position == 0) {
                paymentLayout.addView(tapCardInputView)
                switchDemo.setSwitchDataSource(getSwitchDataSource(getString(R.string.nfc_text)))
                cardScannerBtn?.visibility = View.VISIBLE
                nfcButton?.visibility = View.VISIBLE
                clearView?.visibility = View.GONE

            } else if(position == 1){
                //paymentLayout.removeAllViews()
                paymentLayout.addView(tapMobileInputView)
                switchDemo.setSwitchDataSource(getSwitchDataSource(getString(R.string.mobile_text)))
               // paymentLayout.removeView(tapCardInputView)
                cardScannerBtn?.visibility = View.GONE
                nfcButton?.visibility = View.GONE
                clearView?.visibility = View.VISIBLE

                // tapMobileInputView.mobileInputEditText.text
                mobileNumberEditText?.addTextChangedListener(object : TextWatcher {
                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {


                    }
                    override fun afterTextChanged(mobileText: Editable) {
                        if(mobileText.length==12){
                            mobileNumberEditText?.setText(mobileText)

                        }
                        println("mobile number value ${mobileText.length}")
                    }
                    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                })

            }


        }
    }

    private fun addCardsTab() {
        val items = ArrayList<SectionTabItem>()
        items.add(
            SectionTabItem(
                resources.getDrawable(
                    R.drawable.ic_visa
                ), resources.getDrawable(R.drawable.ic_visa_black), CardBrand.visa
            )
        )
        items.add(
            SectionTabItem(
                resources.getDrawable(
                    R.drawable.mastercard
                ), resources.getDrawable(R.drawable.mastercard_gray), CardBrand.masterCard
            )
        )
        items.add(
            SectionTabItem(
                resources.getDrawable(
                    R.drawable.amex
                ), resources.getDrawable(R.drawable.amex_gray), CardBrand.americanExpress
            )
        )
        tabLayout.addSection(items)
    }

    private fun addMobileTab() {
        val items = ArrayList<SectionTabItem>()
        items.add(
            SectionTabItem(
                resources.getDrawable(
                    R.drawable.zain_gray
                ), resources.getDrawable(R.drawable.zain_dark), CardBrand.zain
            )
        )
        items.add(
            SectionTabItem(
                resources.getDrawable(
                    R.drawable.ooredoo
                ), resources.getDrawable(R.drawable.ooredoo_gray), CardBrand.ooredoo
            )
        )
        tabLayout.addSection(items)
    }

    private fun setupBrandDetection() {
        tapCardInputView.setCardNumberTextWatcher(object : TextWatcher {
            @SuppressLint("ResourceAsColor")
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty())
                    tabLayout.resetBehaviour()
                val card = CardValidator.validate(s.toString())
                if (card.cardBrand != null) {
                    //linearLayoutPay?.visibility = View.GONE
                    activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                    clearView?.visibility = View.VISIBLE
                    tabLayout.selectTab(
                        card.cardBrand,
                        card.validationState == CardValidationState.valid
                    )


                    nfcButton?.visibility = View.GONE
                    cardScannerBtn?.visibility = View.GONE
                    tapCardInputView.holderNameEnabled= false
                    if (card.validationState == CardValidationState.incomplete) {
                       // switchSaveDemo?.visibility = View.VISIBLE
                        switchLayout?.visibility = View.GONE
                        switchMerchantCheckout?.visibility = View.GONE
                        switchMerchantCheckout?.isChecked = true
                        switchgoPayCheckout?.isChecked = true
                        switchgoPayCheckout?.visibility = View.GONE
                        savegoPay?.visibility = View.GONE
                        alertgoPay?.visibility = View.GONE
                        separatorView?.visibility = View.GONE
                       // checkboxString = getString(R.string.savecard_text)

                        switchDemo.setSwitchDataSource(getSwitchDataSource(getString(R.string.mobile_save_text)))
                        alertMessage?.visibility = View.VISIBLE
                        alertMessage?.setText("Card number is invalid")
                        alert_text.visibility = View.VISIBLE
                        alert_text.setBackgroundColor(Color.parseColor("#19e12131"))
                        alertMessage?.setTextColor(Color.parseColor("#e12131"))


                    }
                  //  checkboxString =  LocalizationManager.getValue("cardSaveLabel","TapCardInputKit" )
                    if(s?.trim()?.length== 19 && card.validationState == CardValidationState.valid) {
                        switchSaveDemo?.visibility = View.VISIBLE
                        switchLayout?.visibility = View.VISIBLE
                        switchMerchantCheckout?.visibility = View.VISIBLE
                        switchMerchantCheckout?.isChecked = true
                        switchgoPayCheckout?.isChecked = true
                        switchgoPayCheckout?.visibility = View.VISIBLE
                        savegoPay?.visibility = View.VISIBLE
                        alertgoPay?.visibility = View.VISIBLE
                        separatorView?.visibility = View.VISIBLE
                       // checkboxString = getString(R.string.savecard_text)
                        switchDemo.setSwitchDataSource(getSwitchDataSource(getString(R.string.savecard_text)))
                        alertMessage?.visibility = View.VISIBLE

                                if(card.validationState == CardValidationState.invalid){
                                    alertMessage?.setText("Card number is invalid")
                                    alert_text.visibility = View.VISIBLE
                                    alert_text.setBackgroundColor(Color.parseColor("#19e12131"))
                                    alertMessage?.setTextColor(Color.parseColor("#e12131"))
                                }else {
                                    alertMessage?.setText("Expiry date & CVV number are missing.")
                                    alert_text.visibility = View.VISIBLE
                                    alert_text.setBackgroundColor(Color.parseColor("#4cffbe60"))
                                    alertMessage?.setTextColor(Color.parseColor("#ea611c"))


                                }


                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        tapCardInputView.setCvcNumberTextWatcher(object : TextWatcher {
            @SuppressLint("ResourceAsColor")
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()){
                    tabLayout.resetBehaviour()
                }else
                alert_text.visibility = View.GONE


            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        tapCardInputView.setExpiryDateTextWatcher(object : TextWatcher {
            @SuppressLint("ResourceAsColor")
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()){
                    tabLayout.resetBehaviour()
                }
                else {
                    alertMessage?.setText("CVV number are missing.")
                    alert_text.visibility = View.VISIBLE
                    alert_text.setBackgroundColor(Color.parseColor("#4cffbe60"))
                    alertMessage?.setTextColor(Color.parseColor("#ea611c"))
                }



                    }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

    }

    // Configuring switch states and listening to switch states.
    private fun configureSwitch() {

        switchSaveDemo?.setOnCheckedChangeListener { buttonView, isChecked ->
            println("isChecked Save value $isChecked")

            if (isChecked) {
                switchLayout?.visibility = View.VISIBLE
                switchMerchantCheckout?.visibility = View.VISIBLE
                switchMerchantCheckout?.isChecked = true
                switchgoPayCheckout?.isChecked = true
                switchgoPayCheckout?.visibility = View.VISIBLE
                savegoPay?.visibility = View.VISIBLE
                alertgoPay?.visibility = View.VISIBLE
                separatorView?.visibility = View.VISIBLE
            } else {
                switchLayout?.visibility = View.GONE
                switchMerchantCheckout?.visibility = View.GONE
                switchMerchantCheckout?.isChecked = false
                switchgoPayCheckout?.isChecked = false
                switchgoPayCheckout?.visibility = View.GONE
                savegoPay?.visibility = View.GONE
                alertgoPay?.visibility = View.GONE
                separatorView?.visibility = View.GONE
            }
        }
        switchMerchantCheckout?.setOnCheckedChangeListener { buttonView, isChecked ->
            //tapSwitchInterface?.enableSaveMerchantCheckout(isChecked)
            if (!switchMerchantCheckout?.isChecked!! && !switchgoPayCheckout?.isChecked!!) {
                switchSaveDemo?.isChecked = false
                switchLayout?.visibility = View.GONE
                switchMerchantCheckout?.visibility = View.GONE
                switchMerchantCheckout?.isChecked = false
                switchgoPayCheckout?.isChecked = false
                switchgoPayCheckout?.visibility = View.GONE
                savegoPay?.visibility = View.GONE
                alertgoPay?.visibility = View.GONE
                separatorView?.visibility = View.GONE
            }
        }
        switchgoPayCheckout?.setOnCheckedChangeListener { buttonView, isChecked ->
            // tapSwitchInterface?.enableSavegoPayCheckout(isChecked)
            if (!switchMerchantCheckout?.isChecked!! && !switchgoPayCheckout?.isChecked!!) {
                switchSaveDemo?.isChecked = false
                switchLayout?.visibility = View.GONE
                switchMerchantCheckout?.visibility = View.GONE
                switchMerchantCheckout?.isChecked = false
                switchgoPayCheckout?.isChecked = false
                switchgoPayCheckout?.visibility = View.GONE
                savegoPay?.visibility = View.GONE
                alertgoPay?.visibility = View.GONE
                separatorView?.visibility = View.GONE
            }
        }
    }

    override fun onCardSelectedAction(isSelected: Boolean) {
        if (isSelected) {
            actionButton.setButtonDataSource(getSuccessDataSource(R.color.button_green))
            actionButton.setOnClickListener {
                switchLayout?.visibility = View.GONE
                switchMerchantCheckout?.visibility = View.GONE
                switchMerchantCheckout?.isChecked = false
                switchgoPayCheckout?.isChecked = false
                switchgoPayCheckout?.visibility = View.GONE
//                currentCurrency.visibility = View.GONE
                tabLayout.visibility = View.GONE
                paymentLayout.visibility = View.GONE
                tapHeaderSectionView.visibility = View.GONE
                businessIcon.visibility = View.GONE
                businessPlaceholder.visibility = View.GONE
                businessPlaceholder.visibility = View.GONE
                amountSectionView.visibility = View.GONE
                switchDemo.visibility = View.GONE
                tabLayout.visibility=View.GONE
                paymentLayout.visibility=View.GONE
                tapHeaderSectionView.visibility=View.GONE
                businessIcon.visibility=View.GONE
                businessPlaceholder.visibility=View.GONE
                businessPlaceholder.visibility=View.GONE
                amountSectionView.visibility=View.GONE
                switchDemo.visibility=View.GONE
                separatorView?.visibility = View.GONE
                chipRecycler.visibility = View.GONE
//                selectedCurrency.visibility= View.GONE
                nfcButton?.visibility =View.GONE
                cardScannerBtn?.visibility =View.GONE
                tap_payment_input0?.visibility = View.GONE
                switchSaveDemo?.visibility = View.GONE
                savegoPay?.visibility = View.GONE
                alertgoPay?.visibility = View.GONE
                saveCardorMobile?.visibility = View.GONE
                headerView?.visibility = View.GONE
                chipRecycler.visibility= View.GONE
                switchSaveDemo?.visibility= View.GONE
                savegoPay?.visibility= View.GONE
                alertgoPay?.visibility= View.GONE
                saveCardorMobile?.visibility= View.GONE
                headerView?.visibility= View.GONE
                separator.visibility = View.GONE
                groupAction?.visibility= View.GONE
                groupName?.visibility= View.GONE
                separator_.visibility = View.GONE
                topSeparator.visibility = View.GONE
                separatorــ.visibility = View.GONE
                if(paymentsList.get(2)==3){
                    Toast.makeText(context,"goPay is clicked",Toast.LENGTH_SHORT).show()
                    childFragmentManager
                        .beginTransaction()
                        .remove( WebFragment(this))
                        .commit()
                    dialog?.hide()
                 changeBottomSheetTransition()
                }else
                actionButton.addChildView(
                    actionButton.getImageView(
                        R.drawable.loader,
                        1
                    ) { replaceBetweenFragments() })
//                action_button.changeButtonState(ActionButtonState.LOADING)
//                changeBottomSheetTransition()

            }
        } else
            actionButton.setButtonDataSource(getSuccessDataSource(R.color.button_pay))
    }

    override fun onDeleteIconClicked(stopAnimation: Boolean, itemId: Int) {
        if (stopAnimation)   stopShakingCards(chipRecycler)
    }


    private fun replaceBetweenFragments(){
        actionButton.visibility= View.GONE
        dialog?.window?.attributes?.windowAnimations  = R.anim.slide_up
//        slidingUpAnimate()
        childFragmentManager.beginTransaction().replace(R.id.webViewContainer,
            WebFragment(this)
        ).commit()
    }


    override fun redirectLoadingFinished(done: Boolean) {
        changeBottomSheetTransition()
        if (done) {
            actionButton.visibility = View.VISIBLE
            webViewContainer.visibility = View.GONE
            actionButton.setButtonDataSource(getSuccessDataSource(R.color.button_green))
            actionButton.changeButtonState(ActionButtonState.SUCCESS)
        } else {
            actionButton.visibility = View.GONE
            webViewContainer.visibility = View.VISIBLE
        }
    }

    private fun changeBottomSheetTransition() {
        bottomSheetLayout?.let { layout ->
            layout.post {
                TransitionManager.beginDelayedTransition(layout)
            }
        }
    }


    fun slidingUpAnimate(){
        val animate = view?.height?.toFloat()?.let {
            TranslateAnimation(
                0F,  // fromXDelta
                0F,  // toXDelta
                it,  // fromYDelta
                0F
            )
        } // toYDelta
        animate?.duration = 500
        animate?.fillAfter = true
        view!!.startAnimation(animate)
    }
//
    fun slidingDownAnimate(){
        val animate = view?.height?.toFloat()?.let {
            TranslateAnimation(
                0F,  // fromXDelta
                0F,  // toXDelta
                0F,  // fromYDelta
                it
            )
        } // toYDelta

        animate?.duration = 500
        animate?.fillAfter = true
        view!!.startAnimation(animate)
    }


    fun setSeparatorTheme(){
        val separatorViewTheme = SeparatorViewTheme()
//        separatorViewTheme.strokeColor = Color.parseColor(ThemeManager.getValue("tapSeparationLine.backgroundColor"))
//        separatorViewTheme.strokeHeight = ThemeManager.getValue("tapSeparationLine.height")
//        indicatorSeparator.setTheme(separatorViewTheme)
    }
}

