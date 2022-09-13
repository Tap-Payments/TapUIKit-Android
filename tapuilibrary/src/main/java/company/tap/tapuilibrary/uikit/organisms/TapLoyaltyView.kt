package company.tap.tapuilibrary.uikit.organisms

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.uikit.atoms.*
import company.tap.tapuilibrary.uikit.datasource.HeaderDataSource
import company.tap.tapuilibrary.uikit.datasource.LoyaltyHeaderDataSource
import company.tap.tapuilibrary.uikit.ktx.setBorderedView
import company.tap.tapuilibrary.uikit.views.TapMobilePaymentView
import company.tap.tapuilibrary.uikit.views.TextDrawable

class TapLoyaltyView (context: Context?, attrs: AttributeSet?) :
LinearLayout(context, attrs){
    val constraintLayout by lazy { findViewById<LinearLayout>(R.id.constraintLayout) }
    val cardViewOut by lazy { findViewById<CardView>(R.id.cardViewOut) }
    val mainChip by lazy { findViewById<TapChip>(R.id.mainChip) }
    val iconBank by lazy { findViewById<TapImageView>(R.id.iconBank) }
    val textViewTitle by lazy { findViewById<TapTextView>(R.id.textViewTitle) }
    val textViewClickable by lazy { findViewById<TapTextView>(R.id.textViewClickable) }
    val switchLoyalty by lazy { findViewById<TapSwitch>(R.id.switchLoyalty) }
    val textViewSubTitle by lazy { findViewById<TapTextView>(R.id.textViewSubTitle) }
    val editTextAmount by lazy { findViewById<TextInputEditText>(R.id.editTextAmount) }
    val textViewRemainPoints by lazy { findViewById<TapTextView>(R.id.textViewRemainPoints) }
    val textViewRemainAmount by lazy { findViewById<TapTextView>(R.id.textViewRemainAmount) }


    private var loyaltyHeaderDataSource: LoyaltyHeaderDataSource? = null
    init {
        inflate(context, R.layout.tap_loyalty_view, this)
        applyTheme()

    }
    private fun applyTheme() {
        cardViewOut.setBackgroundColor(Color.parseColor(ThemeManager.getValue("loyaltyView.cardView.backgroundColor")))
        cardViewOut.radius = ThemeManager.getValue("loyaltyView.cardView.radius")
        mainChip.outlineSpotShadowColor = (Color.parseColor(ThemeManager.getValue("loyaltyView.cardView.shadowColor")))
        setBorderedView(
            cardViewOut,
            25f,// corner raduis
            0.0f,
            Color.parseColor(ThemeManager.getValue("loyaltyView.cardView.shadowColor")),// stroke color
            Color.parseColor(ThemeManager.getValue("loyaltyView.cardView.backgroundColor")),// tint color
            Color.parseColor(ThemeManager.getValue("loyaltyView.cardView.backgroundColor"))
        )
        textViewClickable.setText("Balance: AED 520.00 (81,500 TouchPoints)  T&amp;C")
    }


    /**
     * @param loyaltyHeaderDataSource is set via the consumer app for bankName,
     * bankIcon.
     **/
    fun setLoyaltyHeaderDataSource(loyaltyHeaderDataSource: LoyaltyHeaderDataSource) {
        this.loyaltyHeaderDataSource = loyaltyHeaderDataSource


            Glide.with(this)
                .load(loyaltyHeaderDataSource.bankImageResources)
                .placeholder(
                    TextDrawable(
                        loyaltyHeaderDataSource.bankName?.get(0).toString()
                    )
                )
                .error(
                    TextDrawable(
                        loyaltyHeaderDataSource.bankName?.get(0).toString()
                    )
                )
                .into(iconBank)




    }

}