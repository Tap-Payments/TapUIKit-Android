package company.tap.tapuisample.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import company.tap.tapanimationkit.AnimateView
import company.tap.tapanimationkit.RenderAnimate
import company.tap.tapanimationkit.viewsanim.Fade
import company.tap.tapuilibrary.atoms.TapSeparatorView
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
open class SwitchFragment : TapBottomSheetDialog() {
    private lateinit var switchDemo: TapCardSwitch
    private lateinit var demoText: TapTextView
    private var tapSwitchInterface: TapSwitchInterface? = null
    private var switchSaveDemo: TapSwitch? = null
    private var switchLayout: LinearLayout? = null
    private var switchMerchantCheckout: TapSwitch? = null
    private var switchgoPayCheckout: TapSwitch? = null
    private var savegoPay: TapTextView? = null
    private var alertgoPay: TapTextView? = null
    private var saveCardorMobile: TapTextView? = null
    private var separatorView: TapSeparatorView? = null
    private var radioGroup: RadioGroup? = null
    private lateinit var radio: RadioButton
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
        demoText = view.findViewById(R.id.demo_text)
        switchDemo.setSwitchDataSource(getSwitchDataSource())
        switchSaveDemo = switchDemo.findViewById(R.id.switch_save_mobile)
        switchLayout = switchDemo.findViewById(R.id.switches_layout)
        separatorView = switchDemo.findViewById(R.id.switch_separator)
        switchMerchantCheckout = switchDemo.findViewById(R.id.switch_merchant_checkout)
        switchgoPayCheckout = switchDemo.findViewById(R.id.switch_gopay_checkout)
        saveCardorMobile = switchDemo.findViewById(R.id.text_save)
        savegoPay = switchDemo.findViewById(R.id.save_goPay)
        alertgoPay = switchDemo.findViewById(R.id.alert_gopay_signup)
        radioGroup = view.findViewById(R.id.radio_group)
        radioGroup?.setOnCheckedChangeListener { group, checkedId ->
            radio = view.findViewById(checkedId)
            println("raio id ${radio.id}")

            if (radio.id == 1) {

                bottomSheetLayout?.let {

                    layout ->
                    val removeTransition: Transition =
                        TransitionInflater.from(context)
                            .inflateTransition(R.transition.remove_fragment)
                    TransitionManager.beginDelayedTransition(layout, removeTransition)
                switchMerchantCheckout?.visibility = View.VISIBLE
                switchMerchantCheckout?.isChecked = true
                    switchgoPayCheckout?.visibility = View.GONE
                    savegoPay?.visibility = View.GONE
                    alertgoPay?.visibility = View.GONE
                    separatorView?.visibility = View.GONE

            }



            } else if (radio.id == 2) {
                bottomSheetLayout?.let { layout ->
                    val removeTransition: Transition =
                        TransitionInflater.from(context)
                            .inflateTransition(R.transition.remove_fragment)
                    TransitionManager.beginDelayedTransition(layout, removeTransition)
                    switchMerchantCheckout?.visibility = View.GONE
                    switchMerchantCheckout?.isChecked = true
                    switchgoPayCheckout?.visibility = View.VISIBLE
                    savegoPay?.visibility = View.VISIBLE
                    alertgoPay?.visibility = View.VISIBLE
                    separatorView?.visibility = View.GONE

                }

            } else {

         
             bottomSheetLayout?.let { layout ->
                    val removeTransition: Transition =
                        TransitionInflater.from(context)
                            .inflateTransition(R.transition.add_fragment)
                    TransitionManager.beginDelayedTransition(layout, removeTransition)
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


        }

        configureSwitch()

    }

    // Configuring switch states and listening to switch states.
    private fun configureSwitch() {

        switchSaveDemo?.setOnCheckedChangeListener { buttonView, isChecked ->
            println("isChecked Save value $isChecked")
            tapSwitchInterface?.enableSaveMobile(isChecked)
            if(isChecked){
                switchLayout?.visibility = View.VISIBLE
                switchMerchantCheckout?.visibility = View.VISIBLE
                switchMerchantCheckout?.isChecked = true
                switchgoPayCheckout?.isChecked = true
                switchgoPayCheckout?.visibility = View.VISIBLE
                savegoPay?.visibility = View.VISIBLE
                alertgoPay?.visibility = View.VISIBLE
                separatorView?.visibility = View.VISIBLE
            }else{
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
            tapSwitchInterface?.enableSaveMerchantCheckout(isChecked)
        }
        switchgoPayCheckout?.setOnCheckedChangeListener { buttonView, isChecked ->
            tapSwitchInterface?.enableSavegoPayCheckout(isChecked)

        }
    }

    //Setting data to TapSwitchDataSource
    private fun getSwitchDataSource(): TapSwitchDataSource {
        return TapSwitchDataSource(
            switchSave = "For faster and easier checkout,\n use card scanner or NFC.",
            switchSaveMerchantCheckout = "Save for [merchant_name] Checkouts",
            switchSavegoPayCheckout = "By enabling goPay, your mobile number will be saved with Tap Payments to get faster and more secure checkouts in multiple apps and websites.",
            savegoPayText = "Save for goPay Checkouts",
            alertgoPaySignup = "Please check your email or SMS’s in order to complete the goPay Checkout signup process."

        )

    }


}