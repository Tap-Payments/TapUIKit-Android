package company.tap.tapuisample.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.transition.AutoTransition
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import company.tap.tapuilibrary.datasource.ActionButtonDataSource
import company.tap.tapuilibrary.enums.ActionButtonState
import company.tap.tapuilibrary.views.TapBottomSheetDialog

import company.tap.tapuisample.R
import company.tap.tapuisample.webview.WebFragment
import company.tap.tapuisample.webview.WebViewContract
import kotlinx.android.synthetic.main.fragment_example.*

class ExampleFragment : TapBottomSheetDialog() , WebViewContract {

    var clickAction = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_example, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetDialog.behavior.skipCollapsed = true
        action_button.setButtonDataSource(getSuccessDataSource())

        knet.setOnClickListener {
            clickAction = 1
        }
        otp.setOnClickListener {
            Toast.makeText(context, "OTP", Toast.LENGTH_SHORT).show()
        }
        gopay.setOnClickListener {
            Toast.makeText(context, "goPay", Toast.LENGTH_SHORT).show()
        }
        action_button.setOnClickListener {
            implementActionBtnOnClick()
        }
    }


    private fun implementActionBtnOnClick(){
        action_button.changeButtonState(ActionButtonState.LOADING)
        when (clickAction) {
            1 -> {
                changeBottomSheetTransition()
                buttons_layout.visibility = View.GONE
                replaceBetweenFragments()
            }
        }
    }

    private fun replaceBetweenFragments(){
        childFragmentManager.beginTransaction().add(R.id.fragment_container,
            WebFragment(this)
        ).commit()
    }


    override fun redirectLoadingFinished(done: Boolean) {
        changeBottomSheetTransition()
        if (done) {
            action_button.visibility = View.VISIBLE
            fragment_container.visibility = View.GONE
            action_button.setButtonDataSource(getSuccessDataSource())
            action_button.changeButtonState(ActionButtonState.SUCCESS)

        } else {
            action_button.visibility = View.GONE
            fragment_container.visibility = View.VISIBLE
        }
    }

    private fun changeBottomSheetTransition(){
        bottomSheetLayout?.let { layout ->
            layout.post {
                TransitionManager.beginDelayedTransition(layout, ChangeBounds().setDuration(1000))
            }
        }
    }

    private fun getSuccessDataSource(): ActionButtonDataSource {
        return ActionButtonDataSource(
            text = "Pay",
            textSize = 20f,
            textColor = Color.WHITE,
            cornerRadius = 100f,
            successImageResources = R.drawable.checkmark,
            backgroundColor = resources.getColor(R.color.button_green)
        )
    }
}
