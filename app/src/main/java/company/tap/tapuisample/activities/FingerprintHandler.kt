package company.tap.tapuisample.activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.CancellationSignal
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import company.tap.tapuisample.R

/**
 * Created by AhlaamK on 7/22/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
 @RequiresApi(Build.VERSION_CODES.M)
class FingerprintHandler// Constructor
    (mContext: Context?) : FingerprintManager.AuthenticationCallback() {
    private var context: Context? = mContext

    fun startAuth(
        manager: FingerprintManager,
        cryptoObject: FingerprintManager.CryptoObject?
    ) {
        val cancellationSignal = CancellationSignal()
        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.USE_FINGERPRINT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null)
    }

    override fun onAuthenticationError(errMsgId: Int, errString: CharSequence) {
        this.update("Fingerprint Authentication error\n$errString", false)
    }

    override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence) {
        this.update("Fingerprint Authentication help\n$helpString", false)
    }

    override fun onAuthenticationFailed() {
        this.update("Fingerprint Authentication failed.", false)
    }


    override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult?) {
        this.update("Fingerprint Authentication succeeded.", true)
      //  val textdetect =
          //  (context as Activity).findViewById<View>(R.id.text_finger) as TextView

       // textdetect.visibility= View.GONE

    }

    fun update(e: String?, success: Boolean) {
        val textView =
        // (context as Activity).findViewById<View>(R.id.errorText) as TextView
        // textView.text = e
        // if (success) {
            Toast.makeText(context, e, Toast.LENGTH_LONG)
            // textView.setTextColor(R.color)
        }
    }
