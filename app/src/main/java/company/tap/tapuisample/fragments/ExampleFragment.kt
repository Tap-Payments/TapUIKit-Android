package company.tap.tapuisample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import company.tap.tapuilibrary.uikit.datasource.ActionButtonDataSource
import company.tap.tapuilibrary.uikit.datasource.GoPayLoginDataSource
import company.tap.tapuilibrary.uikit.enums.ActionButtonState
import company.tap.tapuilibrary.uikit.interfaces.GoPayLoginInterface

import company.tap.tapuilibrary.uikit.organisms.GoPayPasswordInput
import company.tap.tapuilibrary.uikit.views.TapBottomSheetDialog
import company.tap.tapuisample.R
import company.tap.tapuisample.webview.WebFragment
import company.tap.tapuisample.webview.WebViewContract
import kotlinx.android.synthetic.main.fragment_example.*

class ExampleFragment : TapBottomSheetDialog() , WebViewContract, GoPayLoginInterface {

    var clickAction = 0

   // var goPayLoginInput: GoPayLoginInput?= null
     var goPayPasswordInput: GoPayPasswordInput? = null
    companion object {
        const val TAG = "ModalBottomSheet"
    }
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
       // goPayLoginInput = view.findViewById(R.id.gopay_login_input)
        goPayPasswordInput = view.findViewById(R.id.goPay_password)
//        backgroundColor = Color.parseColor(ThemeManager.getValue("GlobalValues.Colors.whiteTwo"))


       // goPayLoginInput?.changeDataSource(GoPayLoginDataSource())
     //   goPayLoginInput?.setLoginInterface(this)
    //    goPayPasswordInput?.setLoginInterface(this, goPayLoginInput?.textInput?.text.toString())
//        goPayPasswordInput?.rootView?.setBackgroundColor(Color.parseColor("#c7f9f9f9"))


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

    fun getSuccessDataSource(
        backgroundColor: Int,
        text: String,
        textColor: Int
    ): ActionButtonDataSource {
        return ActionButtonDataSource(
            text = text,
            textSize = 18f,
            textColor = textColor,
            cornerRadius = 100f,
            successImageResources = R.drawable.checkmark,
            backgroundColor = backgroundColor
        )
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


    override fun onChangeClicked() {
//        AnimationEngine.applyTransition(bottomSheet, SLIDE)
      //  goPayLoginInput?.visibility = View.VISIBLE
        goPayPasswordInput?.visibility = View.GONE
    }

    override fun onEmailValidated() {
//        AnimationEngine.applyTransition(bottomSheet, SLIDE)
     //   goPayLoginInput?.visibility = View.GONE
        goPayPasswordInput?.visibility = View.VISIBLE
      //  goPayPasswordInput?.setLoginInterface(this,goPayLoginInput?.textInput?.text.toString())
    }

    override fun onPhoneValidated() {

        //Todo open otp view here
//        AnimationEngine.applyTransition(bottomSheet, SLIDE)
        Toast.makeText(context,"OTP view to slide up",Toast.LENGTH_SHORT).show()

        activity?.supportFragmentManager?.let { OTPFragment().show(it,null) }

    }


}
