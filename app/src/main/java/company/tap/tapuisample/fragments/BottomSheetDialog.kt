package company.tap.tapuisample.fragments


import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import company.tap.cardinputwidget.CardBrandSingle
import company.tap.cardinputwidget.widget.inline.InlineCardInput
import company.tap.tapcardvalidator_android.CardBrand
import company.tap.tapcardvalidator_android.CardValidationState
import company.tap.tapcardvalidator_android.CardValidator
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.fontskit.FontChanger
import company.tap.tapuilibrary.fontskit.enums.TapFont
import company.tap.tapuilibrary.fontskit.enums.TapFont.Companion.tapFontType
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.themekit.theme.EditTextTheme
import company.tap.tapuilibrary.themekit.theme.SeparatorViewTheme
import company.tap.tapuilibrary.uikit.animation.AnimationEngine
import company.tap.tapuilibrary.uikit.atoms.*
import company.tap.tapuilibrary.uikit.datasource.*
import company.tap.tapuilibrary.uikit.enums.ActionButtonState
import company.tap.tapuilibrary.uikit.enums.GoPayLoginMethod
import company.tap.tapuilibrary.uikit.fragment.CardScannerFragment
import company.tap.tapuilibrary.uikit.fragment.NFCFragment
import company.tap.tapuilibrary.uikit.interfaces.*
import company.tap.tapuilibrary.uikit.ktx.setBorderedView
import company.tap.tapuilibrary.uikit.ktx.setBottomBorders
import company.tap.tapuilibrary.uikit.ktx.setTopBorders
import company.tap.tapuilibrary.uikit.models.SectionTabItem
import company.tap.tapuilibrary.uikit.organisms.GoPayLoginInput
import company.tap.tapuilibrary.uikit.organisms.GoPayPasswordInput
import company.tap.tapuilibrary.uikit.organisms.OTPView
import company.tap.tapuilibrary.uikit.organisms.TapPaymentInput
import company.tap.tapuilibrary.uikit.views.*
import company.tap.tapuisample.*
import company.tap.tapuisample.MainSwitch
import company.tap.tapuisample.TapHeaderSectionView
import company.tap.tapuisample.adapters.CardTypeAdapter
import company.tap.tapuisample.interfaces.OnCardSelectedActionListener
import company.tap.tapuisample.webview.WebFragment
import company.tap.tapuisample.webview.WebViewContract
import kotlinx.android.synthetic.main.custom_bottom_sheet.*
import kotlinx.android.synthetic.main.custom_bottom_sheet.switch_pay_demo
import kotlinx.android.synthetic.main.item_knet.view.*
import kotlinx.android.synthetic.main.tap_main_header.view.*

/**
Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
open class BottomSheetDialog : TapBottomSheetDialog(),
    TapSelectionTabLayoutInterface, GoPayLoginInterface, OpenOTPInterface,
    OnCardSelectedActionListener, TapActionButtonInterface, TapPaymentShowHideClearImage,
    WebViewContract, OtpButtonConfirmationInterface {

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
    private lateinit var tapCloseIcon: TapImageView
    private lateinit var businessPlaceholder: TapTextView
    var fontChanger: FontChanger? = null
    private var selectedTab = 0

    private lateinit var tabLayout: company.tap.tapuilibrary.uikit.views.TapSelectionTabLayout
    private lateinit var tapCardInputView: InlineCardInput
    private lateinit var tapMobileInputView: TapMobilePaymentView

    private lateinit var paymentLayout: LinearLayout
    private lateinit var nfcScanBtn: TapButton
    private lateinit var switchDemo: TapInlineCardSwitch
    private lateinit var mainSwitch: MainSwitch
    private lateinit var payButton: company.tap.tapuilibrary.uikit.views.TabAnimatedActionButton
    private var switch_save_mobile: TapSwitch? = null
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
    private var nfcButton: ImageView? = null
    private var mobileNumberEditText: EditText? = null
    private var alertMessage: TapTextView? = null
    private var clearView: ImageView? = null
    private var linearLayoutPay: RelativeLayout? = null
    private var tapSeparatorViewLinear: LinearLayout? = null
    private val cardFragment = CardScannerFragment()
    private val nfcFragment = NFCFragment()
    private var cardFragmentadded: Boolean = false
    private var delImageView1: ImageView? = null
    private var delImageView2: ImageView? = null
    private var delImageView3: ImageView? = null
    private var tapChipgrp: TapChip? = null
    private var tapCardChip2: TapChip? = null
    private var goPayPasswordInput: GoPayPasswordInput? = null
    private var goPayLoginInput: GoPayLoginInput? = null
    private var otpButtonConfirmationInterface: OtpButtonConfirmationInterface? = null
    private var otpView: OTPView? = null
    private var acceptedCardText: TapTextView? = null
    private var mainLinear: LinearLayout? = null
    private var contactDetailView: TapContactDetailsView? = null
    private var shippingDetailView: TapShippingDetailView? = null
    private var mobileMainLinear: LinearLayout? = null
    lateinit var cardInputCardView: LinearLayout
    lateinit var tapPaymentInput: TapPaymentInput
    lateinit var saveForLaterCheckBox: CheckBox


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.custom_bottom_sheet, container, false)
        bottomSheetDialog.behavior.state = STATE_EXPANDED
        otpView = view.findViewById(R.id.otpView)
        otpView?.setOTPInterface(this)
        otpView?.setOtpButtonConfirmationInterface(this)
        initGoPay(view)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view.rootView
    }

    fun initGoPay(view: View) {
        goPayLoginInput = view.findViewById(R.id.goPayLoginInput)
        goPayPasswordInput = view.findViewById(R.id.goPayPassword)
        goPayLoginInput?.changeDataSource(GoPayLoginDataSource())
        goPayLoginInput?.setLoginInterface(this)
        goPayLoginInput?.setOpenOTPInterface(this)
        goPayPasswordInput?.setLoginInterface(this, goPayLoginInput?.textInput?.text.toString())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            tapAmountSectionInterface = context as TapAmountSectionInterface
        } catch (ex: ClassCastException) {
            try {
                tapAmountSectionInterface = parentFragment as TapAmountSectionInterface
            } catch (ignore: Exception) {
            }
        }
        // dialog?.window?.attributes?.windowAnimations = R.anim.slide_up
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        switch_pay_demo.payButton.setButtonDataSource(
            false, context?.let { LocalizationManager.getLocale(it).language },
            LocalizationManager.getValue("pay", "ActionButton"),
            Color.parseColor(ThemeManager.getValue("actionButton.Invalid.backgroundColor")),
            Color.parseColor(ThemeManager.getValue("actionButton.Invalid.titleLabelColor"))
        )


        //switch_pay_demo.brandingLayout.visibility =View.VISIBLE
        /**
         * Calling this class for adjust view up when keyboard is opened
         */
        activity?.let { KeyboardUtil(it, view) }


        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
        bottomSheetLayout = bottomSheetDialog.findViewById(R.id.design_bottom_sheet)
        bottomSheetLayout?.setBackgroundColor(Color.MAGENTA)
        /**
         * set separator background
         */
        topSeparatorLinear.setBackgroundColor((Color.parseColor(ThemeManager.getValue("merchantHeaderView.backgroundColor"))))


        switch_pay_demo.payButton.stateListAnimator = null
        switch_pay_demo.payButton.isActivated = false
        tapChipgrp = view.findViewById(R.id.tapcard_Chip)
        tapCardChip2 = view.findViewById(R.id.tapCardChip2)
        setAllSeparatorTheme()
//        setTapMobileInputViewTheme()
//        dialog?.getWindow()?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));
//        mainView.clipToOutline = true
//        outer_layout.setBackgroundColor(Color.parseColor(ThemeManager.getValue("TapSwitchView.backgroundColor")))

        /**
         * set bottom sheet background
         */
        //   backgroundColor =
        //       (Color.parseColor(ThemeManager.getValue("GlobalValues.Colors.main_switch_background")))
        //  backgroundColor = (Color.parseColor("#00000000"))
        setTopBorders(
            topLinear,
            40f,// corner raduis
            0.0f,
            Color.parseColor(ThemeManager.getValue("merchantHeaderView.backgroundColor")),// stroke color
            Color.parseColor(ThemeManager.getValue("merchantHeaderView.backgroundColor")),// tint color
            Color.parseColor(ThemeManager.getValue("merchantHeaderView.backgroundColor"))
        )//
        val borderColor: String =
            ThemeManager.getValue<String>("poweredByTap.backgroundColor").toString()
        var borderOpacityVal: String? = null
        //Workaround since we don't have direct method for extraction
        borderOpacityVal = borderColor.substring(borderColor.length - 2)
        /* setTopBorders(
             topLinear2.outerConstraint,
            40f,// corner raduis
            0.0f,
             Color.parseColor("#"+borderOpacityVal+borderColor.substring(0, borderColor.length -2).replace("#","")),
             Color.parseColor("#"+borderOpacityVal+borderColor.substring(0, borderColor.length -2).replace("#","")),// tint color
             Color.parseColor("#"+borderOpacityVal+borderColor.substring(0, borderColor.length -2).replace("#",""))
        )//
*/


        BlurBuilder.blur(topLinear2)
        separatorــLayout.setBackgroundColor(Color.parseColor(ThemeManager.getValue("TapSwitchView.main.backgroundColor")))
        initializeViews(view)


        switch_pay_demo.payButton.setOnClickListener {
            if (switch_pay_demo.payButton.isActivated) {
                changeBottomSheetTransition()

                /**
                 * check if there is saving options or not if not we will set visibility for switches layout in TapCardSwitch Gone
                 * if user want to check main switch
                 */
//                switch_pay_demo.showOnlyPayButton()
//                mainSwitchUncheckedAction()
//                switch_pay_demo.payButton.changeButtonState(ActionButtonState.LOADING)
//                switch_pay_demo.payButton.addChildView(
//                    switch_pay_demo.payButton.getImageView(
//                        R.drawable.loader,
//                        1
//                    ) {  })

                if (paymentsList[2] == 3) {
                    goPayLoginInput?.visibility = View.VISIBLE
//                    goPayPasswordInput?.visibility = View.VISIBLE
                    mainChipgroup?.visibility = View.GONE
                    tapSeparatorViewLinear?.visibility = View.GONE
                    separatorــLayout?.visibility = View.GONE
                    hideAllViews()
                    tapHeaderSectionView.visibility = View.VISIBLE
                    amountSectionView.visibility = View.VISIBLE
                    businessPlaceholder.visibility = View.VISIBLE
                    topSeparator.visibility = View.VISIBLE
                    separator.visibility = View.VISIBLE
                    businessIcon.visibility = View.VISIBLE


//                    Toast.makeText(context, "goPay is clicked", Toast.LENGTH_SHORT).show()
//                    activity?.supportFragmentManager?.let { it1 ->
//                        ExampleFragment()
//                            .show(it1, null)
//                    }
//
//
//                    childFragmentManager
//                        .beginTransaction()
//                        .add(R.id.fragment_container_nfc, exampleFragment)
//                        .commit()
//                    cardFragmentadded = true
//                    goPayLoginInput.visibility= View.VISIBLE
//                    goPayPassword.visibility= View.VISIBLE
//                        childFragmentManager
//                            .beginTransaction()
//                            .remove(WebFragment(this))
//                            .commit()
//                        dialog?.hide()
//                    changeBottomSheetTransition()
                }
//                else


            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        return BottomSheetDialog(requireContext(), R.style.MyTransparentBottomSheetDialogTheme)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    private fun initializeViews(view: View) {
        OnCLick()
        headerViewInit(view)
        amountViewInit(view)
        tabLayoutInit(view)
        setupChip(view)
        switchViewInit(view)
        initializeCardForm(view)
        addCardsTab()
        //  addMobileTab() // commented for now 28sep22
        setupBrandDetection()
        configureSwitch()
        initCustomerDetailView(view)
    }

    private fun initCustomerDetailView(view: View) {
        contactDetailView = view.findViewById(R.id.contactDetailView)
        shippingDetailView = view.findViewById(R.id.shipDetailView)
        mobileMainLinear = contactDetailView?.findViewById(R.id.mobilePaymentMainLinear)
        mobileMainLinear?.visibility = View.VISIBLE
        shippingDetailView?.visibility = View.GONE

        contactDetailView?.contactEmailET?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(arg0: Editable) {
                if (arg0.contains("tap")) {
                    contactDetailView?.mobileMainLinear?.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        })

        contactDetailView?.contactMobileNumber?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(arg0: Editable) {
                if (arg0.contains("55567")) {
                    shippingDetailView?.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        })

    }

    private fun OnCLick() {
        otpView!!.timerText.setOnClickListener {

            otpView!!.restartTimer()
        }
    }

    private fun initializeCardForm(view: View) {
        cardScannerBtn = view.findViewById(R.id.card_scanner_button)
        nfcButton = view.findViewById(R.id.nfc_button)
        mobileNumberEditText = view.findViewById(R.id.mobileNumber)
        alertMessage = view.findViewById(R.id.textViewAlertMessage)
        clearView = view.findViewById(R.id.clear_text)
        linearLayoutPay = view.findViewById(R.id.linear_paylayout)
        mainLinear = view.findViewById(R.id.mainLinear)
        tapSeparatorViewLinear = view.findViewById(R.id.tapSeparatorViewLinear)
        tapSeparatorViewLinear?.setBackgroundColor(Color.parseColor(ThemeManager.getValue("horizontalList.backgroundColor")))
        mainLinear?.setBackgroundColor(Color.parseColor(ThemeManager.getValue("horizontalList.backgroundColor")))

        tapCardInputView.clearFocus()
        tapCardInputView.holderNameEnabled = true
        clearView?.setOnClickListener {
            tabLayout.resetBehaviour()
            tapMobileInputView.clearNumber()
            tapCardInputView.clear()
            alert_text.visibility = View.GONE
            clearView?.visibility = View.GONE
            switchSaveDemo?.visibility = View.GONE
            switchLayout?.visibility = View.GONE
            switchMerchantCheckout?.visibility = View.GONE
            switchgoPayCheckout?.visibility = View.GONE
            savegoPay?.visibility = View.GONE
            alertgoPay?.visibility = View.GONE
            separatorView?.visibility = View.GONE
            tabLayout.visibility = View.VISIBLE
            tapCardInputView.holderNameEnabled = false
        }
        nfcButton?.setOnClickListener {
            tabLayout?.visibility = View.GONE
            paymentLayout.visibility = View.GONE
            currentCurrency.visibility = View.GONE
            mainChipGroup?.visibility = View.GONE
            mainChipGroup?.visibility = View.GONE
            tap_payment_input0.visibility = View.GONE
            switchDemo.visibility = View.GONE
            mainSwitch.visibility = View.GONE
            separatorــLayout.visibility = View.GONE
            alert_text.visibility = View.GONE
            separatorــ.visibility = View.GONE
            tapSeparatorViewLinear?.visibility = View.GONE
            switch_pay_demo.payButton.visibility = View.GONE
            itemCount.text = "CLOSE"
            childFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_nfc, nfcFragment)
                .commit()
            cardFragmentadded = true

        }
        cardScannerBtn?.setBackgroundColor(Color.parseColor(ThemeManager.getValue("inlineCard.commonAttributes.backgroundColor")))
        cardScannerBtn?.setOnClickListener {
            bottomSheetDialog.behavior.isFitToContents = false
            val cardFragment = CardScannerFragment()
            tabLayout?.visibility = View.GONE
            paymentLayout.visibility = View.GONE
            currentCurrency.visibility = View.GONE
            mainChipGroup?.visibility = View.GONE
            // nfcScanBtn.visibility= View.GONE
            cardSwitch.visibility = View.GONE
            switch_pay_demo.visibility = View.GONE
            mainChipGroup?.visibility = View.GONE
            tap_payment_input0.visibility = View.GONE
            switchDemo.visibility = View.GONE
            mainSwitch.visibility = View.GONE
            cardSwitch.visibility = View.GONE
            switch_pay_demo.payButton.visibility = View.GONE
//            outer_layout.visibility = View.GONE
            itemCount.text = "CLOSE"

            childFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_nfc, cardFragment)
                .commit()
//            cardFragmentadded = true
        }
    }

    private fun switchViewInit(view: View) {
        switchDemo = view.findViewById(R.id.switch_pay_demo)
        // switchDemo.brandingLayout.visibility =View.VISIBLE
        switchDemo.tapLogoImage.visibility = View.VISIBLE
        mainSwitch = view.findViewById(R.id.mainSwitch)
        switchSaveDemo = mainSwitch.findViewById(R.id.switchSaveMobile)
//        switch_save_mobile = switchDemo.findViewById(R.id.switch_save_mobile)
        payButton = switchDemo.findViewById(R.id.payButton)
        switchLayout = switchDemo.findViewById(R.id.switches_layout)
        separatorView = switchDemo.findViewById(R.id.switch_separator)
        switchMerchantCheckout = switchDemo.findViewById(R.id.switch_merchant_checkout)
        switchgoPayCheckout = switchDemo.findViewById(R.id.switch_gopay_checkout)
//        saveCardorMobile = switchDemo.findViewById(R.id.text_save)
        savegoPay = switchDemo.findViewById(R.id.save_goPay)
        alertgoPay = switchDemo.findViewById(R.id.alert_gopay_signup)
        mainSwitch.mainSwitchLinear.setBackgroundColor(Color.parseColor(ThemeManager.getValue("TapSwitchView.main.backgroundColor")))
        cardSwitch.cardElevation = 0f


//        card.setCardBackgroundColor(Color.parseColor(ThemeManager.getValue("TapSwitchView.main.backgroundColor")))
        mainSwitch.setSwitchDataSource(getSwitchDataSource(getString(R.string.mobile_text)))

    }

    //Setting data to TapSwitchDataSource
    private fun getSwitchDataSource(switchText: String): TapSwitchDataSource {
        return TapSwitchDataSource(
            switchSave = switchText,
            switchSaveMerchantCheckout = "Save for Later",
            switchSavegoPayCheckout = "By enabling goPay, your mobile number will be saved with Tap Payments to get faster and more secure checkouts in multiple apps and websites.",
            savegoPayText = "Save for goPay Checkouts",
            alertgoPaySignup = "Please check your email or SMS’s in order to complete the goPay Checkout signup process."
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun tabLayoutInit(view: View) {
        tabLayout = view.findViewById(R.id.sections_tablayout)
        tabLayout.setTabLayoutInterface(this)
        tabLayout.visibility = View.VISIBLE
        tapMobileInputView = TapMobilePaymentView(context, null)
        if (context != null) {
            tapCardInputView = context?.let { InlineCardInput(it) }!!
            println("mobile view $tapCardInputView")
        }
        acceptedCardText = view.findViewById(R.id.acceptedCardText)
        acceptedCardText?.setBackgroundColor(Color.parseColor(ThemeManager.getValue("horizontalList.backgroundColor")))
        bottomSheetDialog.behavior.state = STATE_EXPANDED
        tapMobileInputView.setTapPaymentShowHideClearImage(this)
        tabLayout.setBackgroundColor(Color.parseColor(ThemeManager.getValue("inlineCard.commonAttributes.backgroundColor")))
       // tabLayout.changeTabItemMarginLeftValue(-20)
      //  tabLayout.changeTabItemMarginBottomValue(30)

      //  tabLayout.changeTabItemMarginRightValue(-20)
    }

    private fun setupFonts() {
        if (context?.let { LocalizationManager.getLocale(it).language } == "en") {
            fontChanger = FontChanger(
                activity?.assets,
                tapFontType(TapFont.RobotoMedium)
            )
            fontChanger!!.replaceFonts((activity?.findViewById(android.R.id.content) as ViewGroup?)!!)
        } else {
            fontChanger = FontChanger(
                activity?.assets,
                tapFontType(TapFont.TajawalMedium)
            )
            fontChanger!!.replaceFonts((activity?.findViewById(android.R.id.content) as ViewGroup?)!!)
        }
    }


    @SuppressLint("ResourceAsColor")
    private fun setupChip(view: View) {
        mainChipGroup = view.findViewById(R.id.mainChipgroup)
        tapPaymentInput = view.findViewById(R.id.tap_payment_input0)


        //cardInputCardView = tapPaymentInput.findViewById(R.id.inline_CardView)
        mainChipgroup.orientation = LinearLayout.HORIZONTAL
        groupName = view.findViewById<TapTextView>(R.id.group_name)
        groupName?.text = LocalizationManager.getValue(
            "GatewayHeader",
            "HorizontalHeaders",
            "leftTitle"
        )
        groupAction = view.findViewById<TapTextView>(R.id.group_action)
        groupAction?.text = LocalizationManager.getValue(
            "GatewayHeader",
            "HorizontalHeaders",
            "rightTitle"
        )
        chipRecycler = view.findViewById(R.id.chip_recycler)
        chipRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)


        /**
         * Setting divider for items
         */
        val divider = DividerItemDecoration(
            context,
            DividerItemDecoration.HORIZONTAL
        )
        divider.setDrawable(ShapeDrawable().apply {
            intrinsicWidth = 10
            paint.color = Color.TRANSPARENT
        }) // note: currently (support version 28.0.0), we can not use tranparent color here, if we use transparent, we still see a small divider line. So if we want to display transparent space, we can set color = background color or we can create a custom ItemDecoration instead of DividerItemDecoration.
        chipRecycler.addItemDecoration(divider)



        chipRecycler.adapter = CardTypeAdapter(paymentsList, this, false)

        groupAction?.visibility = View.VISIBLE

        groupAction?.setOnClickListener {
            if (groupAction?.text == "Close") {
                chipRecycler.adapter = CardTypeAdapter(paymentsList, this, false)
                groupAction?.text = "Edit"

            } else {
                chipRecycler.adapter = CardTypeAdapter(paymentsList, this, true)
                groupAction?.text = "Close"
            }

        }
//        }

        /* tapPaymentInput.cardInputChipView.setBackgroundResource(R.drawable.border_unclick)
         setBorderedView(
             tapPaymentInput.cardInputChipView,
             //(ThemeManager.getValue("horizontalList.chips.radius") as Int).toFloat(),// corner raduis
             19.0f,// corner raduis
             0.0f,
             Color.parseColor(ThemeManager.getValue("horizontalList.chips.goPayChip.selected.shadow.color")),// stroke color
             Color.parseColor(ThemeManager.getValue("horizontalList.chips.gatewayChip.backgroundColor")),// tint color
             Color.parseColor(ThemeManager.getValue("horizontalList.chips.goPayChip.unSelected.shadow.color"))
         )// shadow color
 */
        tapPaymentInput.paymentInputContainer.setBackgroundResource(R.drawable.border_unclick)
        setBorderedView(
            tapPaymentInput.paymentInputContainer,
            //(ThemeManager.getValue("horizontalList.chips.radius") as Int).toFloat(),// corner raduis
            19.0f,// corner raduis
            0.0f,
            Color.parseColor(ThemeManager.getValue("horizontalList.chips.goPayChip.selected.shadow.color")),// stroke color
            Color.parseColor(ThemeManager.getValue("horizontalList.chips.gatewayChip.backgroundColor")),// tint color
            Color.parseColor(ThemeManager.getValue("horizontalList.chips.goPayChip.unSelected.shadow.color"))
        )// shadow color

        tapPaymentInput.tabLinear.setBackgroundResource(R.drawable.border_unclick)
        setBorderedView(
            tapPaymentInput.tabLinear,
            //(ThemeManager.getValue("horizontalList.chips.radius") as Int).toFloat(),// corner raduis
            19.0f,// corner raduis
            0.0f,
            Color.parseColor(ThemeManager.getValue("horizontalList.chips.goPayChip.selected.shadow.color")),// stroke color
            Color.parseColor(ThemeManager.getValue("horizontalList.chips.gatewayChip.backgroundColor")),// tint color
            Color.parseColor(ThemeManager.getValue("horizontalList.chips.goPayChip.unSelected.shadow.color"))
        )// shadow color
    }

    private fun stopShakingCards(chipsView: RecyclerView) {
        chipsView.adapter = CardTypeAdapter(paymentsList, this, false)
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
        tapCloseIcon = view.findViewById(R.id.tapCloseIcon)
        businessPlaceholder = view.findViewById(R.id.placeholderText)
        tapCloseIcon.setBackgroundColor(Color.parseColor(ThemeManager.getValue("merchantHeaderView.cancelButton.backgroundColor")))
        tapCloseIcon.setOnClickListener {
            this.dismiss()
        }

    }

    private fun getHeaderDataSource(): HeaderDataSource {
        return HeaderDataSource(
            businessName = businessName,
            businessFor = LocalizationManager.getValue("paymentFor", "TapMerchantSection")
        )
    }


    @SuppressLint("SetTextI18n")
    private fun amountViewInit(view: View) {
        amountSectionView = view.findViewById(R.id.amount_section)
        amountSectionView.setAmountViewDataSource(getAmountViewDataSOurce())
        currentCurrency = view.findViewById(R.id.mainKDAmountValue)
        selectedCurrency = view.findViewById(R.id.selectedAmountValue)
        itemCount = view.findViewById(R.id.itemCountButton)

        amountSectionView.itemAmountLayout.setOnClickListener {
            //  bottomSheetLayout?.setBackgroundResource(R.drawable.corner_radius_top)
            //  bottomSheetLayout?.setBackgroundColor(Color.parseColor(ThemeManager.getValue("merchantHeaderView.backgroundColor")))
            bottomSheetLayout?.setBackgroundColor(Color.RED)
            bottomSheetLayout?.let { it1 ->
                setTopBorders(
                    it1,
                    40f,// corner raduis
                    0.0f,
                    Color.parseColor(ThemeManager.getValue("merchantHeaderView.backgroundColor")),// stroke color
                    Color.parseColor(ThemeManager.getValue("merchantHeaderView.backgroundColor")),// tint color
                    Color.parseColor(ThemeManager.getValue("merchantHeaderView.backgroundColor"))
                )
            }//

        }
        if (isFragmentAdded) {
            currentCurrency.visibility = View.VISIBLE
        } else {
            currentCurrency.visibility = View.GONE
        }
//        val itemListDummy: ArrayList<Int> =
//            arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 20, 21, 22)
        val itemListDummy: ArrayList<String> =
            arrayListOf("1", "2", "3", "4", "5")
        val currencyViewFragment = CurrencyViewFragment()
        itemCount.setOnClickListener {
            tapAmountSectionInterface?.didClickItems()
            if (isFragmentAdded) {
                Handler().postDelayed({
                    childFragmentManager
                        .beginTransaction()
                        .remove(currencyViewFragment)
                        .commit()
                }, 0)

                separatorــLayout.visibility = View.VISIBLE

                mainChipGroup?.visibility = View.VISIBLE
                separator_.visibility = View.VISIBLE
                tapSeparatorViewLinear?.visibility = View.VISIBLE
                tap_payment_input0.visibility = View.VISIBLE
                separatorــ.visibility = View.VISIBLE
                separatorــ.setBackgroundColor(Color.parseColor(ThemeManager.getValue("tapSeparationLine.backgroundColor")))
                paymentLayout.removeAllViews()
                paymentLayout.addView(tapCardInputView)
                selectedCurrency.text = "AED1000,000.000"
                itemCount.text = getString(R.string.items)

            } else {
                separatorــLayout.visibility = View.GONE

//                switch_pay_demo.payButton.visibility = View.GONE
                separator_.visibility = View.GONE
                separatorــ.visibility = View.GONE
                Handler().postDelayed({
                    mainChipGroup?.visibility = View.GONE
                    tap_payment_input0.visibility = View.GONE
                }, 0)

                bottomSheetDialog.behavior.state = STATE_EXPANDED

                if (cardFragmentadded) {
                    childFragmentManager
                        .beginTransaction()
                        .remove(cardFragment)
                        .commit()
                } else {
                    childFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container_nfc, currencyViewFragment)
                        .commit()
                }

//                bottomSheetLayout?.let { layout ->
//                    layout.post {
//                        val addTransition: Transition =
//                            TransitionInflater.from(context)
//                                .inflateTransition(R.transition.add_fragment)
//                        TransitionManager.beginDelayedTransition(layout, addTransition)
//                    }
//                }

                currentCurrency.visibility = View.VISIBLE
                selectedCurrency.text = "KD1000,000.000"
                itemCount.text = LocalizationManager.getValue("close", "Common")
                Handler().postDelayed({
                    tabLayout.visibility = View.VISIBLE
                    paymentLayout.visibility = View.VISIBLE
                }, 0)
//                fragment_container_nfc.visibility = View.GONE


            }
            isFragmentAdded = !isFragmentAdded
        }
        paymentLayout = view.findViewById(R.id.payment_input_layout)
        println("bottom state ${bottomSheetDialog.behavior.state}")
    }

    private fun getAmountViewDataSOurce(): AmountViewDataSource {
        return AmountViewDataSource(
            selectedCurr = "1000,000.000",
            selectedCurrText = "AED",
            currentCurr = "1000,000.000",
            currentCurrText = "KD",
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
            paymentLayout.removeAllViews()
            if (position == 0) {
                paymentLayout.addView(tapCardInputView)
                switchDemo.setSwitchDataSource(getSwitchDataSource(getString(R.string.nfc_text)))
                mainSwitch.setSwitchDataSource(getSwitchDataSource(getString(R.string.nfc_text)))
                cardScannerBtn?.visibility = View.VISIBLE
                nfcButton?.visibility = View.VISIBLE
                clearView?.visibility = View.GONE
            } else if (position == 1) {
                paymentLayout.addView(tapMobileInputView)
                switchDemo.setSwitchDataSource(getSwitchDataSource(getString(R.string.mobile_text)))
                mainSwitch.setSwitchDataSource(getSwitchDataSource(getString(R.string.mobile_text)))
                cardScannerBtn?.visibility = View.GONE
                nfcButton?.visibility = View.GONE
                mobileNumberEditText?.addTextChangedListener(object : TextWatcher {
                    override fun onTextChanged(
                        s: CharSequence,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }

                    override fun afterTextChanged(mobileText: Editable) {
                        if (mobileText.length > 2) {
                            clearView?.visibility = View.VISIBLE
                        }
                        if (mobileText.length == 12) {
                            mobileNumberEditText?.text = mobileText
                        }
                        println("mobile number value ${mobileText.length}")
                    }

                    override fun beforeTextChanged(
                        s: CharSequence,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }
                })
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun addCardsTab() {

        tabLayout.changeTabItemAlphaValue(0.9f)

        val items = ArrayList<SectionTabItem>()

        items.add(
            SectionTabItem(
                "https://tap-assets.b-cdn.net/payment-options/v2/dark/visa.png",
                "https://tap-assets.b-cdn.net/payment-options/v2/dark/visa.png",
                CardBrand.visa
            )
        )
        items.add(
            SectionTabItem(
                "https://tap-assets.b-cdn.net/payment-options/v2/dark/mastercard.png",
                "https://tap-assets.b-cdn.net/payment-options/v2/dark/mastercard.png",
                CardBrand.masterCard
            )
        )

        items.add(
            SectionTabItem(
                "https://tap-assets.b-cdn.net/payment-options/v2/dark/american_express.png",
                "https://tap-assets.b-cdn.net/payment-options/v2/dark/american_express.png",
                CardBrand.americanExpress
            )
        )

        tabLayout.addSection(items)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun addMobileTab() {
        /**
         * if there is only one payment method we will set visibility gone for tablayout
         * and set the payment method icon for inline input card
         * and set visibility  for separator after chips gone
         */
//        tabLayout.visibility = View.GONE
//        tapSeparatorViewLinear?.visibility = View.GONE
//        tapCardInputView.setSingleCardInput(company.tap.cardinputwidget.CardBrand.Visa)


        tabLayout.changeTabItemAlphaValue(0.9f)


        val items = ArrayList<SectionTabItem>()

        items.add(
            SectionTabItem(
                "https://img.icons8.com/color/2x/visa.png",
                "https://img.icons8.com/color/2x/visa.png",
                CardBrand.ooredoo
            )
        )


        items.add(
            SectionTabItem(
                "https://img.icons8.com/color/2x/visa.png",
                "https://img.icons8.com/color/2x/visa.png",
                CardBrand.ooredoo
            )
        )
        items.add(
            SectionTabItem(
                "https://img.icons8.com/color/2x/visa.png",
                "https://img.icons8.com/color/2x/visa.png",
                CardBrand.ooredoo
            )
        )
        items.add(
            SectionTabItem(
                "https://img.icons8.com/color/2x/visa.png",
                "https://img.icons8.com/color/2x/visa.png",
                CardBrand.ooredoo
            )
        )
        tabLayout.addSection(items) //

    }

    private fun setupBrandDetection() {
        tapCardInputView.setCardNumberTextWatcher(object : TextWatcher {
            @RequiresApi(Build.VERSION_CODES.N)
            @SuppressLint("ResourceAsColor")
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty())
                    tabLayout.resetBehaviour()
                val card = CardValidator.validate(s.toString())
                if (card.cardBrand != null) {
                    activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                    clearView?.visibility = View.VISIBLE
                    tabLayout.selectTab(
                        card.cardBrand,
                        card.validationState == CardValidationState.valid
                    )
                    nfcButton?.visibility = View.GONE
                    cardScannerBtn?.visibility = View.GONE
                    tapCardInputView.holderNameEnabled = true
                    if (card.validationState == CardValidationState.incomplete) {
                        switchLayout?.visibility = View.GONE
                        switchMerchantCheckout?.visibility = View.GONE
                        switchMerchantCheckout?.isChecked = true
                        switchgoPayCheckout?.isChecked = true
                        switch_pay_demo.payButton.isActivated = true
                        switch_pay_demo.payButton.visibility = View.VISIBLE
                        switchgoPayCheckout?.visibility = View.GONE
                        savegoPay?.visibility = View.GONE
                        alertgoPay?.visibility = View.GONE
                        separatorView?.visibility = View.GONE
                        switchDemo.setSwitchDataSource(getSwitchDataSource(getString(R.string.mobile_save_text)))
                        mainSwitch.setSwitchDataSource(getSwitchDataSource(getString(R.string.mobile_save_text)))
                        alertMessage?.visibility = View.VISIBLE
                        alertMessage?.text = (LocalizationManager.getValue(
                            "Error",
                            "Hints",
                            "wrongCardNumber"
                        ))
                        alert_text.visibility = View.VISIBLE
                    }
                    if (s?.trim()?.length == 19 && card.validationState == CardValidationState.valid) {
                        /**
                         * This an important part for showing switches part correctly as in design
                         */
                        switch_pay_demo.payButton.isActivated
                        switchSaveDemo?.visibility = View.VISIBLE
                        switchMerchantCheckout?.visibility = View.VISIBLE
                        cardSwitch.visibility = View.VISIBLE
                        mainSwitch.visibility = View.VISIBLE
                        alertgoPay?.visibility = View.VISIBLE
                        separatorView?.visibility = View.VISIBLE
                        switchDemo.setSwitchDataSource(getSwitchDataSource(getString(R.string.savecard_text)))
                        mainSwitch.setSwitchDataSource(getSwitchDataSource(getString(R.string.savecard_text)))
                        alertMessage?.visibility = View.VISIBLE
                        if (card.validationState == CardValidationState.invalid) {
                            alertMessage?.text = (LocalizationManager.getValue(
                                "Error",
                                "Hints",
                                "wrongCardNumber"
                            ))
                            alert_text.visibility = View.VISIBLE
                        } else {
                            alertMessage?.text = (LocalizationManager.getValue(
                                "Warning",
                                "Hints",
                                "missingExpiryCVV"
                            ))
//                            alertMessage?.setText("Expiry date & CVV number are missing.")
                            alert_text.visibility = View.VISIBLE
                        }
                        tapCardInputView.setSingleCardInput(CardBrandSingle.fromCode(card.cardBrand.name))
                        tabLayout.visibility = View.GONE
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        tapCardInputView.setCvcNumberTextWatcher(object : TextWatcher {
            @RequiresApi(Build.VERSION_CODES.N)
            @SuppressLint("ResourceAsColor")
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
//                    tabLayout.resetBehaviour()
                } else
                    alert_text.visibility = View.GONE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        tapCardInputView.setExpiryDateTextWatcher(object : TextWatcher {
            @RequiresApi(Build.VERSION_CODES.N)
            @SuppressLint("ResourceAsColor")
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
//                    tabLayout.resetBehaviour()
                } else {
                    alertMessage?.text = "CVV number are missing."
                    alertMessage?.text = (LocalizationManager.getValue(
                        "Warning",
                        "Hints",
                        "missingCVV"
                    ))
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

    /**
     * We will change tap card switch background if main switch checked or not
     */
    private fun configureSwitch() {
//        switch_pay_demo.tapCardSwitchLinear.setBackgroundColor(Color.parseColor(ThemeManager.getValue("TapSwitchView.main.backgroundColor")))
//                switch_pay_demo.tapCardSwitchLinear.setBackgroundColor(Color.parseColor(ThemeManager.getValue("TapSwitchView.backgroundColor")))

        setBottomBorders(
            cardSwitch,
            30f,// corner raduis
            0.0f,
            Color.parseColor(ThemeManager.getValue("TapSwitchView.backgroundColor")),// stroke color
            Color.parseColor(ThemeManager.getValue("TapSwitchView.backgroundColor")),// tint color
            Color.parseColor(ThemeManager.getValue("TapSwitchView.backgroundColor"))
        )//
        setBottomBorders(
            cardSwitch,
            30f,// corner raduis
            0.0f,
            Color.parseColor(ThemeManager.getValue("TapSwitchView.backgroundColor")),// stroke color
            Color.parseColor(ThemeManager.getValue("TapSwitchView.backgroundColor")),// tint color
            Color.parseColor(ThemeManager.getValue("TapSwitchView.backgroundColor"))
        )//
        switchSaveDemo?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                switch_pay_demo.payButton.isActivated
                payButton.setButtonDataSource(
                    true,
                    context?.let { LocalizationManager.getLocale(it).language },
                    LocalizationManager.getValue("pay", "ActionButton"),
                    Color.parseColor(ThemeManager.getValue("actionButton.Valid.paymentBackgroundColor")),
                    Color.parseColor(ThemeManager.getValue("actionButton.Valid.titleLabelColor"))
                )
                /**
                 * Here we will check if there is saving options if NOT ----> We will just activate action button
                 * switch_pay_demo.payButton.isActivated
                 * if YES -----> we will set Logic of function mainSwitchCheckedAction()
                 */
                mainSwitchCheckedAction()

            } else mainSwitchUncheckedAction()
        }
        switchMerchantCheckout?.setOnCheckedChangeListener { _, _ -> switchMerchantCheckoutChangeCheckedAction() }
        switchgoPayCheckout?.setOnCheckedChangeListener { _, _ -> switchGoPayCheckoutChangeCheckedAction() }
    }

    private fun switchMerchantCheckoutChangeCheckedAction() {
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

    private fun switchGoPayCheckoutChangeCheckedAction() {
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


    fun mainSwitchCheckedAction() {
        if (ThemeManager.currentTheme.isNotEmpty() && ThemeManager.currentTheme.contains("dark")) {
            switch_pay_demo.tapCardSwitchLinear.setBackgroundResource(company.tap.tapuilibrary.R.drawable.ic_blurbackgroundblack)
        } else {
            switch_pay_demo.tapCardSwitchLinear.setBackgroundResource(company.tap.tapuilibrary.R.drawable.blurbackground)
        }
        cardSwitch.cardElevation = 2.5f

        setBorderedView(
            mainSwitch.card,
            40f,// corner raduis
            0.0f,
            Color.parseColor(ThemeManager.getValue("TapSwitchView.main.backgroundColor")),// stroke color
            Color.parseColor(ThemeManager.getValue("TapSwitchView.main.backgroundColor")),// tint color
            Color.parseColor(ThemeManager.getValue("TapSwitchView.main.backgroundColor"))
        )//
        switch_pay_demo.payButton.stateListAnimator = null
        switch_pay_demo.payButton.isActivated
        switch_pay_demo.payButton.setButtonDataSource(
            true,
            company.tap.tapuisample.adapters.context?.let { LocalizationManager.getLocale(it).language },
            LocalizationManager.getValue("pay", "ActionButton"),
            Color.parseColor(ThemeManager.getValue("actionButton.Valid.paymentBackgroundColor")),
            Color.parseColor(ThemeManager.getValue("actionButton.Valid.titleLabelColor"))
        )
        switchLayout?.visibility = View.VISIBLE
        switchMerchantCheckout?.visibility = View.VISIBLE
        switchMerchantCheckout?.isChecked = true
        switchgoPayCheckout?.isChecked = true
        switchgoPayCheckout?.visibility = View.VISIBLE
        savegoPay?.visibility = View.VISIBLE
        alertgoPay?.visibility = View.VISIBLE
        separatorView?.visibility = View.VISIBLE
    }

    private fun mainSwitchUncheckedAction() {
        setBorderedView(
            mainSwitch.card,
            0f,// corner raduis
            0.0f,
            Color.parseColor(ThemeManager.getValue("TapSwitchView.main.backgroundColor")),// stroke color
            Color.parseColor(ThemeManager.getValue("TapSwitchView.main.backgroundColor")),// tint color
            Color.parseColor(ThemeManager.getValue("TapSwitchView.main.backgroundColor"))
        )//
        switch_pay_demo.tapCardSwitchLinear.setBackgroundColor(
            Color.parseColor(
                ThemeManager.getValue(
                    "TapSwitchView.main.backgroundColor"
                )
            )
        )
        cardSwitch.cardElevation = 0f

//        switch_pay_demo.payButton.stateListAnimator = null
//        switch_pay_demo.payButton.setButtonDataSource(
//            false,
//            company.tap.tapuisample.adapters.context?.let { LocalizationManager.getLocale(it).language },
//            LocalizationManager.getValue("pay", "ActionButton"),
//            Color.parseColor(ThemeManager.getValue("actionButton.Invalid.backgroundColor")),
//            Color.parseColor(ThemeManager.getValue("actionButton.Invalid.titleLabelColor"))
//        )
//        switchLayout?.visibility = View.GONE
//        switchMerchantCheckout?.visibility = View.GONE
//        switchMerchantCheckout?.isChecked = false
//        switchgoPayCheckout?.isChecked = false
//        switchgoPayCheckout?.visibility = View.GONE
//        savegoPay?.visibility = View.GONE
//        alertgoPay?.visibility = View.GONE
//        separatorView?.visibility = View.GONE

    }


    private fun getSuccessDataSource(text: String): ActionButtonDataSource {
        switch_pay_demo.payButton.stateListAnimator = null
        return ActionButtonDataSource(
            text = text,
            textSize = 20f,
            textColor = Color.WHITE,
            cornerRadius = 100f,
            successImageResources = R.drawable.checkmark,
            backgroundColor = resources.getColor(R.color.button_green)
        )
    }

    override fun onCardSelectedAction(isSelected: Boolean) {
        if (isSelected) {
            switch_pay_demo.payButton.isActivated = true
            switch_pay_demo.payButton.setButtonDataSource(
                true,
                company.tap.tapuisample.adapters.context?.let { LocalizationManager.getLocale(it).language },
                LocalizationManager.getValue("pay", "ActionButton"),
                Color.parseColor(ThemeManager.getValue("actionButton.Valid.paymentBackgroundColor")),
                Color.parseColor(ThemeManager.getValue("actionButton.Valid.titleLabelColor"))
            )

        } else {

            switch_pay_demo.payButton.isActivated = false
            switch_pay_demo.payButton.setButtonDataSource(
                false,
                company.tap.tapuisample.adapters.context?.let { LocalizationManager.getLocale(it).language },
                LocalizationManager.getValue("pay", "ActionButton"),
                Color.parseColor(ThemeManager.getValue("actionButton.Invalid.backgroundColor")),
                Color.parseColor(ThemeManager.getValue("actionButton.Invalid.titleLabelColor"))
            )
        }
    }

    override fun onDeleteIconClicked(stopAnimation: Boolean, itemId: Int) {
        if (stopAnimation) {
            stopShakingCards(chipRecycler)
            groupAction?.text = "Edit"
        } else groupAction?.text = "Close"

    }


    private fun replaceBetweenFragments() {
        switch_pay_demo.payButton.visibility = View.GONE
        dialog?.window?.attributes?.windowAnimations = R.anim.slide_up
        childFragmentManager.beginTransaction().replace(
            R.id.webViewContainer,
            WebFragment(this)
        ).commit()
    }

    override fun redirectLoadingFinished(done: Boolean) {
        changeBottomSheetTransition()
        if (done) {
            switch_pay_demo.payButton.visibility = View.VISIBLE
            webViewContainer.visibility = View.GONE
            switch_pay_demo.payButton.isActivated = true
            switch_pay_demo.payButton.changeButtonState(ActionButtonState.SUCCESS)
        } else {
            switch_pay_demo.payButton.visibility = View.GONE
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

    fun hideAllViews() {
        switchLayout?.visibility = View.GONE
        switchMerchantCheckout?.visibility = View.GONE
        switchMerchantCheckout?.isChecked = false
        switchgoPayCheckout?.isChecked = false
        switchgoPayCheckout?.visibility = View.GONE
        tabLayout.visibility = View.GONE
        paymentLayout.visibility = View.GONE
        tapHeaderSectionView.visibility = View.GONE
        amountSectionView.visibility = View.GONE
        switchDemo.visibility = View.GONE
        mainSwitch.visibility = View.GONE
        cardSwitch.visibility = View.GONE
        tabLayout.visibility = View.GONE
        paymentLayout.visibility = View.GONE
        businessIcon.visibility = View.GONE
        businessPlaceholder.visibility = View.GONE
        amountSectionView.visibility = View.GONE
        switchDemo.visibility = View.GONE
        separatorView?.visibility = View.GONE
        chipRecycler.visibility = View.GONE
        fragment_container_nfc.visibility = View.GONE
//       selectedCurrency.visibility= View.GONE
        nfcButton?.visibility = View.GONE
        cardScannerBtn?.visibility = View.GONE
        tap_payment_input0?.visibility = View.GONE
        switchSaveDemo?.visibility = View.GONE
        savegoPay?.visibility = View.GONE
        alertgoPay?.visibility = View.GONE
        saveCardorMobile?.visibility = View.GONE
        chipRecycler.visibility = View.GONE
        switchSaveDemo?.visibility = View.GONE
        savegoPay?.visibility = View.GONE
        alertgoPay?.visibility = View.GONE
        saveCardorMobile?.visibility = View.GONE
        headerView?.visibility = View.GONE
        separator.visibility = View.GONE
        groupAction?.visibility = View.GONE
        groupName?.visibility = View.GONE
        separator_.visibility = View.GONE
        topSeparator.visibility = View.GONE
        separatorــ.visibility = View.GONE
    }


    fun setAllSeparatorTheme() {
        val separatorViewTheme = SeparatorViewTheme()
        separatorViewTheme.strokeColor =
            Color.parseColor(ThemeManager.getValue("tapSeparationLine.backgroundColor"))
        separatorViewTheme.strokeHeight = ThemeManager.getValue("tapSeparationLine.height")
        indicatorSeparator.setTheme(separatorViewTheme)
        topSeparator.setTheme(separatorViewTheme)
        separator.setTheme(separatorViewTheme)
        separator_.setTheme(separatorViewTheme)
        separatorــ.setTheme(separatorViewTheme)
    }

    fun setTapMobileInputViewTheme() {
        val editTextTheme = EditTextTheme()
        editTextTheme.backgroundTint =
            Color.parseColor(ThemeManager.getValue("phoneCard.commonAttributes.backgroundColor"))
        editTextTheme.textColorHint =
            Color.parseColor(ThemeManager.getValue("phoneCard.textFields.placeHolderColor"))
        editTextTheme.textSize = ThemeManager.getFontSize("phoneCard.textFields.font")
        tapMobileInputView.setTheme(editTextTheme)
        tapMobileInputView.mobileNumber.setBackgroundColor(Color.parseColor(ThemeManager.getValue("phoneCard.commonAttributes.backgroundColor")))
        tapMobileInputView.mobilePaymentMainLinear.setBackgroundColor(
            Color.parseColor(
                ThemeManager.getValue(
                    "phoneCard.commonAttributes.backgroundColor"
                )
            )
        )
        tapMobileInputView.mobileImage.setBackgroundColor(Color.parseColor(ThemeManager.getValue("phoneCard.commonAttributes.backgroundColor")))
    }

    override fun onEnterValidCardNumberActionListener() {
        TODO("Not yet implemented")
    }

    override fun onEnterValidPhoneNumberActionListener() {
        TODO("Not yet implemented")
    }

    override fun onSelectPaymentOptionActionListener() {
        TODO("Not yet implemented")
    }

    override fun onStateChanged(state: ActionButtonState) {

    }

//    override fun onClickActionButton() {
//        if (actionButton.isActivated) {
//            changeBottomSheetTransition()
//            hideAllViews()
//            if (paymentsList[2] == 3) {
//                Toast.makeText(context, "goPay is clicked", Toast.LENGTH_SHORT).show()
//                goPayLoginInput.visibility= View.VISIBLE
//                goPayPassword.visibility= View.VISIBLE
////                        childFragmentManager
////                            .beginTransaction()
////                            .remove(WebFragment(this))
////                            .commit()
////                        dialog?.hide()
//                changeBottomSheetTransition()
//            } else
//                switch_pay_demo.payButton.addChildView(
//                    switch_pay_demo.payButton.getImageView(
//                        R.drawable.loader,
//                        1
//                    ) { replaceBetweenFragments() })
//        }
//    }

    override fun showHideClearImage(show: Boolean) {
        if (show) {
            clearView?.visibility = View.VISIBLE
        } else {
            clearView?.visibility = View.VISIBLE
        }
    }

    override fun onChangeClicked() {

//        AnimationEngine.applyTransition(bottomSheet, SLIDE)
        goPayLoginInput?.visibility = View.VISIBLE
        goPayPasswordInput?.visibility = View.GONE
        otpView?.visibility = View.GONE
    }

    override fun onEmailValidated() {
//        AnimationEngine.applyTransition(bottomSheet, SLIDE)
        goPayLoginInput?.visibility = View.GONE
        otpView?.visibility = View.GONE
        otpView?.changePhoneCardView?.visibility = View.GONE
        goPayPasswordInput?.visibility = View.VISIBLE
        goPayPasswordInput?.setLoginInterface(this, goPayLoginInput?.textInput?.text.toString())
    }

    override fun onPhoneValidated() {
//        AnimationEngine.applyTransition(bottomSheet, SLIDE)
//        activity?.supportFragmentManager?.let { OTPFragment().show(it,null) }
        goPayPasswordInput?.visibility = View.GONE
        goPayLoginInput?.visibility = View.GONE
        otpView?.visibility = View.VISIBLE
        otpView?.changePhoneCardView?.visibility = View.VISIBLE

//        val blurredBitmap: Bitmap? = otpView?.otpLinearLayout?.let { BlurBuilder.blur(it) }
//        otpView?.otpLinearLayout?.background = BitmapDrawable(resources, blurredBitmap)

    }


    @SuppressLint("SetTextI18n")
    override fun getPhoneNumber(phoneNumber: String, countryCode: String, maskedValue: String) {
        otpView?.mobileNumberText?.text = "+${countryCode.replace("+", " ")} $maskedValue"
        Log.d("countrycode", countryCode)
        Log.d("countrycode......", countryCode.replace("+", " "))
    }

    override fun onChangePhoneClicked() {
        goPayLoginInput?.visibility = View.VISIBLE
        goPayLoginInput?.changeDataSource(GoPayLoginDataSource())
        goPayLoginInput?.inputType = GoPayLoginMethod.EMAIL
        otpView?.visibility = View.GONE
        otpView?.changePhoneCardView?.visibility = View.GONE

    }

    override fun onOtpButtonConfirmationClick(otpNumber: String): Boolean {
        Log.d("isValidOTP", (otpNumber == "111111").toString())
        return otpNumber == "111111"
    }


}

