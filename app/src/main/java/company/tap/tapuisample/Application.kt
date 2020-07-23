package company.tap.tapuisample

import company.tap.taplocalizationkit.LocaleApplication
import io.alterac.blurkit.BlurKit


/**
 * Created by AhlaamK on 6/22/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
class Application : LocaleApplication() {
    override fun onCreate() {
        super.onCreate()
        BlurKit.init(this)
    }
}