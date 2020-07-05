package company.tap.tapuisample.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tap.tapfontskit.FontChanger
import com.tap.tapfontskit.enums.TapFont
import company.tap.nfcreader.open.reader.TapEmvCard
import company.tap.nfcreader.open.reader.TapNfcCardReader
import company.tap.nfcreader.open.utils.TapCardUtils
import company.tap.nfcreader.open.utils.TapNfcUtils
import company.tap.tapuilibrary.atoms.TapTextView
import company.tap.tapuilibrary.interfaces.TapNFCInterface
import company.tap.tapuilibrary.views.TapBottomSheetDialog
import company.tap.tapuilibrary.views.TapNFCView
import company.tap.tapuisample.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables


/**
 * Created by AhlaamK on 7/2/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
class NFCFragment : TapBottomSheetDialog() {
    private lateinit var customNFC: TapNFCView
    private lateinit var scanNFC: TapTextView
    private lateinit var aboutNFC: TapTextView
    private var tapNfcCardReader: TapNfcCardReader? = null
    private var tapNFCInterface: TapNFCInterface? = null
    private var cardReadDisposable: Disposable = Disposables.empty()
    private var mProgressDialog: ProgressDialog? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.custom_sheet_nfc, container, false)
        initView(view)
        return view

    }

    @SuppressLint("SetTextI18n")
    private fun initView(view: View) {
        customNFC = view.findViewById(R.id.custom_nfc)
        scanNFC = customNFC.findViewById(R.id.scan_nfc)
        aboutNFC = customNFC.findViewById(R.id.about_nfc)
        scanNFC.text = "Ready to scan, add the card under the device to scan it."
        aboutNFC.text =
            "Near-field communication is a set of communication protocols for communication between two electronic devices over a distance of 4 cm or less."
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        tapNfcCardReader = TapNfcCardReader(this.activity)
        try {
            tapNFCInterface = context as TapNFCInterface
        } catch (ex: ClassCastException) {
            try {
                tapNFCInterface = parentFragment as TapNFCInterface
            } catch (ignore: Exception) {
            }
        }
        if (TapNfcUtils.isNfcAvailable(context)) {
            if (TapNfcUtils.isNfcEnabled(context)) {
                tapNfcCardReader?.enableDispatch() //Activates NFC  to read NFC Card details .

            } else enableNFC()
        } else {
            //scancardContent.setVisibility(View.GONE)
            // cardreadContent.setVisibility(View.GONE)
            // noNfcText.setVisibility(View.VISIBLE)
        }
        //  super.onResume()
    }

    override fun onResume() {
        super.onResume()
        if (TapNfcUtils.isNfcAvailable(context)) {
            if (TapNfcUtils.isNfcEnabled(context)) {
                tapNfcCardReader?.enableDispatch() //Activates NFC  to read NFC Card details .

            } else{
                enableNFC()
            }
        } else {
            println("nfc unavaialble")
        }


    }

    fun enableNFC() {
        //noNfcText.setVisibility(View.VISIBLE)
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Please enable NFC")
        alertDialog.setMessage("NFC not enabled")
        alertDialog.setPositiveButton(
            getString(R.string.msg_ok)
        ) { dialog: DialogInterface, which: Int ->
            // noNfcText.setVisibility(View.GONE)
            dialog.dismiss()
            startActivity(Intent(Settings.ACTION_NFC_SETTINGS))
        }
        alertDialog.setNegativeButton(
            getString(R.string.msg_dismiss)
        ) { dialog: DialogInterface, which: Int ->
            dialog.dismiss()
            // onBackPressed()
        }
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    //
    fun displayError(message: String) {
        //noNfcText.setText(message)
        println("errored message $message")
    }

    private fun createProgressDialog() {
        val title = "resources.getString(R.string.ad_progressBar_title)"
        val mess = "pppp"
        mProgressDialog = ProgressDialog(context)
        mProgressDialog?.setTitle(title)
        mProgressDialog?.setMessage(mess)
        mProgressDialog?.setIndeterminate(true)
        mProgressDialog?.setCancelable(false)
    }

    fun showCardInfo(emvCard: TapEmvCard) {
        val text = TextUtils.join(
            "\n", arrayOf(
                TapCardUtils.formatCardNumber(emvCard.cardNumber, emvCard.type),
                DateFormat.format("M/y", emvCard.expireDate),
                "---",
                "Bank info (probably): ",
                emvCard.atrDescription,
                "---",
                emvCard.toString().replace(", ", ",\n")
            )
        )
        tapNFCInterface?.scannedCard(text)
        Log.e("showCardInfo:", text)

        //  mProgressDialog!!.dismiss()
    }

    fun processNFC(intent: Intent?) {
        if (tapNfcCardReader?.isSuitableIntent(intent)!!) {
            mProgressDialog?.show()
            cardReadDisposable = tapNfcCardReader!!
                .readCardRx2(intent)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ emvCard: TapEmvCard? ->
                    if (emvCard != null) {
                        this.showCardInfo(emvCard)
                    }
                },
                    { throwable -> throwable.message?.let { displayError(it) } })
        }

    }

    override fun onPause() {
        cardReadDisposable.dispose()
        tapNfcCardReader?.disableDispatch()
        super.onPause()
    }
}