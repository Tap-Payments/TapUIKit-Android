package company.tap.tapuilibrary.uikit.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.themekit.theme.ButtonTheme
import company.tap.tapuilibrary.themekit.theme.TextViewTheme
import company.tap.tapuilibrary.uikit.atoms.TapButton
import company.tap.tapuilibrary.uikit.atoms.TapTextView
import company.tap.tapuilibrary.uikit.datasource.AmountViewDataSource
import kotlinx.android.synthetic.main.tap_main_amount.view.*
import kotlinx.android.synthetic.main.tap_main_amount.view.constraint
import kotlinx.android.synthetic.main.tap_main_header.view.*


class TapAmountSectionView: LinearLayout {
     private var selectedCurrency: TapTextView
     private var currentCurrency:TapTextView
     var itemCount: TapButton
  private var amountViewDataSource: AmountViewDataSource? = null
    /**
     * Simple constructor to use when creating a TapAmountSectionView from code.
     *  @param context The Context the view is running in, through which it can
     *  access the current theme, resources, etc.
     **/
    constructor(context: Context) : super(context)

    /**
     *  @param context The Context the view is running in, through which it can
     *  access the current theme, resources, etc.
     *  @param attrs The attributes of the XML Button tag being used to inflate the view.
     *
     */
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    /**
     *  @param context The Context the view is running in, through which it can
     *  access the current theme, resources, etc.
     *  @param attrs The attributes of the XML Button tag being used to inflate the view.
     * @param defStyleAttr The resource identifier of an attribute in the current theme
     * whose value is the the resource id of a style. The specified style’s
     * attribute values serve as default values for the button. Set this parameter
     * to 0 to avoid use of default values.
     */
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    init {
        inflate(
            context,
            R.layout.tap_main_amount, this
        )
        selectedCurrency = findViewById(R.id.textview_selectedcurrency)
        currentCurrency  = findViewById(R.id.textView_currentcurrency)
        itemCount = findViewById(R.id.textView_itemcount)
        itemCount.elevation = 0F
        setTheme()
    }
    fun setTheme(){
        val buttonTheme = ButtonTheme()
        buttonTheme.textColor = Color.parseColor(ThemeManager.getValue("amountSectionView.itemsLabelColor"))
        buttonTheme.borderColor = Color.parseColor(ThemeManager.getValue("amountSectionView.itemsNumberButtonBorder.color"))
        buttonTheme.backgroundColor = Color.parseColor(ThemeManager.getValue("amountSectionView.itemsNumberButtonBackgroundColor"))
        buttonTheme.cornerRadius = ThemeManager.getValue("amountSectionView.itemsNumberButtonCorner")
//        buttonTheme.borderWidth = (ThemeManager.getValue("amountSectionView.itemsNumberButtonBorder.width"))
        itemCount.setTheme(buttonTheme)
        val shapeAppearanceModel = ShapeAppearanceModel()
            .toBuilder()
            .setAllCorners(CornerFamily.ROUNDED, (ThemeManager.getValue("amountSectionView.itemsNumberButtonCorner")as Int).toFloat())
            .build()
        val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
        ViewCompat.setBackground(itemCount, shapeDrawable)

        val currentCurrencyTextViewTheme = TextViewTheme()
        currentCurrencyTextViewTheme.textColor = Color.parseColor(ThemeManager.getValue("amountSectionView.originalAmountLabelColor"))
        currentCurrencyTextViewTheme.textSize = ThemeManager.getFontSize("amountSectionView.itemsLabelFont")
        currentCurrencyTextViewTheme.font = ThemeManager.getFontName("amountSectionView.originalAmountLabelFont")
        currentCurrency.setTheme(currentCurrencyTextViewTheme)

        val selectedCurrencyTextViewTheme = TextViewTheme()
        selectedCurrencyTextViewTheme.textColor = Color.parseColor(ThemeManager.getValue("amountSectionView.convertedAmountLabelColor"))
        selectedCurrencyTextViewTheme.textSize = ThemeManager.getFontSize("amountSectionView.convertedAmountLabelFont")
        selectedCurrencyTextViewTheme.font = ThemeManager.getFontName("amountSectionView.convertedAmountLabelFont")
        selectedCurrency.setTheme(selectedCurrencyTextViewTheme)

        constraint.setBackgroundColor(Color.parseColor(ThemeManager.getValue("amountSectionView.backgroundColor")))

    }

    /**
     * @param amountViewDataSource is set via the consumer app for selectedCurrency,
     * currentCurrency and itemCount.
     **/
    fun setAmountViewDataSource(amountViewDataSource: AmountViewDataSource) {
        this.amountViewDataSource = amountViewDataSource
        amountViewDataSource.selectedCurr?.let {
            selectedCurrency.text = it
        }
        amountViewDataSource.currentCurr?.let {
            currentCurrency.text = it
        }
        amountViewDataSource.itemCount?.let {
            itemCount.text = it
        }

        itemCount.elevation = 0F

    }


}