package company.tap.tapuisample.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import company.tap.tapcardvalidator_android.CardBrand
import company.tap.tapuilibrary.SectionTabItem
import company.tap.tapuilibrary.views.TapSelectionTabLayout
import company.tap.tapuisample.R

/**
 * Sample Activity to show how TableLayout functions .
 *
 * **/
class SectionsTabLayout : AppCompatActivity() {

    lateinit var tabLayout: TapSelectionTabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sections_tab_layout)
        tabLayout = findViewById(R.id.sections_tablayout)
        addCardsTab()
        addMobileTab()
    }

    private fun addCardsTab() {
        val items = ArrayList<SectionTabItem>()
        items.add(SectionTabItem(resources.getDrawable(R.drawable.visa), resources.getDrawable(R.drawable.visa_gray), CardBrand.visa))
        items.add(SectionTabItem(resources.getDrawable(R.drawable.mastercard), resources.getDrawable(R.drawable.mastercard_gray), CardBrand.masterCard))
        items.add(SectionTabItem(resources.getDrawable(R.drawable.amex), resources.getDrawable(R.drawable.amex_gray), CardBrand.americanExpress))
        tabLayout.addSection(items)
    }

    private fun addMobileTab() {
        val items = ArrayList<SectionTabItem>()
        items.add(SectionTabItem(resources.getDrawable(R.drawable.zain), resources.getDrawable(R.drawable.zain_gray), CardBrand.zain))
        items.add(SectionTabItem(resources.getDrawable(R.drawable.ooredoo), resources.getDrawable(R.drawable.ooredoo_gray), CardBrand.ooredoo))
        tabLayout.addSection(items)
    }

    fun selectTab(view: View) {
        var alert: AlertDialog? = null
        val items = arrayOf("VISA", "MASTERCARD", "AMEX", "Zain PAY", "Ooredoo PAY")
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

}
