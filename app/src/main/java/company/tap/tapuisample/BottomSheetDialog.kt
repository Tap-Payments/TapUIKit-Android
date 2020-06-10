package company.tap.tapuisample



import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.Nullable
import com.bumptech.glide.Glide
import company.tap.tapuilibrary.TapBottomSheetDialog
import company.tap.tapuilibrary.TapImageView
import company.tap.tapuilibrary.TapTextView
import java.io.InputStream
import java.net.URL


/**
 * Created by AhlaamK on 6/10/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
open class BottomSheetDialog : TapBottomSheetDialog() {
    lateinit var businessName:TapTextView
    lateinit var businessFor:TapTextView
    lateinit var businessIcon :TapImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.custom_bottom_sheet, container, false)


    open class DownLoadImageTask(imageView: ImageView) : AsyncTask<String, Void, Bitmap>() {
        var imageView: ImageView

        override fun onPostExecute(result: Bitmap) {
            imageView.setImageBitmap(result)
        }

        init {
            this.imageView = imageView
        }

        override fun doInBackground(vararg urls: String): Bitmap? {
            val urlOfImage = urls[0]
            var logo: Bitmap? = null
            try {
                val `is`: InputStream = URL(urlOfImage).openStream()
                logo = BitmapFactory.decodeStream(`is`)
            } catch (e: Exception) { // Catch the download exception
                e.printStackTrace()
            }
            return logo
        }
    }


    override fun onViewCreated(
        view: View,
        @Nullable savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        businessName = view.findViewById(R.id.businessName)
        businessFor= view.findViewById(R.id.buinessFor)
        businessIcon = view.findViewById(R.id.businessIcon)
        businessName.text = getString(R.string.business_name)
        businessFor.text = "PAYMENT FOR"
        val placeholder:String = businessName.text[0].toString()
        context?.let {
            Glide.with(it)
                .load("https://www.google.com/images/srpr/logo11w.png")
                .placeholder(R.drawable.tap_logo)
                .disallowHardwareConfig()
                .into(businessIcon)
        }
       // DownLoadImageTask(businessIcon).execute("https://www.google.com/images/srpr/logo11w.png")
    }
    companion object {
        const val TAG = "ModalBottomSheet"
    }


}