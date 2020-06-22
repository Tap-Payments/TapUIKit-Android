package company.tap.tapuisample.activities

import android.os.Bundle
import company.tap.taplocalizationkit.LocaleAppCompatActivity
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuisample.R
import java.util.*

/**
 * Created by AhlaamK on 6/22/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
open class BaseActivity: LocaleAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAppLocale(R.raw.lang)
        setLocale(Locale("en"))
    }
    private fun initAppLocale(lang: Int) {
        LocalizationManager.loadTapLocale(resources, lang)
    }
}