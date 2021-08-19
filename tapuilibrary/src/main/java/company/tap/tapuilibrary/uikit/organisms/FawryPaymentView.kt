package company.tap.tapuilibrary.uikit.organisms

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.LinearLayout
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.R
import company.tap.tapuilibrary.fontskit.enums.TapFont
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuilibrary.themekit.theme.TextViewTheme
import company.tap.tapuilibrary.uikit.atoms.TapTextView
import company.tap.tapuilibrary.uikit.views.TabAnimatedActionButton


class FawryPaymentView  (context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {


        lateinit var attrs: AttributeSet
        val titleText by lazy { findViewById<TapTextView>(R.id.titleText) }
        val descText by lazy { findViewById<TapTextView>(R.id.descText) }
        val orderCodeText by lazy { findViewById<TapTextView>(R.id.orderCodeText) }
        val orderCodeValue by lazy { findViewById<TapTextView>(R.id.orderCodeValue) }
        val codeExpireText by lazy { findViewById<TapTextView>(R.id.codeExpireText) }
        val codeExpireValue by lazy { findViewById<TapTextView>(R.id.codeExpireValue) }
        val linkDescText by lazy { findViewById<TapTextView>(R.id.linkDescText) }
        val linkValue by lazy { findViewById<TapTextView>(R.id.linkValue) }
        val payButton by lazy { findViewById<TabAnimatedActionButton>(R.id.payButton) }

        init {
        inflate(context, R.layout.fawry_payment_view, this)
            setTheme()
            initButton()
            if (context?.let { LocalizationManager.getLocale(it).language } == "en") setFontsEnglish() else setFontsArabic()
        }


    fun initButton(){
        payButton.isEnabled = true
        payButton.setButtonDataSource(
            true,
            context?.let { LocalizationManager.getLocale(it).language },
            LocalizationManager.getValue("close", "Common"),
            Color.parseColor(ThemeManager.getValue("actionButton.Invalid.backgroundColor")),
            Color.parseColor(ThemeManager.getValue("actionButton.Valid.titleLabelColor"))
        )
    }

    fun initViews(orderCode:String,codeExpire : String , link:String ){
        orderCodeValue.text = orderCode
        codeExpireValue.text = codeExpire
        linkValue.text = link
    }




    fun setTheme() {
        val titleTextViewTheme = TextViewTheme()
        titleTextViewTheme.textColor =
            Color.parseColor(ThemeManager.getValue("merchantHeaderView.subTitleLabelColor"))
        titleTextViewTheme.textSize =
            ThemeManager.getFontSize("merchantHeaderView.subTitleLabelFont")
        titleTextViewTheme.font =
            ThemeManager.getFontName("merchantHeaderView.subTitleLabelFont")
        titleText.setTheme(titleTextViewTheme)


        val descTextViewTheme = TextViewTheme()
        descTextViewTheme.textColor =
            Color.parseColor(ThemeManager.getValue("merchantHeaderView.subTitleLabelColor"))
        descTextViewTheme.textSize =
            ThemeManager.getFontSize("merchantHeaderView.subTitleLabelFont")
        descTextViewTheme.font =
            ThemeManager.getFontName("merchantHeaderView.subTitleLabelFont")
        descText.setTheme(descTextViewTheme)


        val orderCodeTextViewTheme = TextViewTheme()
        orderCodeTextViewTheme.textColor =
            Color.parseColor(ThemeManager.getValue("merchantHeaderView.subTitleLabelColor"))
        orderCodeTextViewTheme.textSize =
            ThemeManager.getFontSize("merchantHeaderView.subTitleLabelFont")
        orderCodeTextViewTheme.font =
            ThemeManager.getFontName("merchantHeaderView.subTitleLabelFont")
        orderCodeText.setTheme(orderCodeTextViewTheme)


        val orderCodeValueViewTheme = TextViewTheme()
        orderCodeValueViewTheme.textColor =
            Color.parseColor(ThemeManager.getValue("merchantHeaderView.subTitleLabelColor"))
        orderCodeValueViewTheme.textSize =
            ThemeManager.getFontSize("merchantHeaderView.subTitleLabelFont")
        orderCodeValueViewTheme.font =
            ThemeManager.getFontName("merchantHeaderView.subTitleLabelFont")
        orderCodeValue.setTheme(orderCodeValueViewTheme)


        val codeExpireTextViewTheme = TextViewTheme()
        codeExpireTextViewTheme.textColor =
            Color.parseColor(ThemeManager.getValue("merchantHeaderView.subTitleLabelColor"))
        codeExpireTextViewTheme.textSize =
            ThemeManager.getFontSize("merchantHeaderView.subTitleLabelFont")
        codeExpireTextViewTheme.font =
            ThemeManager.getFontName("merchantHeaderView.subTitleLabelFont")
        codeExpireText.setTheme(codeExpireTextViewTheme)


        val codeExpireValueViewTheme = TextViewTheme()
        codeExpireValueViewTheme.textColor =
            Color.parseColor(ThemeManager.getValue("merchantHeaderView.subTitleLabelColor"))
        codeExpireValueViewTheme.textSize =
            ThemeManager.getFontSize("merchantHeaderView.subTitleLabelFont")
        codeExpireValueViewTheme.font =
            ThemeManager.getFontName("merchantHeaderView.subTitleLabelFont")
        codeExpireValue.setTheme(codeExpireValueViewTheme)

        val linkDescTextViewTheme = TextViewTheme()
        linkDescTextViewTheme.textColor =
            Color.parseColor(ThemeManager.getValue("merchantHeaderView.subTitleLabelColor"))
        linkDescTextViewTheme.textSize =
            ThemeManager.getFontSize("merchantHeaderView.subTitleLabelFont")
        linkDescTextViewTheme.font =
            ThemeManager.getFontName("merchantHeaderView.subTitleLabelFont")
        linkDescText.setTheme(linkDescTextViewTheme)


        val linkValueViewTheme = TextViewTheme()
        linkValueViewTheme.textColor =
            Color.parseColor(ThemeManager.getValue("merchantHeaderView.subTitleLabelColor"))
        linkValueViewTheme.textSize =
            ThemeManager.getFontSize("merchantHeaderView.subTitleLabelFont")
        linkValueViewTheme.font =
            ThemeManager.getFontName("merchantHeaderView.subTitleLabelFont")
        linkValue.setTheme(linkValueViewTheme)

    }






    fun setFontsEnglish() {
        titleText?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoRegular
            )
        )

        descText?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoRegular
            )
        )

        orderCodeText?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoRegular
            )
        )
        orderCodeValue?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoRegular
            )
        )
        codeExpireText?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoRegular
            )
        )

        codeExpireValue?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoRegular
            )
        )


        linkDescText?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoRegular
            )
        )
        linkValue?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.RobotoRegular
            )
        )

    }

    fun setFontsArabic() {
        titleText?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.TajawalMedium
            )
        )

        descText?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.TajawalMedium
            )
        )

        orderCodeText?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.TajawalMedium
            )
        )
        orderCodeValue?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.TajawalMedium
            )
        )
        codeExpireText?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.TajawalMedium
            )
        )


        codeExpireValue?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.TajawalMedium
            )
        )

        linkDescText?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.TajawalMedium
            )
        )
        linkValue?.typeface = Typeface.createFromAsset(
            context?.assets, TapFont.tapFontType(
                TapFont.TajawalMedium
            )
        )

    }






}