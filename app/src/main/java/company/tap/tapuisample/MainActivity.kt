package company.tap.tapuisample


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import company.tap.tapuilibrary.*
import kotlinx.android.synthetic.main.view_custom_tapcard.view.*
import java.io.InputStream
import java.net.URL


class MainActivity : AppCompatActivity(),
    TapBottomDialogInterface {
    lateinit var businessIcon:TapImageView

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openBottomSheet(view: View) {
        val modalBottomSheet = BottomSheetDialog()
        modalBottomSheet.arguments = getArguments()
        modalBottomSheet.show(supportFragmentManager, BottomSheetDialog.TAG)
    }

    private fun getArguments(): Bundle {
        val arguments = Bundle()
        arguments.putFloatArray(DialogConfigurations.Corners, floatArrayOf(100f, 100f, 0f, 0f))
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


}