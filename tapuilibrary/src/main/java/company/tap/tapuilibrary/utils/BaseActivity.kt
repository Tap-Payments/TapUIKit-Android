package company.tap.tapuilibrary.utils

import android.os.Bundle
import android.util.Log
import com.tap.tapfontskit.FontChanger
import com.tap.tapfontskit.enums.TapFont
import company.tap.taplocalizationkit.LocaleAppCompatActivity
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.R
import java.util.*

/**
 * Created by AhlaamK on 6/22/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/

/**
 * BaseActivity to set default locale to entire application using LoxalizationKit.
 * ***/
open class BaseActivity: LocaleAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAppLocale(R.raw.lang)
    }
    private fun initAppLocale(lang: Int) {
        LocalizationManager.loadTapLocale(resources, lang)
    }
}