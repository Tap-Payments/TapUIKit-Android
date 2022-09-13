package company.tap.tapuisample.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.LinearLayout
import company.tap.tapuilibrary.uikit.atoms.TapSwitch
import company.tap.tapuilibrary.uikit.atoms.TapTextView
import company.tap.tapuilibrary.uikit.datasource.LoyaltyHeaderDataSource
import company.tap.tapuilibrary.uikit.organisms.TapLoyaltyView
import company.tap.tapuisample.R

class LoyaltyViewActivity : AppCompatActivity() {
    var loyaltyView: TapLoyaltyView?=null
    var textViewClickable: TapTextView ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loyalty_view)
        loyaltyView = findViewById(R.id.loyaltyView)
        textViewClickable = loyaltyView?.findViewById(R.id.textViewClickable)!!
        textViewClickable?.linksClickable

        loyaltyView?.setLoyaltyHeaderDataSource(LoyaltyHeaderDataSource("ADCB","https://is4-ssl.mzstatic.com/image/thumb/Purple112/v4/05/33/67/05336718-a6f6-8ca1-1ea0-0644f5071ce9/AppIcon-0-0-1x_U007emarketing-0-0-0-5-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/1200x600wa.png"))
    }
}