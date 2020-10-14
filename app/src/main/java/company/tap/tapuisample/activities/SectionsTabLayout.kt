package company.tap.tapuisample.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import company.tap.cardinputwidget.widget.inline.InlineCardInput
import company.tap.tapcardvalidator_android.CardBrand
import company.tap.tapcardvalidator_android.CardValidationState
import company.tap.tapcardvalidator_android.CardValidator
import company.tap.tapuilibrary.uikit.animation.AnimationEngine
import company.tap.tapuilibrary.uikit.enums.TabSectionType
import company.tap.tapuilibrary.uikit.interfaces.TapPaymentShowHideClearImage
import company.tap.tapuilibrary.uikit.models.SectionTabItem
import company.tap.tapuilibrary.uikit.interfaces.TapSelectionTabLayoutInterface
import company.tap.tapuilibrary.uikit.models.TabSection
import company.tap.tapuilibrary.uikit.organisms.TapPaymentInput
import company.tap.tapuilibrary.uikit.views.TapMobilePaymentView
import company.tap.tapuilibrary.uikit.views.TapSelectionTabLayout
import company.tap.tapuisample.R
import kotlinx.android.synthetic.main.activity_sections_tab_layout.*

/**
 * Sample Activity to show how TableLayout functions .
 *
 * **/
class SectionsTabLayout : AppCompatActivity(),
    TapSelectionTabLayoutInterface , TapPaymentShowHideClearImage {

    lateinit var tabLayout: TapSelectionTabLayout
    lateinit var paymentInput: TapPaymentInput

    private var mobileNumberEditText: EditText? = null

    private var selectedTab = 0
    private lateinit var tapCardInputView: InlineCardInput
    private lateinit var tapMobileInputView: TapMobilePaymentView
    private val tab1Items = arrayOf("VISA", "MASTERCARD", "AMEX")
    private val tab2Items = arrayOf("Zain PAY", "Ooredoo PAY")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sections_tab_layout)
        paymentInput = findViewById(R.id.tap_payment_input)
        tabLayout = findViewById(R.id.sections_tablayout)
        tabLayout.setTabLayoutInterface(this)
        tapMobileInputView = TapMobilePaymentView(this, null)
        tapMobileInputView.setTapPaymentShowHideClearImage(this)
        tapCardInputView = InlineCardInput(this)
        tapCardInputView.holderNameEnabled = false
        tabLayout.addSection(getCardList())
        tabLayout.addSection(getMobileList())
        setupBrandDetection()


        paymentInput.addTabLayoutSection(
            TabSection(
                TabSectionType.CARD,
                getCardList()
            ),
            TabSection(
                TabSectionType.MOBILE,
                getMobileList()
            )
        )
    }

    private fun setupBrandDetection() {
        tapCardInputView.setCardNumberTextWatcher(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    tabLayout.resetBehaviour()
                }
                val card = CardValidator.validate(s.toString())
                if (card.cardBrand != null)
                    tabLayout.selectTab(card.cardBrand, card.validationState == CardValidationState.valid)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun getCardList(): ArrayList<SectionTabItem>{
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
        return items
    }

    private fun getMobileList(): ArrayList<SectionTabItem>{
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
        return items
    }

    fun selectTab(view: View) {
        var alert: AlertDialog? = null
        var items = tab1Items
        if (selectedTab == 1)
            items = tab2Items
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle("Select Item")
        builder.setItems(items) { _, position ->
            tabLayout.selectTab(CardBrand.fromString(items[position]), true)
            alert?.hide()
        }
        alert = builder.create()
        alert.show()
    }

    fun resetSelection(view: View) {
        tabLayout.resetBehaviour()

    }

    override fun onTabSelected(position: Int?) {
        position?.let {
            selectedTab = it
//            AnimationEngine.applyTransition(payment_input_layout)
            payment_input_layout.removeAllViews()
            if (position == 0)
                payment_input_layout.addView(tapCardInputView)
            else
                payment_input_layout.addView(tapMobileInputView)
        }
    }

    fun selectSegment(view: View) {
        var alert: AlertDialog? = null
        val items = arrayOf("1", "2")
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle("Select Section")
        builder.setItems(items) { _, position ->
            tabLayout.selectSection(position)
            alert?.hide()
        }
        alert = builder.create()
        alert.show()
    }

    override fun showHideClearImage(show: Boolean) {
        if (show){
            paymentInput.clearView.visibility = View.VISIBLE

        }else{
            paymentInput.clearView.visibility = View.GONE

        }
    }

}
