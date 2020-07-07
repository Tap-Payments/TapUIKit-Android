package company.tap.tapuisample.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import company.tap.tapuilibrary.views.TapBottomSheetDialog

import company.tap.tapuisample.R
import kotlinx.android.synthetic.main.fragment_example.*

class ExampleFragment : TapBottomSheetDialog() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_example, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        knet.setOnClickListener {
            Toast.makeText(context, "KNET", Toast.LENGTH_SHORT).show()
        }
        otp.setOnClickListener {
            Toast.makeText(context, "OTP", Toast.LENGTH_SHORT).show()
        }
        gopay.setOnClickListener {
            Toast.makeText(context, "goPay", Toast.LENGTH_SHORT).show()
        }
    }
}
