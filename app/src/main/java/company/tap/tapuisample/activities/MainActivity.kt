package company.tap.tapuisample.activities


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.tap.tapfontskit.FontChanger
import com.tap.tapfontskit.enums.TapFont
import company.tap.tapuilibrary.DialogConfigurations
import company.tap.tapuilibrary.interfaces.TapAmountSectionInterface
import company.tap.tapuilibrary.interfaces.TapBottomDialogInterface
import company.tap.tapuisample.R
import company.tap.tapuisample.adapters.context
import company.tap.tapuisample.fragments.BottomSheetDialog


class MainActivity : AppCompatActivity(),
    TapBottomDialogInterface, TapAmountSectionInterface {
    lateinit var fontChanger: FontChanger
    @SuppressLint("ResourceAsColor", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

    override fun didShow() {
        println("Dialog is shown now!!!")
    }

    override fun didDismiss() {
        println("Dialog is dismissed !!!")
    }

    fun openTapChip(view: View) {
        startActivity(Intent(this, CardviewActivity::class.java))
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

}