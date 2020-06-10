package company.tap.tapuisample

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import company.tap.tapuilibrary.TapChipGroup
import company.tap.tapuilibrary.TapTextView
import kotlinx.android.synthetic.main.activity_cardview.*
import kotlinx.android.synthetic.main.view_custom_tapcard.view.*

class CardviewActivity : AppCompatActivity() {
    private lateinit var chipRecycler: RecyclerView

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cardview)
        setUpSwitch()
        setupTapCardChip()
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
}
