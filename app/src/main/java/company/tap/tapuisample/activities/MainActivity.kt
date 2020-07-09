package company.tap.tapuisample.activities


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import cards.pay.paycardsrecognizer.sdk.Card
import cards.pay.paycardsrecognizer.sdk.FrameManager
import cards.pay.paycardsrecognizer.sdk.ui.InlineViewCallback
import com.tap.tapfontskit.FontChanger
import com.tap.tapfontskit.enums.TapFont
import company.tap.tapuilibrary.interfaces.TapAmountSectionInterface
import company.tap.tapuilibrary.interfaces.TapSwitchInterface
import company.tap.tapuilibrary.models.DialogConfigurations
import company.tap.tapuisample.R
import company.tap.tapuisample.fragments.*


class MainActivity : BaseActivity(),
    TapAmountSectionInterface, TapSwitchInterface, InlineViewCallback {
    private lateinit var fontChanger: FontChanger
    private lateinit var context: Context
    private val modalNFCBottomSheet = NFCSampleFragment()
    private val modalCardScannerBottomSheet = CardScannerFragment()

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

    fun openCardScannerView(view: View) {
        modalCardScannerBottomSheet.arguments = getArguments()
        modalCardScannerBottomSheet.show(
            supportFragmentManager,
            BottomSheetDialog.TAG
        )

    }

    override fun onScanCardFinished(card: Card?, cardImage: ByteArray?) {
      //  Toast.makeText(context,card.toString(), Toast.LENGTH_LONG).show()
        if (card != null) {
            showCardDialog(card)

        }
        Handler().postDelayed({
            supportFragmentManager
                .beginTransaction()
                .remove(modalCardScannerBottomSheet)
                .commit()
        }, 8000)


    }
    private fun showCardDialog(card: Card) {
        FrameManager.getInstance().frameColor = Color.GREEN
        val cardData = """
                Card number: ${card.cardNumberRedacted}
                Card holder: ${card.cardHolderName}
                Card expiration date: ${card.expirationDate}
                """.trimIndent()
        AlertDialog.Builder(this)
            .setTitle("Card Info")
            .setMessage(cardData)
            .setPositiveButton(android.R.string.yes, null)
            .setIcon(android.R.drawable.ic_dialog_info)
            .show()
    }


    override fun onScanCardFailed(e: Exception?) {
        println("card scan failed $e")
    }

    fun openFragment(view: View) {
        ExampleFragment().show(supportFragmentManager, null)
    }

}


