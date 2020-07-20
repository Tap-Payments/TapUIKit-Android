package company.tap.tapuisample.webview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.NonNull
import kotlinx.android.synthetic.main.fragment_web.*


/**
 * Created by OlaMonir on 7/19/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
class CustomWebViewClient constructor(private val webViewFragment: WebFragment) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        checkPaymentError(request.url.toString().toLowerCase())
        checkKnetPaymentStatus(request.url.toString().toLowerCase())
        checkCreditCardPaymentStatus(request.url.toString().toLowerCase())
        checkCreditCardToken(request.url.toString().toLowerCase())
        showLoadingToCheckCreditCardToken(request.url.toString().toLowerCase())
        Log.d("url2", request.url.toString())
        view.loadUrl(request.url.toString(), getCustomHeaders())
        return true
    }


    override fun shouldOverrideUrlLoading(view: WebView, @NonNull url: String): Boolean {
        checkPaymentError(url.toLowerCase())
        checkKnetPaymentStatus(url.toLowerCase())
        checkCreditCardPaymentStatus(url.toLowerCase())
        checkCreditCardToken(url.toLowerCase())
        showLoadingToCheckCreditCardToken(url.toLowerCase())
        Log.d("url2", url)
        view.loadUrl(url, getCustomHeaders())
        return true
    }


    private fun checkKnetPaymentStatus(url: String) {
        if (url.contains("response/receiptKnet".toLowerCase())) {
            if (checkPaymentSuccess(url)) {
                webViewFragment.showSuccessMessage("")
            } else {
                val urlQuerySanitizer: Uri = Uri.parse(url)
                val msg: String = urlQuerySanitizer.getQueryParameter("message").toString()
                webViewFragment.showErrorMessage( msg)
            }
        }
    }

    private fun checkCreditCardPaymentStatus(url: String) {
        if (url.contains("response/receiptCC".toLowerCase())) {
            if (checkPaymentSuccess(url)) webViewFragment.showSuccessMessage("") else webViewFragment.showErrorMessage("")

        }
    }


    private fun checkCreditCardToken(url: String) {
        if (url.contains("response/receipt_checkout".toLowerCase())) {
            if (checkPaymentSuccess(url)) webViewFragment.showSuccessMessage("") else webViewFragment.showErrorMessage("")
        }
    }

    private fun checkPaymentError(url: String) {
        if (url.contains("errorPage".toLowerCase())) {
            val urlQuerySanitizer: Uri = Uri.parse(url)
            val msg: String = urlQuerySanitizer.getQueryParameter("message").toString()
            if (msg.isNotEmpty()) webViewFragment.showErrorMessage(msg) else webViewFragment.showErrorMessage("Error")
        }
    }


    private fun showLoadingToCheckCreditCardToken(url: String) {
        try {
            val urlQuerySanitizer: Uri = Uri.parse(url)
            // here we will put the word of return in response url
            val token: String = urlQuerySanitizer.getQueryParameter("cko-payment-token").toString()
            if (token.isNotEmpty()) {
                webViewFragment.showLoading()
                webViewFragment.web_view.visibility = View.INVISIBLE
            }
        } catch (ex: UnsupportedOperationException) {
            ex.printStackTrace()
        }
    }

    private fun checkPaymentSuccess(url: String): Boolean {
        return try {
            val urlQuerySanitizer: Uri = Uri.parse(url)
            val status: String = urlQuerySanitizer.getQueryParameter("result").toString()
            status.equals("CAPTURED", ignoreCase = true) || status.equals(
                "0",
                ignoreCase = true
            )
        } catch (ex: Exception) {
            false
        }
    }


    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        Log.d("url", url.toString())
        webViewFragment.showLoading()
    }

    override fun onPageFinished(@NonNull view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        Log.d("url1", url.toString())
        url?.let { webViewFragment.setOnPageFinishedAction(it) }

        webViewFragment.hideLoading()
    }


    @SuppressLint("NewApi")
    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {
        return null
    }

    private fun getCustomHeaders(): Map<String, String>? {
        val headers: MutableMap<String, String> = HashMap()

      //  headers["cid"] = LocalCacheManager.getSelectedCountry().getId().toString() + ""
      //  headers["auth_token"] = Preferences.getAuthToken(webviewActivity).toString() + ""
       // headers[APIConstants.ACCESS_TOKEN] = APIConstants.API_ACCESS_TOKEN
        return headers
    }


}