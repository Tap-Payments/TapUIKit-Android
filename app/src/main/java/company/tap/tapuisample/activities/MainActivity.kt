package company.tap.tapuisample.activities


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentTransaction
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import com.tap.tapfontskit.FontChanger
import com.tap.tapfontskit.enums.TapFont
import company.tap.tapuilibrary.interfaces.TapAmountSectionInterface
import company.tap.tapuilibrary.interfaces.TapNFCInterface
import company.tap.tapuilibrary.interfaces.TapSwitchInterface
import company.tap.tapuilibrary.models.DialogConfigurations
import company.tap.tapuisample.R
import company.tap.tapuisample.fragments.BottomSheetDialog
import company.tap.tapuisample.fragments.NFCFragment
import company.tap.tapuisample.fragments.SwitchFragment


class MainActivity : BaseActivity(),
    TapAmountSectionInterface, TapSwitchInterface, TapNFCInterface {
    private lateinit var fontChanger: FontChanger
    private lateinit var context: Context
    private val modalNFCBottomSheet = NFCFragment()

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        fontChanger = FontChanger(this.assets, TapFont.tapFontType(TapFont.robotoRegular))
        fontChanger.replaceFonts((findViewById(android.R.id.content)))


    }

    fun openBottomSheet(view: View) {
        val modalBottomSheet =
            BottomSheetDialog()
        modalBottomSheet.arguments = getArguments()
        modalBottomSheet.show(
            supportFragmentManager,
            BottomSheetDialog.TAG
        )
    }

    private fun getArguments(): Bundle {
        val arguments = Bundle()
        arguments.putFloatArray(DialogConfigurations.Corners, floatArrayOf(25f, 25f, 0f, 0f))
        arguments.putInt(DialogConfigurations.Color, Color.WHITE)
        arguments.putBoolean(DialogConfigurations.Cancelable, false)
        arguments.putFloat(DialogConfigurations.Dim, 0.75f)
        return arguments
    }

    fun openTapChip(view: View) {
        startActivity(Intent(this, TapChipsActivity::class.java))
    }

    override fun didClickItems() {
        println("Items button clicked !!!")
        Toast.makeText(this, "You have clicked Items", Toast.LENGTH_SHORT).show()
    }

    fun openAmountDiscountRow(view: View) {
        startActivity(Intent(this, AmountItemActivity::class.java))
    }

    fun openSectionsTabLayout(view: View) {
        startActivity(Intent(this, SectionsTabLayout::class.java))
    }

    fun openActionButton(view: View) {
        startActivity(Intent(this, ActionButtonActivity::class.java))
    }

    override fun enableSaveMobile(boolean: Boolean) {
        println("enableSaveMobile $boolean")
    }

    override fun enableSaveMerchantCheckout(boolean: Boolean) {
        println("enableSaveMerchantCheckout $boolean")
    }

    override fun enableSavegoPayCheckout(boolean: Boolean) {
        println("enableSavegoPayCheckout $boolean")
    }

    fun openSwitchView(view: View) {
        fontChanger = FontChanger(this.assets, TapFont.tapFontType(TapFont.robotoLight))
        fontChanger.replaceFonts((findViewById(android.R.id.content)))
        val modalswitchBottomSheet =
            SwitchFragment()
        modalswitchBottomSheet.arguments = getArguments()
        modalswitchBottomSheet.show(
            supportFragmentManager,
            BottomSheetDialog.TAG
        )
    }

    fun openNFCView(view: View) {
        modalNFCBottomSheet.arguments = getArguments()
        modalNFCBottomSheet.show(
            supportFragmentManager,
            BottomSheetDialog.TAG
        )
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        modalNFCBottomSheet.processNFC(intent)
    }

    override fun scannedCard(scannedCard: String) {
        Toast.makeText(context, scannedCard, Toast.LENGTH_LONG).show()
        val fragmentTransaction: FragmentTransaction =
            supportFragmentManager.beginTransaction()

        modalNFCBottomSheet?.let { layout ->
            val removeTransition: Transition =
                TransitionInflater.from(context)
                    .inflateTransition(R.transition.remove_fragment)
            //  TransitionManager.beginDelayedTransition(layout, removeTransition)
        }


        fragmentTransaction.remove(modalNFCBottomSheet).commit()

    }
}


