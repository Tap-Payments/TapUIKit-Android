package company.tap.tapuisample.activities

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.uikit.atoms.TapTextViewNew
import company.tap.tapuilibrary.uikit.datasource.LoyaltyHeaderDataSource
import company.tap.tapuilibrary.uikit.ktx.makeLinks
import company.tap.tapuilibrary.uikit.organisms.TapLoyaltyView
import company.tap.tapuisample.R

class LoyaltyViewActivity : AppCompatActivity() {
    var loyaltyView: TapLoyaltyView? = null
    var constraintt: ConstraintLayout? = null
    var textViewClickable: TapTextViewNew? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loyalty_view)
        loyaltyView = findViewById(R.id.loyaltyView)
       // textViewClickable = loyaltyView?.findViewById(R.id.textViewClickable)!!
      //  textViewClickable?.linksClickable
//        loyaltyView?.linearLayout2?.visibility = View.GONE
//        loyaltyView?.linearLayout3?.visibility = View.GONE
//        loyaltyView?.textViewSubTitle?.text = "Amount to Redeem "
//        loyaltyView?.textViewTouchPoints?.text = "= TouchPoints "

        constraintt = findViewById(R.id.constraintt)
        loyaltyView?.switchLoyalty?.setOnCheckedChangeListener { buttonView, isChecked ->
//            loyaltyView?.switchTheme()
//
//            if (isChecked) {
//                loyaltyView?.linearLayout2?.visibility = View.VISIBLE
//                loyaltyView?.linearLayout3?.visibility = View.VISIBLE
//
//            } else {
//
//                loyaltyView?.linearLayout2?.visibility = View.GONE
//                loyaltyView?.linearLayout3?.visibility = View.GONE
//
//            }
        }
        constraintt?.setBackgroundColor(Color.parseColor(ThemeManager.getValue("amountSectionView.backgroundColor")))
        loyaltyView?.setLoyaltyHeaderDataSource(
            LoyaltyHeaderDataSource(
                "ADCB",
                "https://user-images.githubusercontent.com/57221514/191747750-544516b5-94d1-4b00-86d5-916f3da9f55a.png"
            )
        )

//        loyaltyView?.textViewClickable?.makeLinks(
//            Pair("(T&C)", View.OnClickListener {
//                val url = "https://www.adcb.com/en/tools-resources/adcb-privacy-policy/"
//                val intent = Intent(Intent.ACTION_VIEW)
//                intent.data = Uri.parse(url)
//                startActivity(intent)
//                Toast.makeText(this, "Terms of Service Clicked", Toast.LENGTH_SHORT).show()
//            })
//        )

//        loyaltyView?.editTextAmount?.doOnTextChanged { text, start, count, after ->
//            // action which will be invoked when the text is changing
//            if (text.toString() == "340") {
//                loyaltyView?.loyaltyAlertView?.alertMessage?.text =
//                    "Minimum redemption is AED 20.00 "
//                loyaltyView?.loyaltyAlertView?.visibility = View.VISIBLE
//            } else {
//                loyaltyView?.loyaltyAlertView?.visibility = View.GONE
//            }
//        }
//
//        loyaltyView?.textViewRemainAmount?.text = "Remain amount "
//        loyaltyView?.textViewRemainPoints?.text = "Remain points "

    }


}