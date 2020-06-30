package company.tap.tapuisample.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
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
open class BottomSheetSwitch:TapBottomSheetDialog() {
    private lateinit var switchDemo:TapCardSwitch
    private lateinit var demoButton:TapTextView
    private lateinit var editText: TapEditText
    private var tapSwitchInterface: TapSwitchInterface? = null
    private var switchSaveDemo: TapSwitch? = null
    private var switchLayout: LinearLayout? = null

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

        private fun initViews(view: View) {
            switchDemo = view.findViewById(R.id.switch_pay_demo)
            demoButton = view.findViewById(R.id.demo_button)
            editText = view.findViewById(R.id.demo_edit)
            switchDemo.setSwitchDataSource(getSwitchDataSource())
            switchSaveDemo = switchDemo.findViewById(R.id.switch_save)
            switchLayout = switchDemo.findViewById(R.id.switches_layout)
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    switchSaveDemo?.visibility = View.VISIBLE
                }

                override fun afterTextChanged(s: Editable) {
                    if(s.length==8){
                        editText.hideKeyboard()
                        switchSaveDemo?.isChecked= true
                        switchLayout?.visibility = View.VISIBLE

                    }
                }
            })
            switchSaveDemo?.setOnCheckedChangeListener { buttonView, isChecked ->
              println("isChecked Save value $isChecked")
            if(isChecked){
                switchLayout?.visibility = View.VISIBLE
            }else switchLayout?.visibility = View.INVISIBLE
            }


    }

    private fun getSwitchDataSource(): TapSwitchDataSource {
        return TapSwitchDataSource (
            switchSave ="For faster and easier checkout,save your mobile number.",
            switchMerchantCheckout = "Save for [merchant_name] Checkouts",
            switchgoPayMobile =  "By enabling goPay, your mobile number will be saved with Tap Payments to get faster and more secure checkouts in multiple apps and websites.",
            savegoPayText = "Save for goPay Checkouts",
            alertgoPaySignup = "Please check your email or SMS’s in order to complete the goPay Checkout signup process."

        )

    }
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

}