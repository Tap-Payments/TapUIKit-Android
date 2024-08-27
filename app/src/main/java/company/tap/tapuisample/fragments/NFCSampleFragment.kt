package company.tap.tapuisample.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomsheet.BottomSheetBehavior
import company.tap.nfcreader.open.reader.TapEmvCard
import company.tap.nfcreader.open.reader.TapNfcCardReader
import company.tap.nfcreader.open.utils.TapCardUtils
import company.tap.nfcreader.open.utils.TapNfcUtils
import company.tap.tapuilibrary.uikit.atoms.TapTextViewNew
import company.tap.tapuilibrary.uikit.views.TapBottomSheetDialog
import company.tap.tapuilibrary.uikit.views.TapNFCView
import company.tap.tapuisample.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable


/**
 * Created by AhlaamK on 7/2/20.

Copyright (c) 2020    Tap Payments.
All rights reserved.
 **/
class NFCSampleFragment : Fragment() {
    private lateinit var customNFC: TapNFCView
    private lateinit var scanNFC: TapTextViewNew
    private lateinit var aboutNFC: TapTextViewNew
    private var tapNfcCardReader: TapNfcCardReader? = null
    private var cardReadDisposable: Disposable = Disposable.empty()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.custom_nfc_example, container, false)
//        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED

        initView(view)
        return view.rootView

    }

    @SuppressLint("SetTextI18n")
    private fun initView(view: View) {
        customNFC = view.findViewById(R.id.custom_nfc)
        scanNFC = customNFC.findViewById(R.id.scan_nfc)
        aboutNFC = customNFC.findViewById(R.id.aboutNFC)
        scanNFC.text = "Ready to scan, add the card under the device to scan it."
        aboutNFC.text =
            "Near-field communication is a set of communication protocols for communication between two electronic devices over a distance of 4 cm or less."

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        tapNfcCardReader = TapNfcCardReader(this.activity)
    }

    override fun onResume() {
        super.onResume()

        if (TapNfcUtils.isNfcAvailable(context)) {
            if (TapNfcUtils.isNfcEnabled(context)) {
                tapNfcCardReader?.enableDispatch() //Activates NFC  to read NFC Card details .

            } else {
                enableNFC()
            }
        } else {
            Toast.makeText(context, "NFC is not supported!!!", Toast.LENGTH_SHORT).show()
        }
    }

    fun enableNFC() {

        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Please enable NFC")
        alertDialog.setMessage("NFC not enabled")
        alertDialog.setPositiveButton(
            getString(R.string.msg_ok)
        ) { dialog: DialogInterface, which: Int ->

            dialog.dismiss()
            startActivity(Intent(Settings.ACTION_NFC_SETTINGS))
        }
        alertDialog.setNegativeButton(
            getString(R.string.msg_dismiss)
        ) { dialog: DialogInterface, which: Int ->
            dialog.dismiss()
            val fragmentTransaction: FragmentTransaction? = fragmentManager?.beginTransaction()
            fragmentTransaction?.remove(this)?.commit()
            // onBackPressed()
        }
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun displayError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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
      //  Toast.makeText(context, text, Toast.LENGTH_LONG).show()
        Toast.makeText(context, "Scanned Successful!!!\n $text", Toast.LENGTH_LONG).show()

        val fragmentTransaction: FragmentTransaction? = fragmentManager?.beginTransaction()
        fragmentTransaction?.remove(this)?.commit()


    }

    fun processNFC(intent: Intent?) {
        if (tapNfcCardReader?.isSuitableIntent(intent)!!) {

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