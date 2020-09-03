package company.tap.tapuisample.activities

import android.annotation.SuppressLint
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.fontskit.enums.TapFont
import company.tap.tapuilibrary.uikit.atoms.TapTextView
import company.tap.tapuilibrary.uikit.datasource.ItemViewDataSource
import company.tap.tapuilibrary.uikit.datasource.TapSwitchDataSource
import company.tap.tapuilibrary.uikit.utils.BaseActivity
import company.tap.tapuilibrary.uikit.views.TapCardSwitch
import company.tap.tapuilibrary.uikit.views.TapListItemView
import company.tap.tapuisample.R
import kotlinx.android.synthetic.main.activity_amount_item.*
/**
 * Sample Activity to show how Amount and Item Description will be shown.
 * **/
class AmountItemActivity : BaseActivity() {

    private lateinit var descrTxt: TapTextView
    private lateinit var totalAmount: TapTextView
    private lateinit var totalQuantity: TapTextView
    private lateinit var discount: TapTextView
    private lateinit var amountListItem: TapListItemView
    private lateinit var cardswitch: TapCardSwitch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amount_item)
        initializeViews()
    }

    //Initailized Views
    @SuppressLint("SetTextI18n")
    private fun initializeViews() {
        amountListItem = findViewById(R.id.amount_items)
        amountListItem.setItemViewDataSource(getItemViewdataSource())
        descrTxt = findViewById(R.id.show_description)
        totalAmount = findViewById(R.id.total_amount)
        totalQuantity = findViewById(R.id.total_quantity)
        discount = findViewById(R.id.discount_text)
        cardswitch = findViewById(R.id.pay_card_switch)
        cardswitch.setSwitchDataSource(getSwitchDataSource())
        descrTxt.typeface = Typeface.createFromAsset(this.assets, TapFont.tapFontType(
            TapFont.RobotoLight))
        totalAmount.typeface = Typeface.createFromAsset(this.assets, TapFont.tapFontType(
            TapFont.RobotoRegular))
        discount.typeface = Typeface.createFromAsset(this.assets, TapFont.tapFontType(
            TapFont.RobotoLight))
        descrTxt.text = LocalizationManager.getValue("showDesc","ItemList")
        discount.text = LocalizationManager.getValue("Discount","ItemList")
        discount.visibility = View.INVISIBLE

        switch_discount.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                discount.visibility = View.VISIBLE
                totalAmount.paintFlags =
                    totalAmount.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                discount.visibility = View.INVISIBLE
                totalAmount.paintFlags =
                    totalAmount.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

            }
        }
    }

    //Set data to TapSwitchDataSource
    private fun getSwitchDataSource(): TapSwitchDataSource {
        return TapSwitchDataSource(
            // switchSave =LocalizationManager.getValue("cardSaveLabel","TapCardInputKit"),
            switchSaveMerchantCheckout = "Save for [merchant_name] Checkouts",
            switchSavegoPayCheckout = "By enabling goPay, your mobile number will be saved with Tap Payments to get faster and more secure checkouts in multiple apps and websites.",
            savegoPayText = "Save for goPay Checkouts",
            alertgoPaySignup = "Please check your email or SMS’s in order to complete the goPay Checkout signup process."
        )

    }

    //Set data to ItemViewDataSource
    private fun getItemViewdataSource(): ItemViewDataSource {
        return ItemViewDataSource(
            itemTitle = "ITEM TITLE",
            itemAmount = "KD000,000.000",
            totalQuantity = "1",
            totalAmount = "KD000,000.000"
        )

    }

}
