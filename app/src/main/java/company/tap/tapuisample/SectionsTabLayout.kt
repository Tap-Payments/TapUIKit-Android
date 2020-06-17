package company.tap.tapuisample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import company.tap.tapuilibrary.PaymentSectionItemType
import company.tap.tapuilibrary.views.TapSelectionTabLayout


class SectionsTabLayout : AppCompatActivity() {

    lateinit var tabLayout: TapSelectionTabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sections_tab_layout)
        tabLayout = findViewById(R.id.sections_tablayout)
    }

    fun selectTab(view: View) {
        var alert: AlertDialog? = null
        val items = arrayOf("VISA", "MASTERCARD", "AMEX", "ZAIN", "OOREDOO")
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle("Select Item")
        builder.setItems(items) { _, position ->
            tabLayout.selectItem(PaymentSectionItemType.valueOf(items[position]))
            alert?.hide()
        }
        alert = builder.create()
        alert.show()
    }

    fun resetSelection(view: View) {
        tabLayout.resetSelection()
    }

}
