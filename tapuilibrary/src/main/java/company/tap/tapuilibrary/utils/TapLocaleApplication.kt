package company.tap.tapuilibrary.utils

import android.content.Context
import android.content.res.Configuration
import company.tap.taplocalizationkit.LocaleApplication
import company.tap.taplocalizationkit.LocaleApplicationDelegates
import company.tap.taplocalizationkit.LocalizationManager
import company.tap.tapuilibrary.R

/**
 * Created by AhlaamK on 7/26/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
open class TapLocaleApplication: LocaleApplication() {
    private val localeAppDelegate = LocaleApplicationDelegates()
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base?.let {
            localeAppDelegate.attachBaseContext(it)
        })
        initAppLocale(R.raw.lang)


    }
    private fun initAppLocale(lang: Int) {
        LocalizationManager.loadTapLocale(resources, lang)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeAppDelegate.onConfigurationChanged(this)
    }
}