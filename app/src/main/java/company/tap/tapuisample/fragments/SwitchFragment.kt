package company.tap.tapuisample.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import company.tap.tapuilibrary.atoms.TapEditText
import company.tap.tapuilibrary.atoms.TapSwitch
import company.tap.tapuilibrary.atoms.TapTextView
import company.tap.tapuilibrary.datasource.TapSwitchDataSource
import company.tap.tapuilibrary.interfaces.TapSwitchInterface
import company.tap.tapuilibrary.views.TapBottomSheetDialog
import company.tap.tapuilibrary.views.TapCardSwitch
import company.tap.tapuisample.R


/**
 * Created by AhlaamK on 6/30/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
open class BottomSheetSwitch : TapBottomSheetDialog() {
    private lateinit var switchDemo: TapCardSwitch
    private lateinit var demoButton: TapTextView
    private lateinit var editText: TapEditText
    private var tapSwitchInterface: TapSwitchInterface? = null
    private var switchSaveDemo: TapSwitch? = null
    private var switchLayout: LinearLayout? = null
    private var switchMerchantCheckout: TapSwitch? = null
    private var switchgoPayCheckout: TapSwitch? = null
    private var savegoPay: TapTextView? = null
    private var alertgoPay: TapTextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.custom_sheet_switch, container, false)
        initViews(view)
        return view

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            tapSwitchInterface = context as TapSwitchInterface
        } catch (ex: ClassCastException) {
            try {
                tapSwitchInterface = parentFragment as TapSwitchInterface
            } catch (ignore: Exception) {
            }
        }
    }

    // Initializing the views
    private fun initViews(view: View) {
        switchDemo = view.findViewById(R.id.switch_pay_demo)
        demoButton = view.findViewById(R.id.demo_button)
        editText = view.findViewById(R.id.demo_edit)
        switchDemo.setSwitchDataSource(getSwitchDataSource())
        switchSaveDemo = switchDemo.findViewById(R.id.switch_save_mobile)
        switchLayout = switchDemo.findViewById(R.id.switches_layout)
        switchMerchantCheckout = switchDemo.findViewById(R.id.switch_merchant_checkout)
        switchgoPayCheckout = switchDemo.findViewById(R.id.switch_gopay_checkout)
        savegoPay = switchDemo.findViewById(R.id.save_goPay)
        alertgoPay = switchDemo.findViewById(R.id.alert_gopay_signup)

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                switchSaveDemo?.visibility = View.VISIBLE
            }

            override fun afterTextChanged(s: Editable) {
                if (s.length == 8) {
                    editText.hideKeyboard()
                    switchSaveDemo?.isChecked = true
                    switchLayout?.visibility = View.VISIBLE

                }
            }
        })

        configureSwitch()

    }

    // Configuring switch states and listening to switch states.
    private fun configureSwitch() {
        val slideUp: Animation = AnimationUtils.loadAnimation(context, R.anim.slide_up)
        val slideDown: Animation = AnimationUtils.loadAnimation(context, R.anim.slide_down)
        val FadeOut: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
        switchSaveDemo?.setOnCheckedChangeListener { buttonView, isChecked ->
            println("isChecked Save value $isChecked")
            tapSwitchInterface?.enableSaveMobile(isChecked)

            if (isChecked) {
                switchLayout?.visibility = View.VISIBLE
                switchMerchantCheckout?.isChecked = true
                switchgoPayCheckout?.isChecked = true
                switchLayout?.startAnimation(slideUp)

            } else {
                switchLayout?.visibility = View.GONE
                switchLayout?.startAnimation(FadeOut)
            }
        }
        switchMerchantCheckout?.setOnCheckedChangeListener { buttonView, isChecked ->
            tapSwitchInterface?.enableSaveMerchantCheckout(isChecked)
            if (isChecked) {
                switchMerchantCheckout?.visibility = View.VISIBLE
                switchMerchantCheckout?.isChecked = true
                switchMerchantCheckout?.startAnimation(slideUp)
            } else {
                switchMerchantCheckout?.visibility = View.GONE
                switchMerchantCheckout?.startAnimation(FadeOut)
            }
        }
        switchgoPayCheckout?.setOnCheckedChangeListener { buttonView, isChecked ->
            tapSwitchInterface?.enableSavegoPayCheckout(isChecked)
            if (isChecked) {
                switchgoPayCheckout?.visibility = View.VISIBLE
                savegoPay?.visibility = View.VISIBLE
                alertgoPay?.visibility = View.VISIBLE
                switchgoPayCheckout?.isChecked = true
                switchgoPayCheckout?.startAnimation(slideUp)
            } else {
                switchgoPayCheckout?.visibility = View.GONE
                savegoPay?.visibility = View.GONE
                alertgoPay?.visibility = View.GONE
                switchgoPayCheckout?.startAnimation(Fadein)
            }
        }
    }

    //Setting data to TapSwitchDataSource
    private fun getSwitchDataSource(): TapSwitchDataSource {
        return TapSwitchDataSource(
            switchSaveMobile = "For faster and easier checkout,save your mobile number.",
            switchSaveMerchantCheckout = "Save for [merchant_name] Checkouts",
            switchSavegoPayCheckout = "By enabling goPay, your mobile number will be saved with Tap Payments to get faster and more secure checkouts in multiple apps and websites.",
            savegoPayText = "Save for goPay Checkouts",
            alertgoPaySignup = "Please check your email or SMS’s in order to complete the goPay Checkout signup process."

        )

    }

    //AutoHide keyboard
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

}