package company.tap.tapuisample

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import company.tap.tapuilibrary.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_custom_tapcard.view.*


class MainActivity : AppCompatActivity(),
    TapBottomDialogInterface {
    private lateinit var chipRecycler: RecyclerView

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupHeader()
        setUpSwitch()
        setupTapCardChip()
    }

    private fun setupHeader() {
        val businessName = findViewById<TapTextView>(R.id.businessName)
        businessName.text = getString(R.string.business_name)
        val businessIcon = findViewById<TapImageView>(R.id.businessIcon)
        businessIcon.setImageResource(company.tap.tapuilibrary.R.drawable.tap_logo)
        val cancelIcon = findViewById<TapImageView>(R.id.cancel_icon)
        cancelIcon.setImageResource(company.tap.tapuilibrary.R.drawable.cancel)

        cancelIcon.setOnClickListener {
            Toast.makeText(this, getString(R.string.you_clicked), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpSwitch() {
        leftAccessory_Switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                chipRecycler.imageView_visa.visibility = View.VISIBLE
            } else {
                chipRecycler.imageView_visa.visibility = View.GONE
            }
        }
        rightAccessory_Switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                chipRecycler.imageView_master.visibility = View.VISIBLE
            } else {
                chipRecycler.imageView_master.visibility = View.GONE
            }
        }
    }

    fun changeText(view: View) {
        chipRecycler.textView_card.visibility = View.VISIBLE
        chipRecycler.imageView_amex.visibility = View.GONE
        changeTextDialog()

    }

    private fun changeTextDialog() {
        var inputText = ""
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Chip Content")
        builder.setMessage("Enter a string value")
        // Set up the input
        val input = EditText(this)
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)
        // Set up the buttons
        builder.setPositiveButton(
            "Submit"
        ) { dialog, _ ->
            inputText = input.text.toString()
            chipRecycler.textView_card.text = inputText

        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("RestrictedApi", "ResourceAsColor")
    private fun setupTapCardChip() {
        val mainLayout = findViewById<TapChipGroup>(R.id.mainLayout)
        mainLayout.orientation = LinearLayout.HORIZONTAL
        val groupName = findViewById<TapTextView>(R.id.group_name)
        groupName.text = getString(R.string.recent)
        chipRecycler = findViewById(R.id.chip_recycler)
        val arrayList = arrayListOf(1, 2, 3, 4, 5)
        chipRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        chipRecycler.adapter = RecyclerAdapter(arrayList)

    }

    fun changeToImage(view: View) {
        chipRecycler.imageView_amex.visibility = View.VISIBLE
        chipRecycler.textView_card.visibility = View.GONE
    }

    fun openBottomSheet(view: View) {
        val modalBottomSheet = TapBottomSheetDialog()
        modalBottomSheet.arguments = getArguments()
        modalBottomSheet.show(supportFragmentManager, TapBottomSheetDialog.TAG)
    }

    private fun getArguments(): Bundle{
        val arguments = Bundle()
        arguments.putFloatArray(DialogConfigurations.Corners, floatArrayOf(50f, 50f, 0f, 0f))
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

}


