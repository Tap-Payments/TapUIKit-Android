package company.tap.tapuisample.activities

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import company.tap.tapuilibrary.atoms.TapTextView
import company.tap.tapuisample.R
import kotlinx.android.synthetic.main.activity_amount_item.*
/**
 * Sample Activity to show how Amount and Item Description will be shown.
 * **/
class AmountItemActivity : AppCompatActivity() {
    lateinit var itemTitleText: TapTextView
    lateinit var itemAmountText: TapTextView
    lateinit var showDescrpText: TapTextView
    lateinit var totalAmountText: TapTextView
    lateinit var totalQuantityText: TapTextView
    lateinit var discountText: TapTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amount_item)
        initializeViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initializeViews() {
        itemTitleText = findViewById(R.id.item_title)
        itemAmountText = findViewById(R.id.item_amount)
        showDescrpText = findViewById(R.id.show_description)
        totalAmountText = findViewById(R.id.total_amount)
        totalQuantityText = findViewById(R.id.total_quantity)
        discountText = findViewById(R.id.discount_text)
        itemTitleText.text = "ITEM TITLE"
        itemAmountText.text = "KD000,000.000"
        showDescrpText.text = "Show Description"
        totalAmountText.text = "KD000,000.000"
        totalQuantityText.text = "1"
        discountText.text = "10% Discount"
        discountText.visibility = View.INVISIBLE

        switch_discount.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                discountText.visibility = View.VISIBLE
                totalAmountText.paintFlags =
                    totalAmountText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                discountText.visibility = View.INVISIBLE
                totalAmountText.paintFlags =
                    totalAmountText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

            }
        }
    }
}
