package company.tap.tapuilibrary.uikit.views.otp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status


/**
 * BroadcastReceiver to wait for SMS messages. This can be registered either
 * in the AndroidManifest or at runtime.  Should filter Intents on
 * SmsRetriever.SMS_RETRIEVED_ACTION.
 */
class OTPBroadcastReceiver : BroadcastReceiver() {
    private var otpReceiverListener: OTPReceiverListener? = null
    fun setOtpReceiverListener(otpReceiverListener: OTPReceiverListener?) {
        this.otpReceiverListener = otpReceiverListener
    }

    override fun onReceive(context: Context?, intent: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.action)) {
            val extras = intent.extras
            val status: Status? = extras!![SmsRetriever.EXTRA_STATUS] as Status?
            when (status?.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    // Get SMS message contents
                    val message =
                        extras[SmsRetriever.EXTRA_SMS_MESSAGE] as String?
                    // Extract one-time code from the message and complete verification
                    // by sending the code back to your server.
                    if (otpReceiverListener != null) {
                        otpReceiverListener!!.onOTPReceived(message)
                    }
                }
                CommonStatusCodes.TIMEOUT ->                     // Waiting for SMS timed out (5 minutes)
                    // Handle the error ...
                    if (otpReceiverListener != null) {
                        otpReceiverListener!!.onOTPTimeout()
                    }
            }
        }
    }

    interface OTPReceiverListener {
        fun onOTPReceived(message: String?)
        fun onOTPTimeout()
    }
}