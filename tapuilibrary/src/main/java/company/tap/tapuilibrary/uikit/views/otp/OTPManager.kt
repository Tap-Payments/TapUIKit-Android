package company.tap.tapuilibrary.uikit.views.otp

import android.app.Activity
import android.content.IntentFilter
import androidx.annotation.NonNull
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient


class OTPManager(builder: OTPManagerBuilder) {
    private val mActivity: Activity
    private val mOtpReceiverListener: OTPBroadcastReceiver.OTPReceiverListener?
    private val mOTPBroadcastReceiver: OTPBroadcastReceiver = OTPBroadcastReceiver()
    fun start() {
        initSMSReceiver()
        mOTPBroadcastReceiver.setOtpReceiverListener(mOtpReceiverListener)
        val intentFilter = IntentFilter()
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
        unregisterReceiver()
        mActivity.registerReceiver(mOTPBroadcastReceiver, intentFilter)
    }

    private fun unregisterReceiver() {
        try {
            mActivity.unregisterReceiver(mOTPBroadcastReceiver)
        } catch (e: Exception) {
            //DO NOTHING
        }
    }

    private fun initSMSReceiver() {
        // Get an instance of SmsRetrieverClient, used to start listening for a matching
        // SMS message.
        val client: SmsRetrieverClient = SmsRetriever.getClient(mActivity)

        // Starts SmsRetriever, which waits for ONE matching SMS message until timeout
        // (5 minutes). The matching SMS message will be sent via a Broadcast Intent with
        // action SmsRetriever#SMS_RETRIEVED_ACTION.
        val task: com.google.android.gms.tasks.Task<Void> = client.startSmsRetriever()

        // Listen for success/failure of the start Task. If in a background thread, this
        // can be made blocking using Tasks.await(task, [timeout]);
        task.addOnSuccessListener { TODO("Not yet implemented") }
        task.addOnFailureListener { e -> // Failed to start retriever, inspect Exception for more details
            // ...
            e.printStackTrace()
        }
    }

    class OTPManagerBuilder(val mActivity: Activity) {
        var otpReceiverListener: OTPBroadcastReceiver.OTPReceiverListener? = null
        fun setListener(otpReceiverListener: OTPBroadcastReceiver.OTPReceiverListener?): OTPManagerBuilder {
            this.otpReceiverListener = otpReceiverListener
            return this
        }

        fun build(): OTPManager {
            return OTPManager(this)
        }

    }

    init {
        mActivity = builder.mActivity
        mOtpReceiverListener = builder.otpReceiverListener
    }
}