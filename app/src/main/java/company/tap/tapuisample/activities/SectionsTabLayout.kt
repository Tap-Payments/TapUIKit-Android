package company.tap.tapuisample.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import company.tap.cardinputwidget.widget.inline.InlineCardInput
import company.tap.tapcardvalidator_android.CardBrand
import company.tap.tapuilibrary.animation.AnimationEngine
import company.tap.tapuilibrary.models.SectionTabItem
import company.tap.tapuilibrary.interfaces.TapSelectionTabLayoutInterface
import company.tap.tapuilibrary.views.TapMobilePaymentView
import company.tap.tapuilibrary.views.TapSelectionTabLayout
import company.tap.tapuisample.R
import kotlinx.android.synthetic.main.activity_sections_tab_layout.*

/**
 * Sample Activity to show how TableLayout functions .
 *
 * **/
class SectionsTabLayout : AppCompatActivity(), TapSelectionTabLayoutInterface {

    lateinit var tabLayout: TapSelectionTabLayout
    private var selectedTab = 0
    private var isMobileTabAdded = false
    private lateinit var tapCardInputView: InlineCardInput
    private lateinit var tapMobileInputView: TapMobilePaymentView
    private val tab1Items = arrayOf("VISA", "MASTERCARD", "AMEX")
    private val tab2Items = arrayOf("Zain PAY", "Ooredoo PAY")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sections_tab_layout)
        tabLayout = findViewById(R.id.sections_tablayout)
        tabLayout.setTabLayoutInterface(this)
        tapMobileInputView = TapMobilePaymentView(this, null)
        tapCardInputView = InlineCardInput(this)
        tapCardInputView.holderNameEnabled = false
        addCardsTab()
        addMobileTab()
    }

    private fun addCardsTab() {
        val items = ArrayList<SectionTabItem>()
        items.add(
            SectionTabItem(
                resources.getDrawable(
                    R.drawable.visa
                ), resources.getDrawable(R.drawable.visa_gray), CardBrand.visa
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

    fun selectTab(view: View) {
        var alert: AlertDialog? = null
        var items = tab1Items
        if (selectedTab == 1)
            items = tab2Items
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle("Select Item")
        builder.setItems(items) { _, position ->
            tabLayout.selectTab(CardBrand.fromString(items[position]))
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
            AnimationEngine.applyTransition(payment_input_layout)
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

}
