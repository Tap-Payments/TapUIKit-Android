package company.tap.tapuisample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import company.tap.tapuisample.databinding.ActivityMainBinding

import company.tap.thememanager.ThemeManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var themeManager: ThemeManager? = null
    private lateinit var mainBinding: ActivityMainBinding
    private var currentTheme = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        tap_editText.hint = "Please enter details"
        initAppTheme(R.raw.theme1)
    }
    private fun initAppTheme(theme: Int) {
        currentTheme = theme
        themeManager = ThemeManager()
        themeManager!!.loadTapTheme(resources, theme)

    }
}

