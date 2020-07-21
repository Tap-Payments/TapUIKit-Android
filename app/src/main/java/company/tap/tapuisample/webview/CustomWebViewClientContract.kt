package company.tap.tapuisample.webview

/**
 * Created by OlaMonir on 7/21/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/

interface CustomWebViewClientContract {
    fun submitResponseStatus( success : Boolean)
    fun getRedirectedURL(url : String)
}