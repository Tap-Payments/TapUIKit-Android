package company.tap.tapuisample.webview

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.fragment.app.Fragment
import company.tap.tapuisample.R
import company.tap.tapuisample.fragments.ExampleFragment
import kotlinx.android.synthetic.main.fragment_web.*


class WebFragment : Fragment() , WebViewContract {

    var exampleFragment: ExampleFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web, container, false)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        exampleFragment = parentFragment as ExampleFragment?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpWebView()
    }
    fun setOnPageFinishedAction(url: String){
        exampleFragment?.redirectLoadingFinished(url.contains("https://www.google.com/search?"))

    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() {
        web_view.settings.javaScriptEnabled = true
        web_view.webViewClient = CustomWebViewClient(this);
//        web_view.webViewClient = (object : WebViewClient() {
//            override fun onPageFinished(view: WebView, url: String) {
//                exampleFragment?.redirectLoadingFinished(url.contains("https://www.google.com/search?"))
//            }
//        })
        if (Build.VERSION.SDK_INT >= 21) {
            web_view.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        web_view.webViewClient = CustomWebViewClient(this)
        web_view.loadUrl("https://www.google.com")
        web_view.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                if (web_view.canGoBack()) {
                    web_view.goBack()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
            false
        }
    }

    override fun showSuccessMessage(message: String) {
        TODO("Not yet implemented")
    }

    override fun showErrorMessage(message: String) {
        TODO("Not yet implemented")
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }


}
