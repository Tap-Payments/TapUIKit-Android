package company.tap.tapuisample.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import company.tap.tapuisample.R
import kotlinx.android.synthetic.main.fragment_web.*

class WebFragment : Fragment() {

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
        web_view.settings.javaScriptEnabled = true
        web_view.webViewClient = (object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                exampleFragment?.redirectLoadingFinished(url.contains("https://www.google.com/search?"))
            }
        })
        web_view.loadUrl("https://www.google.com");
    }

}
