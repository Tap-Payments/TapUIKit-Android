package company.tap.tapuisample

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_custom_tapcard.*


class MainActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpSwitch()
    }

    private fun setUpSwitch() {
        leftAccessory_Switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                imageView_visa.visibility = View.VISIBLE
            } else {
                imageView_visa.visibility = View.GONE
            }
        }
        rightAccessory_Switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                imageView_master.visibility = View.VISIBLE
            } else {
                imageView_master.visibility = View.GONE
            }
        }
    }

    fun changeText(view: View) {
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
            textView_card.text = inputText
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, _ -> dialog.cancel() }
        builder.show()
    }

}


