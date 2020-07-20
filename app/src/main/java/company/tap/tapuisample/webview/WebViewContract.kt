package company.tap.tapuisample.webview

import android.content.Context

/**
 * Created by OlaMonir on 7/19/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/

interface WebViewContract {
    fun getContext(): Context?

    fun showSuccessMessage(message: String)

    fun showErrorMessage(message: String)

    fun showLoading()

    fun hideLoading()
}