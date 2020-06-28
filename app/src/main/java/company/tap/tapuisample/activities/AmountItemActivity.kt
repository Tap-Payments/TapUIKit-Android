package company.tap.tapuisample.activities

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.atoms.TapTextView
import company.tap.tapuilibrary.datasource.ItemViewDataSource
import company.tap.tapuilibrary.views.TapItemsView
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
    private lateinit var amountItems: TapItemsView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amount_item)
        initializeViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initializeViews() {
        amountItems = findViewById(R.id.amount_items)
        amountItems.setItemViewDataSource(getItemViewdataSource())


        descrTxt = findViewById(R.id.show_description)
        totalAmount = findViewById(R.id.total_amount)
        totalQuantity = findViewById(R.id.total_quantity)
        discount = findViewById(R.id.discount_text)
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

    private fun getItemViewdataSource(): ItemViewDataSource {
        return ItemViewDataSource(
            itemTitle = "ITEM TITLE",
            itemAmount = "KD000,000.000",
            totalQuantity = "1",
            totalAmount = "KD000,000.000"
        )

    }

}
