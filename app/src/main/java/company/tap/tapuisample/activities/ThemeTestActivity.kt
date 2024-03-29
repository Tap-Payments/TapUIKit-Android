package company.tap.tapuisample.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import company.tap.tapuilibrary.themekit.ThemeManager
import company.tap.tapuisample.R

import kotlinx.android.synthetic.main.activity_theme_test.*

class ThemeTestActivity : AppCompatActivity() {
    private var currentTheme = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme_test)
//        initAppTheme(R.raw.defaultlighttheme)
        ThemeManager.loadTapTheme(resources, R.raw.defaultdarktheme1, "defaultdarktheme")


    }


//    private fun initAppTheme(theme: Int) {
//        currentTheme = theme
////        ThemeManager.loadTapTheme(this, "https://kar-tempo.s3.ap-south-1.amazonaws.com/theme-tap.json")
//        ThemeManager.loadTapTheme(resources, theme)
//        val textViewTheme = TextViewTheme()
//        textViewTheme.textColor =
//            Color.parseColor(ThemeManager.getValue("GlobalValues.Colors.lightPeach"))
//        textView.setTheme(textViewTheme)
////        = (ThemeManager.getValue<Int>("inlineCard.saveCardOption.labelTextFont") as String ).toFloat()
////        textView.setTheme(textViewTheme) = Color.parseColor(ThemeManager.getValue("GlobalValues.Colors.orange"))
//    }


    fun swap(view: View?) {
        if (currentTheme == R.raw.defaultdarktheme1) {
            ThemeManager.loadTapTheme(resources, R.raw.defaultdarktheme1, "defaultdarktheme")
            setTheme(R.style.AppTheme);
            Toast.makeText(
                applicationContext,
                "Theme switched to defaultlighttheme",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            ThemeManager.loadTapTheme(resources, R.raw.defaultdarktheme1, "defaultdarktheme")

            setTheme(R.style.AppThemeBlack);
            Toast.makeText(
                applicationContext,
                "Theme switched defaultdarktheme",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}