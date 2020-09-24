package company.tap.tapuilibrary.uikit.views

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.util.AttributeSet
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.fontskit.enums.TapFont
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.themekit.theme.ButtonTheme
import company.tap.tapuilibrary.themekit.theme.TextViewTheme
import company.tap.tapuilibrary.uikit.adapters.context
import company.tap.tapuilibrary.uikit.atoms.TapImageView
import company.tap.tapuilibrary.uikit.atoms.TapTextView
import company.tap.tapuilibrary.uikit.datasource.HeaderDataSource
import kotlinx.android.synthetic.main.tap_main_header.view.*


/**
 * TapHeader is a molecule element for setting businessName ,businessIcon and
 *  businessPlaceHodler for Merchant
 **/
class TapHeaderSectionView : LinearLayout {

    val businessIcon by lazy { findViewById<TapImageView>(R.id.businessIcon) }
    val businessName by lazy { findViewById<TapTextView>(R.id.businessName) }
    val paymentFor by lazy { findViewById<TapTextView>(R.id.paymentFor) }
    val businessPlaceholder by lazy { findViewById<TapTextView>(R.id.placeholderText) }
    private var headerDataSource: HeaderDataSource? = null

    /**
     * Simple constructor to use when creating a TapHeader from code.
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
        inflate(context, R.layout.tap_main_header, this)
        setTheme()
        if (context?.let { LocalizationManager.getLocale(it).language } == "en") setFontsEnglish() else setFontsArabic()
    }

    /**
     * @param headerDataSource is set via the consumer app for businessName,
     * businessIcon and businessPlaceHolder.
     **/
    fun setHeaderDataSource(headerDataSource: HeaderDataSource) {
        this.headerDataSource = headerDataSource
        headerDataSource.businessName?.let {
            businessName.text = it
        }
//        if (headerDataSource.businessImageResources!= null){
////            businessIcon.setImageURI(Uri.parse(headerDataSource.businessImageResources))
//            Glide.with(this)
//                .load(Uri.parse(headerDataSource.businessImageResources))
//                .into(businessIcon)
//        }else{
//                businessPlaceholder.text = headerDataSource.businessPlaceHolder?.get(0).toString()
//        }
//
//


        if (headerDataSource.businessImageResources == null) {
            businessIcon.setBackgroundColor(Color.parseColor(ThemeManager.getValue("horizontalList.backgroundColor")))
            businessPlaceholder.text = headerDataSource.businessPlaceHolder?.get(0).toString()

        } else {
            Glide.with(this)
                .load(headerDataSource.businessImageResources)
                .placeholder(
                    TextDrawable(
                        headerDataSource.businessPlaceHolder?.get(0).toString()
                    )
                )
                .into(businessIcon)

        }
//

//        headerDataSource.businessImageResources?.let {
//            Glide.with(this)
//                .load(Uri.parse(it))
//                .placeholder(TextDrawable(it))
//                .into(businessIcon)
//        }
        headerDataSource.businessFor?.let {
            paymentFor.text = it
        }
        headerDataSource.businessPlaceHolder?.let {
            businessPlaceholder.text = it
        }

    }


    fun setTheme() {

        val businessNameTextViewTheme = TextViewTheme()
        businessNameTextViewTheme.textColor =
            Color.parseColor(ThemeManager.getValue("merchantHeaderView.titleLabelColor"))
        businessNameTextViewTheme.textSize =
            ThemeManager.getFontSize("merchantHeaderView.titleLabelFont")
        businessNameTextViewTheme.font =
            ThemeManager.getFontName("merchantHeaderView.titleLabelFont")
        businessName.setTheme(businessNameTextViewTheme)

        val paymentForTextViewTheme = TextViewTheme()
        paymentForTextViewTheme.textColor =
            Color.parseColor(ThemeManager.getValue("merchantHeaderView.subTitleLabelColor"))
        paymentForTextViewTheme.textSize =
            ThemeManager.getFontSize("merchantHeaderView.subTitleLabelFont")
        paymentForTextViewTheme.font =
            ThemeManager.getFontName("merchantHeaderView.subTitleLabelFont")
        paymentFor.setTheme(paymentForTextViewTheme)

        val businessPlaceholderTextViewTheme = TextViewTheme()
        businessPlaceholderTextViewTheme.textColor =
            Color.parseColor(ThemeManager.getValue("merchantHeaderView.merchantLogoPlaceHolderColor"))
        businessPlaceholderTextViewTheme.textSize =
            ThemeManager.getFontSize("merchantHeaderView.merchantLogoPlaceHolderFont")
        businessPlaceholderTextViewTheme.font =
            ThemeManager.getFontName("merchantHeaderView.merchantLogoPlaceHolderFont")
        businessPlaceholder.setTheme(businessPlaceholderTextViewTheme)

        constraint.setBackgroundColor(Color.parseColor(ThemeManager.getValue("merchantHeaderView.backgroundColor")))

    }


    fun setFontsEnglish() {
        businessName?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoLight
            )
        )

        paymentFor?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoLight
            )
        )

        businessPlaceholder?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoLight
            )
        )

    }

    fun setFontsArabic() {
        businessName?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.TajawalLight
            )
        )

        paymentFor?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.TajawalLight
            )
        )

        businessPlaceholder?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.TajawalLight
            )
        )

    }

}





/*
fun Any.into(businessIcon: TapImageView): Any {
    return businessIcon

}

private fun Any.placeholder(businessPlaceholder: TapTextView): Any {
    return businessPlaceholder

}
*/



