package company.tap.tapuisample.fragments


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.ChangeBounds
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.tap.tapfontskit.FontChanger
import com.tap.tapfontskit.enums.TapFont
import com.tap.tapfontskit.enums.TapFont.Companion.tapFontType
import company.tap.cardinputwidget.widget.inline.InlineCardInput
import company.tap.tapcardvalidator_android.CardBrand
import company.tap.tapcardvalidator_android.CardValidationState
import company.tap.tapcardvalidator_android.CardValidator
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.animation.AnimationEngine
import company.tap.tapuilibrary.atoms.*
import company.tap.tapuilibrary.datasource.ActionButtonDataSource
import company.tap.tapuilibrary.datasource.AmountViewDataSource
import company.tap.tapuilibrary.datasource.HeaderDataSource
import company.tap.tapuilibrary.datasource.TapSwitchDataSource
import company.tap.tapuilibrary.enums.ActionButtonState
import company.tap.tapuilibrary.fragment.CurrencyViewFragment
import company.tap.tapuilibrary.interfaces.TapAmountSectionInterface
import company.tap.tapuilibrary.interfaces.TapSelectionTabLayoutInterface
import company.tap.tapuilibrary.models.SectionTabItem
import company.tap.tapuilibrary.views.*
import company.tap.tapuisample.R
import company.tap.tapuisample.adapters.CardTypeAdapter
import company.tap.tapuisample.interfaces.OnCardSelectedActionListener
import company.tap.tapuisample.webview.WebFragment
import company.tap.tapuisample.webview.WebViewContract
import kotlinx.android.synthetic.main.custom_bottom_sheet.action_button
import kotlinx.android.synthetic.main.custom_bottom_sheet.fragment_container


/**
 * Created by AhlaamK on 6/10/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
open class BottomSheetDialog : TapBottomSheetDialog(), TapSelectionTabLayoutInterface ,
    OnCardSelectedActionListener, WebViewContract {

    private lateinit var selectedCurrency: TapTextView
    private lateinit var currentCurrency: TapTextView
    private lateinit var itemCount: TapButton
    private var tapAmountSectionInterface: TapAmountSectionInterface? = null
    private lateinit var chipRecycler: RecyclerView
    private val paymentsList: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6)
    private var isFragmentAdded = false
    private var businessName: String? = null
    private var businessInitial: String? = null
    private lateinit var tapHeaderSectionView: TapHeaderSectionView
    private lateinit var amountSectionView: TapAmountSectionView
    private lateinit var businessIcon: TapImageView
    private lateinit var businessPlaceholder: TapTextView
    lateinit var tabLayout: TapSelectionTabLayout
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
    private var checkboxString:String="For faster and easier checkout,\n use card scanner or NFC."
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.custom_bottom_sheet, container, false)

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

    }

    override fun onViewCreated(
        view: View,
        @Nullable savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews(view)
        action_button.setButtonDataSource(getSuccessDataSource(R.color.button_gray))
    }
    private fun getSuccessDataSource(backgroundColor : Int): ActionButtonDataSource {
        return ActionButtonDataSource(
            text = "PAY!",
            textSize = 20f,
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
        addCardsTab()
        addMobileTab()
        setupBrandDetection()
        configureSwitch()

    }

    private fun switchViewInit(view: View) {
        switchDemo = view.findViewById(R.id.switch_pay_demo)
        switchDemo.setSwitchDataSource(getSwitchDataSource())
        switchSaveDemo = switchDemo.findViewById(R.id.switch_save_mobile)
        switchLayout = switchDemo.findViewById(R.id.switches_layout)
        separatorView = switchDemo.findViewById(R.id.switch_separator)
        switchMerchantCheckout = switchDemo.findViewById(R.id.switch_merchant_checkout)
        switchgoPayCheckout = switchDemo.findViewById(R.id.switch_gopay_checkout)
        saveCardorMobile = switchDemo.findViewById(R.id.text_save)
        savegoPay = switchDemo.findViewById(R.id.save_goPay)
        alertgoPay = switchDemo.findViewById(R.id.alert_gopay_signup)
        switchSaveDemo?.visibility= View.GONE

    }

    //Setting data to TapSwitchDataSource
    private fun getSwitchDataSource(): TapSwitchDataSource {
        return TapSwitchDataSource(
            switchSave = checkboxString,
            switchSaveMerchantCheckout = "Save for [merchant_name] Checkouts",
            switchSavegoPayCheckout = "By enabling goPay, your mobile number will be saved with Tap Payments to get faster and more secure checkouts in multiple apps and websites.",
            savegoPayText = "Save for goPay Checkouts",
            alertgoPaySignup = "Please check your email or SMS’s in order to complete the goPay Checkout signup process."

        )

    }

    private fun tabLayoutInit(view: View) {
        tabLayout = view.findViewById(R.id.sections_tablayout)
        nfcScanBtn = view.findViewById(R.id.nfc_scan)
        val nfcFragment = NFCFragment()
        nfcScanBtn.setOnClickListener {
            tabLayout.visibility=View.GONE
            paymentLayout.visibility=View.GONE
            currentCurrency.visibility = View.GONE
            fragment_container.visibility = View.GONE
            nfcScanBtn.visibility= View.GONE
            itemCount.text = "CLOSE"

            childFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_nfc,nfcFragment)
                .commit()
        }
        tabLayout.setTabLayoutInterface(this)
        tapMobileInputView = TapMobilePaymentView(context, null)
        if (context != null) {
            tapCardInputView = InlineCardInput(context!!)

        }
        bottomSheetDialog.behavior.state = STATE_EXPANDED
    }

    private fun setupFonts() {
        fontChanger = FontChanger(
            activity?.assets,
            tapFontType(TapFont.robotoRegular)
        )
        fontChanger!!.replaceFonts((activity?.findViewById(android.R.id.content) as ViewGroup?)!!)
    }

    @SuppressLint("ResourceAsColor")
    private fun setupChip(view: View) {
        val mainChipgroup = view.findViewById<TapChipGroup>(R.id.mainChipgroup)
        mainChipgroup.orientation = LinearLayout.HORIZONTAL
        val groupName = view.findViewById<TapTextView>(R.id.group_name)
        groupName.text = LocalizationManager.getValue("GatewayHeader", "HorizontalHeaders","leftTitle")
        groupName.setTextColor(R.color.text_color)
        val groupAction = view.findViewById<TapTextView>(R.id.group_action)
        groupAction.text = LocalizationManager.getValue("GatewayHeader", "HorizontalHeaders","rightTitle")
        groupName.setTextColor(R.color.text_color)
        chipRecycler = view.findViewById(R.id.chip_recycler)
        chipRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        chipRecycler.adapter = CardTypeAdapter(paymentsList, this)
        groupAction.setOnClickListener {
            Toast.makeText(context, "You clicked Edit", Toast.LENGTH_SHORT).show()

        }
    }


    @SuppressLint("ResourceType")
    private fun headerViewInit(view: View) {
        setupFonts()
        tapHeaderSectionView = view.findViewById(R.id.headerView)
        businessName = getString(R.string.tap_payments)
        tapHeaderSectionView.setHeaderDataSource(getHeaderDataSource())

        businessIcon = view.findViewById(R.id.business_icon)

        businessPlaceholder = view.findViewById(R.id.placeholder_text)
        businessInitial = businessName?.get(0).toString()
        businessPlaceholder.text = businessInitial

        Glide.with(this)
            .load(imageUrl)
            .placeholder(
                TextDrawable(
                    businessInitial.toString()
                )
            )
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
        val currencyViewFragment =
            CurrencyViewFragment()
        itemCount.setOnClickListener {
            tapAmountSectionInterface?.didClickItems()
            if (isFragmentAdded) {
                childFragmentManager
                    .beginTransaction()
                    .remove(currencyViewFragment)
                    .commit()
                fragment_container.visibility = View.VISIBLE
                tabLayout.visibility=View.VISIBLE
                paymentLayout.visibility=View.VISIBLE
                bottomSheetLayout?.let { layout ->
                    val removeTransition: Transition =
                        TransitionInflater.from(context)
                            .inflateTransition(R.transition.remove_fragment)
                    TransitionManager.beginDelayedTransition(layout, removeTransition)
                }
                selectedCurrency.text = "SR1000,000.000"
                itemCount.text = "22 ITEMS"
            } else {
                childFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container1, currencyViewFragment)
                    .commit()
                bottomSheetLayout?.let { layout ->
                    layout.post {
                        val addTransition: Transition =
                            TransitionInflater.from(context)
                                .inflateTransition(R.transition.add_fragment)
                        TransitionManager.beginDelayedTransition(layout, addTransition)
                    }
                }
                selectedCurrency.text = "KD1000,000.000"
                currentCurrency.visibility = View.GONE
                fragment_container.visibility = View.GONE
                tabLayout.visibility=View.GONE
                paymentLayout.visibility=View.GONE
                itemCount.text = LocalizationManager.getValue("close", "Common")
                Handler().postDelayed({
                    bottomSheetDialog.behavior.state = STATE_EXPANDED

                }, 1000)
            }
            isFragmentAdded = !isFragmentAdded

        }
        paymentLayout= view.findViewById(R.id.payment_input_layout)
        println("bottom state ${bottomSheetDialog.behavior.state}")

    }

    private fun getAmountViewDataSOurce(): AmountViewDataSource {
        return AmountViewDataSource(
            selectedCurr = "SR1000,000.000",
            currentCurr = "KD1000,000.000",
            itemCount = "22 ITEMS"
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
            }
            else
                paymentLayout.addView(tapMobileInputView)
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
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty())
                    tabLayout.resetBehaviour()
                val card = CardValidator.validate(s.toString())
                if (card.cardBrand != null){
                    tabLayout.selectTab(card.cardBrand, card.validationState == CardValidationState.valid)
                    checkboxString = LocalizationManager.getValue("cardSaveLabel","TapCardInputKit")
                    switchSaveDemo?.visibility= View.VISIBLE
                    switchLayout?.visibility = View.VISIBLE
                    switchMerchantCheckout?.visibility = View.VISIBLE
                    switchMerchantCheckout?.isChecked = true
                    switchgoPayCheckout?.isChecked = true
                    switchgoPayCheckout?.visibility = View.VISIBLE
                    savegoPay?.visibility = View.VISIBLE
                    alertgoPay?.visibility = View.VISIBLE
                    separatorView?.visibility = View.VISIBLE
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
           // tapSwitchInterface?.enableSaveMobile(isChecked)
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
            if(!switchMerchantCheckout?.isChecked!! && !switchgoPayCheckout?.isChecked!!){
                switchSaveDemo?.isChecked= false
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
            if(!switchMerchantCheckout?.isChecked!! && !switchgoPayCheckout?.isChecked!!){
                switchSaveDemo?.isChecked= false
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

    override fun onCardSelectedAction(isSelected:Boolean) {
        if (isSelected) {
            action_button.setButtonDataSource(getSuccessDataSource(R.color.button_green))
            action_button.setOnClickListener { replaceBetweenFragments() }
        }
        else
            action_button.setButtonDataSource(getSuccessDataSource(R.color.button_gray))
    }


    private fun replaceBetweenFragments(){
        childFragmentManager.beginTransaction().add(R.id.fragment_container,
            WebFragment(this)
        ).commit()
    }


    override fun redirectLoadingFinished(done: Boolean) {
        changeBottomSheetTransition()
        if (done) {
            action_button.visibility = View.VISIBLE
            fragment_container.visibility = View.GONE
            action_button.setButtonDataSource(getSuccessDataSource(R.color.button_green))
            action_button.changeButtonState(ActionButtonState.SUCCESS)
        } else {
            action_button.visibility = View.GONE
            fragment_container.visibility = View.VISIBLE
        }
    }
    private fun changeBottomSheetTransition(){
        bottomSheetLayout?.let { layout ->
            layout.post {
                TransitionManager.beginDelayedTransition(layout, ChangeBounds().setDuration(1000))
            }
        }
    }


}

